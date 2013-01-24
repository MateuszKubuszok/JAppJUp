package com.autoupdater.gui.adapters;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static net.jsdpu.process.executors.Commands.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;

import net.jsdpu.EOperatingSystem;
import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.Client;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.EnvironmentDataManager;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.listeners.CheckUpdateTriggerListener;
import com.autoupdater.gui.adapters.listeners.InstallUpdateTriggerListener;
import com.autoupdater.gui.adapters.listeners.PackagesInfoNotificationListener;
import com.autoupdater.gui.adapters.runnables.CheckUpdatesRunnable;
import com.autoupdater.gui.adapters.runnables.InstallUpdatesRunnable;
import com.autoupdater.gui.tabs.updates.UpdateInformationPanel;
import com.autoupdater.gui.window.EWindowStatus;
import com.autoupdater.gui.window.GuiClientWindow;

public class Gui2ClientAdapter {
    private final EnvironmentDataManager environmentDataManager;
    private final EnvironmentData environmentData;
    private final Client client;

    // Client
    private SortedSet<Program> availableOnServer;
    private SortedSet<Update> availableUpdates;

    // GUI instances
    private GuiClientWindow clientWindow;

    private final Thread updateThread;

    private int updateCountdown;

    public Gui2ClientAdapter(EnvironmentDataManager environmentDataManager)
            throws ClientEnvironmentException, IOException {
        this.environmentDataManager = environmentDataManager;
        this.client = new Client(environmentData = this.environmentDataManager.getEnvironmentData());

        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // initially wait 1 minute
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                }

                updateCountdown = 10;

                while (!updateThread.isInterrupted()) {
                    if (updateCountdown >= 10)
                        checkUpdates();

                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        updateThread.start();
    }

    public void setClientWindow(final GuiClientWindow clientWindow) {
        this.clientWindow = clientWindow;
        clientWindow.setSettings(environmentData);

        clientWindow.bindCheckUpdatesButton(new CheckUpdateTriggerListener(this),
                new CheckUpdateTriggerListener(this));
        clientWindow.bindInstallUpdatesButton(new InstallUpdateTriggerListener(this),
                new InstallUpdateTriggerListener(this));

        for (final Program program : getProgramsThatShouldBeDisplayed()) {
            final ProgramSettings programSettings = program.findProgramSettings(client
                    .getProgramsSettings());
            if (programSettings != null) {
                this.clientWindow.bindProgramLauncher(program, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ExecutorService executorService = newSingleThreadExecutor();
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ExecutionQueueReader queueReader = EOperatingSystem
                                            .current()
                                            .getProcessExecutor()
                                            .execute(
                                                    convertMultipleConsoleCommands(wrapArgument(programSettings
                                                            .getPathToProgram())));
                                    String result;
                                    while ((result = queueReader.getNextOutput()) != null)
                                        System.out.println(program.getName() + ": " + result);
                                } catch (IOException | InvalidCommandException e1) {
                                    reportWarning(e1.toString(), program.getName());
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    public synchronized void checkUpdates() {
        if (clientWindow == null
                || (clientWindow.getStatus() != EWindowStatus.UNINITIALIZED && clientWindow
                        .getStatus() != EWindowStatus.IDLE))
            return;

        setState(EWindowStatus.FETCHING_UPDATE_INFO);
        setInstallationInactive();
        updateCountdown = 0;

        try {
            SortedSet<Program> selectedPrograms = getSelectedPrograms();
            SortedSet<Package> selectedPackages = getSelectedPackages();

            UpdateInfoAggregatedDownloadService aggregatedUpdateInfoService = client
                    .createUpdateInfoAggregatedDownloadService(selectedPackages);

            ChangelogInfoAggregatedDownloadService aggregatedChangelogInfoService = client
                    .createChangelogInfoAggregatedDownloadService(selectedPackages);

            BugsInfoAggregatedDownloadService aggregatedBugsInfoService = client
                    .createBugsInfoAggregatedDownloadService(selectedPrograms);

            new Thread(new CheckUpdatesRunnable(this, aggregatedUpdateInfoService,
                    aggregatedChangelogInfoService, aggregatedBugsInfoService)).start();
        } catch (DownloadResultException | IOException | ProgramSettingsNotFoundException e) {
            reportError("Error occured during update checking", e.getMessage());
            setState(EWindowStatus.IDLE);
        }

    }

    public synchronized void installUpdates() {
        setState(EWindowStatus.FETCHING_UPDATES);
        setInstallationIndetermined();

        try {
            FileAggregatedDownloadService aggregatedDownloadService = client
                    .createFileAggregatedDownloadService(availableUpdates);
            AggregatedInstallationService aggregatedInstallationService = client
                    .createInstallationAggregatedService(availableUpdates);

            new Thread(new InstallUpdatesRunnable(this, aggregatedDownloadService,
                    aggregatedInstallationService)).start();
        } catch (ProgramSettingsNotFoundException | IOException e) {
            reportError("Error occured during installation", e.getMessage());
            setState(EWindowStatus.IDLE);
        }

    }

    public SortedSet<Update> getAvailableUpdates() {
        return availableUpdates;
    }

    public void setAvailableUpdates(SortedSet<Update> availableUpdates) {
        this.availableUpdates = availableUpdates;
    }

    public SortedSet<Program> getProgramsThatShouldBeDisplayed() {
        return client.getInstalledPrograms();
    }

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

    public Gui2ClientAdapter setStatusMessage(String message) {
        clientWindow.setStatusMessage(message);
        return this;
    }

    public void refreshGUI() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                clientWindow.refresh();
            }
        });
    }

    public void reportInfo(String title, String message) {
        clientWindow.reportInfo(title, message);
    }

    public void reportWarning(String title, String message) {
        clientWindow.reportWarning(title, message);
    }

    public void reportError(String title, String message) {
        clientWindow.reportError(title, message);
    }

    public boolean isInitiated() {
        return availableUpdates != null;
    }

    private synchronized SortedSet<Program> getAvailableOnServer() throws IOException,
            DownloadResultException {
        if (availableOnServer == null) {
            setStatusMessage("No available programs list found - attempting to download it from repostories");

            PackagesInfoAggregatedDownloadService aggregatedService = client
                    .createPackagesInfoAggregatedDownloadService();
            aggregatedService.getNotifier().addObserver(
                    new PackagesInfoNotificationListener(this, aggregatedService));

            aggregatedService.start();
            aggregatedService.joinThread();

            availableOnServer = aggregatedService.getResult();

            setStatusMessage("Fetched available programs from repositories").refreshGUI();
        }

        return availableOnServer;
    }

    private SortedSet<Program> getSelectedPrograms() throws IOException, DownloadResultException {
        return client.getAvailabilityFilter()
                .findProgramsAvailableToInstallOrInstalledWithDefinedSettings(
                        getAvailableOnServer());
    }

    private SortedSet<Package> getSelectedPackages() throws IOException, DownloadResultException {
        return client.getAvailabilityFilter().findPackagesAvailableToHaveTheirUpdateChecked(
                getAvailableOnServer());
    }

    public void markAllUpdatesAsIntendedToInstall() {
        if (availableUpdates != null)
            for (Update update : availableUpdates)
                if (update.getPackage().getVersionNumber().compareTo(update.getVersionNumber()) < 0)
                    update.setStatus(EUpdateStatus.SELECTED);
    }

    public void bindDownloadServicesToUpdateInformationPanels(
            FileAggregatedDownloadService aggregatedService) {
        for (FileDownloadService downloadService : aggregatedService.getServices()) {
            Update update = aggregatedService.getAdditionalMessage(downloadService);
            if (update != null && getUpdateInformationPanel(update) != null)
                getUpdateInformationPanel(update).setDownloadService(downloadService);
        }
    }
}
