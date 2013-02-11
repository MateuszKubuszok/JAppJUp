package com.autoupdater.gui.adapters;

import static com.autoupdater.gui.config.GuiConfiguration.WINDOW_TITLE;
import static com.autoupdater.gui.window.EWindowStatus.*;
import static java.awt.EventQueue.invokeLater;
import static java.lang.Thread.sleep;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.Client;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.EnvironmentDataManager;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.utils.AdapterUtils;
import com.autoupdater.gui.tabs.updates.UpdateInformationPanel;
import com.autoupdater.gui.window.EInfoTarget;
import com.autoupdater.gui.window.EWindowStatus;
import com.autoupdater.gui.window.GuiClientWindow;

public class Gui2ClientAdapter {
    private final EnvironmentData environmentData;
    private final Client client;

    private final AdapterUtils utils;

    // Client
    private SortedSet<Program> availableOnServer;
    private SortedSet<Update> availableUpdates;

    // GUI instances
    private GuiClientWindow clientWindow;

    private final Thread informationUpdatingThread;
    private FileAggregatedDownloadService currentDownloadSession = null;

    private int minutesSinceLastUpdateCheck;
    private final static int MINUTES_BETWEEN_EACH_UPDATE_CHECK = 10;

    // initialization

    public Gui2ClientAdapter(EnvironmentDataManager environmentDataManager)
            throws ClientEnvironmentException, IOException {
        client = new Client(environmentData = environmentDataManager.getEnvironmentData());
        utils = new AdapterUtils(this, client);

        informationUpdatingThread = new Thread(new InformationUpdater());
    }

    public void setClientWindow(final GuiClientWindow clientWindow) {
        this.clientWindow = clientWindow;
        clientWindow.setSettings(environmentData);
        utils.setUpClientWindow(clientWindow);

        // until window is set up updates shouldn't be checked
        informationUpdatingThread.start();
        reportInfo(
                WINDOW_TITLE + " initalized",
                "Updater is initialized and ready to work. Click right mouse button on tray icon to check/install updates or run installed applications.",
                EInfoTarget.TOOLTIP);
    }

    // operations

    public synchronized void checkUpdates() {
        if (clientWindow == null
                || (clientWindow.getStatus() != UNINITIALIZED && clientWindow.getStatus() != IDLE))
            return;

        setState(FETCHING_UPDATE_INFO);
        setInstallationInactive();
        minutesSinceLastUpdateCheck = 0;

        (new Thread() {
            @Override
            public void run() {
                try {
                    utils.queryServerForInformation();
                } catch (DownloadResultException | IOException | ProgramSettingsNotFoundException e) {
                    reportError("Error occured during update checking", e.getMessage(),
                            EInfoTarget.TOOLTIP);
                    setState(isInitiated() ? IDLE : UNINITIALIZED);
                }
            }
        }).start();
    }

    public synchronized void installAllUpdates() {
        setState(FETCHING_UPDATES);
        setInstallationIndetermined();

        try {
            currentDownloadSession = utils.installAllUpdates();
        } catch (ProgramSettingsNotFoundException | IOException e) {
            reportError("Error occured during installation", e.getMessage(), EInfoTarget.TOOLTIP);
            setState(IDLE);
        }
    }

    public synchronized void installUpdatesForProgram(Program program) {
        setState(FETCHING_UPDATES);
        setInstallationIndetermined();

        try {
            utils.installUpdatesForProgram(program);
        } catch (ProgramSettingsNotFoundException | IOException ex) {
            reportError("Error occured during installation", ex.getMessage(), EInfoTarget.TOOLTIP);
            setState(IDLE);
        }
    }

    public void cancelDownloads() {
        currentDownloadSession.cancel();
    }

    // operations with models

    public SortedSet<Update> getAvailableUpdates() {
        if (availableUpdates == null)
            availableUpdates = new TreeSet<Update>();
        return availableUpdates;
    }

    public void setAvailableUpdates(SortedSet<Update> availableUpdates) {
        this.availableUpdates = availableUpdates;
    }

    public SortedSet<Program> getProgramsThatShouldBeDisplayed() {
        return client.getInstalledPrograms();
    }

    public void markAllUpdatesAsIntendedToInstall() {
        utils.markAllUpdatesAsIntendedToInstall();
    }

    public boolean isInitiated() {
        return availableUpdates != null;
    }

    public synchronized SortedSet<Program> getAvailableOnServer() throws IOException,
            DownloadResultException {
        if (availableOnServer == null) {
            clientWindow
                    .setStatusMessage("No available programs list found - attempting to download it from repostories");
            availableOnServer = utils.queryServerForPackages();
            clientWindow.setStatusMessage("Fetched available programs from repositories");
            refreshGUI();
        }

        return availableOnServer;
    }

    // operations with GUI

    public UpdateInformationPanel getUpdateInformationPanel(Update update) {
        return clientWindow.getUpdateInformationPanel(update);
    }

    public Gui2ClientAdapter setInstallationInactive() {
        clientWindow.setProgressBarInactive();
        return this;
    }

    public Gui2ClientAdapter setInstallationIndetermined() {
        clientWindow.setProgressBarIndetermined();
        return this;
    }

    public Gui2ClientAdapter setInstallationProgress(int numberOfUpdatesBeingInstalled,
            int numberOfUpdatesMarkedAsDone) {
        if (clientWindow != null)
            clientWindow.setProgressBar(numberOfUpdatesBeingInstalled, numberOfUpdatesMarkedAsDone);
        return this;
    }

    public Gui2ClientAdapter setState(EWindowStatus state) {
        clientWindow.setStatus(state);
        return this;
    }

    public void refreshGUI() {
        invokeLater(new RefreshGUI());
    }

    public void reportQuiet(String message) {
        clientWindow.setStatusMessage(message);
    }

    public void reportInfo(String title, String message, EInfoTarget target) {
        clientWindow.reportInfo(title, message, target);
    }

    public void reportWarning(String title, String message, EInfoTarget target) {
        clientWindow.reportWarning(title, message, target);
    }

    public void reportError(String title, String message, EInfoTarget target) {
        clientWindow.reportError(title, message, target);
    }

    public void bindDownloadServicesToUpdateInformationPanels(
            FileAggregatedDownloadService aggregatedService) {
        for (FileDownloadService downloadService : aggregatedService.getServices()) {
            Update update = aggregatedService.getAdditionalMessage(downloadService);
            if (update != null && getUpdateInformationPanel(update) != null)
                getUpdateInformationPanel(update).setDownloadService(downloadService);
        }
    }

    public void cleanTemp() {
        client.cleanTemp();
    }

    private class InformationUpdater implements Runnable {
        @Override
        public void run() {
            checkUpdates();

            while (!informationUpdatingThread.isInterrupted()) {
                if (minutesSinceLastUpdateCheck >= MINUTES_BETWEEN_EACH_UPDATE_CHECK)
                    checkUpdates();

                try {
                    waitOneMinute();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        private void waitOneMinute() throws InterruptedException {
            sleep(60 * 1000);
        }
    }

    private class RefreshGUI implements Runnable {
        @Override
        public void run() {
            clientWindow.refresh();
        }
    }
}
