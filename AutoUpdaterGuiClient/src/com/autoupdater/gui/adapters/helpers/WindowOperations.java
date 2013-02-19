package com.autoupdater.gui.adapters.helpers;

import static java.awt.EventQueue.invokeLater;

import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.listeners.action.RunCommandActionListener;
import com.autoupdater.gui.adapters.listeners.triggers.CancelDownloadsTriggerListener;
import com.autoupdater.gui.adapters.listeners.triggers.CheckUpdateTriggerListener;
import com.autoupdater.gui.adapters.listeners.triggers.InstallUpdateTriggerListener;
import com.autoupdater.gui.tabs.updates.UpdateInformationPanel;
import com.autoupdater.gui.window.EInfoTarget;
import com.autoupdater.gui.window.EWindowStatus;
import com.autoupdater.gui.window.GuiClientWindow;

public class WindowOperations {
    private final Gui2ClientAdapter adapter;

    public WindowOperations(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
    }

    public WindowOperations configureClientWindow(GuiClientWindow clientWindow) {
        clientWindow.setSettings(adapter.environmentData());

        clientWindow.bindCheckUpdatesButton(new CheckUpdateTriggerListener(adapter),
                new CheckUpdateTriggerListener(adapter));
        clientWindow.bindInstallUpdatesButton(new InstallUpdateTriggerListener(adapter),
                new InstallUpdateTriggerListener(adapter));
        clientWindow.bindCancelDownloadButton(new CancelDownloadsTriggerListener(adapter),
                new CancelDownloadsTriggerListener(adapter));

        for (final Program program : adapter.dataStorage().getProgramsThatShouldBeDisplayed()) {
            ProgramSettings programSettings = program.findProgramSettings(adapter.client()
                    .getProgramsSettings());
            if (programSettings != null)
                clientWindow.bindProgramLauncher(program, new RunCommandActionListener(adapter,
                        programSettings));
        }
        return this;
    }

    public UpdateInformationPanel getUpdateInformationPanel(Update update) {
        return adapter.clientWindow().getUpdateInformationPanel(update);
    }

    public WindowOperations setInstallationInactive() {
        adapter.clientWindow().setProgressBarInactive();
        return this;
    }

    public WindowOperations setInstallationIndetermined() {
        adapter.clientWindow().setProgressBarIndetermined();
        return this;
    }

    public WindowOperations setInstallationProgress(int numberOfUpdatesBeingInstalled,
            int numberOfUpdatesMarkedAsDone) {
        if (adapter.clientWindow() != null)
            adapter.clientWindow().setProgressBar(numberOfUpdatesBeingInstalled,
                    numberOfUpdatesMarkedAsDone);
        return this;
    }

    public WindowOperations setState(EWindowStatus state) {
        adapter.clientWindow().setStatus(state);
        return this;
    }

    public WindowOperations setStatusMessage(String message) {
        adapter.clientWindow().setStatusMessage(message);
        return this;
    }

    public WindowOperations refreshGUI() {
        invokeLater(new RefreshGUI());
        return this;
    }

    public WindowOperations reportQuiet(String message) {
        adapter.clientWindow().setStatusMessage(message);
        return this;
    }

    public WindowOperations reportInfo(String title, String message, EInfoTarget target) {
        adapter.clientWindow().reportInfo(title, message, target);
        return this;
    }

    public WindowOperations reportWarning(String title, String message, EInfoTarget target) {
        adapter.clientWindow().reportWarning(title, message, target);
        return this;
    }

    public WindowOperations reportError(String title, String message, EInfoTarget target) {
        adapter.clientWindow().reportError(title, message, target);
        return this;
    }

    public WindowOperations bindDownloadServicesToUpdateInformationPanels(
            FileAggregatedDownloadService aggregatedService) {
        for (FileDownloadService downloadService : aggregatedService.getServices()) {
            Update update = aggregatedService.getAdditionalMessage(downloadService);
            if (update != null && getUpdateInformationPanel(update) != null)
                getUpdateInformationPanel(update).setDownloadService(downloadService);
        }
        return this;
    }

    private class RefreshGUI implements Runnable {
        @Override
        public void run() {
            adapter.clientWindow().refresh();
        }
    }
}
