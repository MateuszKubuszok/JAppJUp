package com.autoupdater.gui.adapters.utils;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static net.jsdpu.process.executors.Commands.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;

import net.jsdpu.EOperatingSystem;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.Client;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.listeners.CheckUpdateTriggerListener;
import com.autoupdater.gui.adapters.listeners.InstallUpdateTriggerListener;
import com.autoupdater.gui.adapters.listeners.PackagesInfoNotificationListener;
import com.autoupdater.gui.adapters.runnables.CheckUpdatesRunnable;
import com.autoupdater.gui.adapters.runnables.InstallUpdatesRunnable;
import com.autoupdater.gui.window.GuiClientWindow;

public class AdapterUtils {
    private final Gui2ClientAdapter adapter;
    private final Client client;

    public AdapterUtils(Gui2ClientAdapter adapter, Client client) {
        this.adapter = adapter;
        this.client = client;
    }

    public void setUpClientWindow(GuiClientWindow clientWindow) {
        clientWindow.bindCheckUpdatesButton(new CheckUpdateTriggerListener(adapter),
                new CheckUpdateTriggerListener(adapter));
        clientWindow.bindInstallUpdatesButton(new InstallUpdateTriggerListener(adapter),
                new InstallUpdateTriggerListener(adapter));

        for (final Program program : adapter.getProgramsThatShouldBeDisplayed()) {
            ProgramSettings programSettings = program.findProgramSettings(client
                    .getProgramsSettings());
            if (programSettings != null)
                clientWindow.bindProgramLauncher(program, new RunCommandActionListener(
                        programSettings));
        }
    }

    public SortedSet<Program> queryServerForPackages() throws IOException, DownloadResultException {
        PackagesInfoAggregatedDownloadService aggregatedService = client
                .createPackagesInfoAggregatedDownloadService();
        aggregatedService.getNotifier().addObserver(
                new PackagesInfoNotificationListener(adapter, aggregatedService));

        aggregatedService.start();
        aggregatedService.joinThread();

        return aggregatedService.getResult();
    }

    public void queryServerForInformation() throws DownloadResultException, IOException,
            ProgramSettingsNotFoundException {
        SortedSet<Program> selectedPrograms = getSelectedPrograms();
        SortedSet<Package> selectedPackages = getSelectedPackages();

        UpdateInfoAggregatedDownloadService aggregatedUpdateInfoService = client
                .createUpdateInfoAggregatedDownloadService(selectedPackages);

        ChangelogInfoAggregatedDownloadService aggregatedChangelogInfoService = client
                .createChangelogInfoAggregatedDownloadService(selectedPackages);

        BugsInfoAggregatedDownloadService aggregatedBugsInfoService = client
                .createBugsInfoAggregatedDownloadService(selectedPrograms);

        new Thread(new CheckUpdatesRunnable(adapter, aggregatedUpdateInfoService,
                aggregatedChangelogInfoService, aggregatedBugsInfoService)).start();
    }

    public void installUpdates() throws ProgramSettingsNotFoundException, IOException {
        FileAggregatedDownloadService aggregatedDownloadService = client
                .createFileAggregatedDownloadService(adapter.getAvailableUpdates());
        AggregatedInstallationService aggregatedInstallationService = client
                .createInstallationAggregatedService(adapter.getAvailableUpdates());

        new Thread(new InstallUpdatesRunnable(adapter, aggregatedDownloadService,
                aggregatedInstallationService)).start();
    }

    public void markAllUpdatesAsIntendedToInstall() {
        if (adapter.isInitiated())
            for (Update update : adapter.getAvailableUpdates())
                if (update.getPackage().getVersionNumber().compareTo(update.getVersionNumber()) < 0)
                    update.setStatus(EUpdateStatus.SELECTED);
    }

    private SortedSet<Program> getSelectedPrograms() throws IOException, DownloadResultException {
        return client.getAvailabilityFilter()
                .findProgramsAvailableToInstallOrInstalledWithDefinedSettings(
                        adapter.getAvailableOnServer());
    }

    private SortedSet<Package> getSelectedPackages() throws IOException, DownloadResultException {
        return client.getAvailabilityFilter().findPackagesAvailableToHaveTheirUpdateChecked(
                adapter.getAvailableOnServer());
    }

    private class RunCommandActionListener implements ActionListener {
        private final ProgramSettings programSettings;

        RunCommandActionListener(ProgramSettings programSettings) {
            this.programSettings = programSettings;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ExecutorService executorService = newSingleThreadExecutor();
            executorService.submit(new RunCommand(programSettings));
        }
    }

    private class RunCommand implements Runnable {
        private final ProgramSettings programSettings;

        RunCommand(ProgramSettings programSettings) {
            this.programSettings = programSettings;
        }

        @Override
        public void run() {
            try {
                EOperatingSystem
                        .current()
                        .getProcessExecutor()
                        .execute(
                                convertMultipleConsoleCommands(wrapArgument(programSettings
                                        .getPathToProgram()))).rewind();
            } catch (IOException | InvalidCommandException e) {
                adapter.reportWarning(e.toString(), programSettings.getProgramName());
            }
        }
    }
}
