package com.autoupdater.gui.adapters.utils;

import static com.google.common.collect.Collections2.filter;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static net.jsdpu.process.executors.Commands.convertMultipleConsoleCommands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;
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
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.listeners.CancelDownloadsTriggerListener;
import com.autoupdater.gui.adapters.listeners.CheckUpdateTriggerListener;
import com.autoupdater.gui.adapters.listeners.InstallUpdateTriggerListener;
import com.autoupdater.gui.adapters.listeners.PackagesInfoNotificationListener;
import com.autoupdater.gui.adapters.runnables.CheckUpdatesRunnable;
import com.autoupdater.gui.adapters.runnables.InstallUpdatesRunnable;
import com.autoupdater.gui.window.EInfoTarget;
import com.autoupdater.gui.window.GuiClientWindow;
import com.google.common.base.Predicate;

public class AdapterUtils {
    private final Gui2ClientAdapter adapter;
    private final Client client;

    private Thread currentInstallationThread;

    public AdapterUtils(Gui2ClientAdapter adapter, Client client) {
        this.adapter = adapter;
        this.client = client;
    }

    public void setUpClientWindow(GuiClientWindow clientWindow) {
        clientWindow.bindCheckUpdatesButton(new CheckUpdateTriggerListener(adapter),
                new CheckUpdateTriggerListener(adapter));
        clientWindow.bindInstallUpdatesButton(new InstallUpdateTriggerListener(adapter),
                new InstallUpdateTriggerListener(adapter));
        clientWindow.bindCancelDownloadButton(new CancelDownloadsTriggerListener(adapter),
                new CancelDownloadsTriggerListener(adapter));

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

    public FileAggregatedDownloadService installAllUpdates()
            throws ProgramSettingsNotFoundException, IOException {
        FileAggregatedDownloadService aggregatedDownloadService = client
                .createFileAggregatedDownloadService(getAllUpdates());
        AggregatedInstallationService aggregatedInstallationService = client
                .createInstallationAggregatedService(getAllUpdates());

        new Thread(new InstallUpdatesRunnable(adapter, aggregatedDownloadService,
                aggregatedInstallationService)).start();

        return aggregatedDownloadService;
    }

    public FileAggregatedDownloadService installUpdatesForProgram(Program program)
            throws ProgramSettingsNotFoundException, IOException {
        SortedSet<Update> updatesForProgram = getUpdatesForProgram(program);

        FileAggregatedDownloadService aggregatedDownloadService = client
                .createFileAggregatedDownloadService(updatesForProgram);
        AggregatedInstallationService aggregatedInstallationService = client
                .createInstallationAggregatedService(updatesForProgram);

        currentInstallationThread = new Thread(new InstallUpdatesRunnable(adapter,
                aggregatedDownloadService, aggregatedInstallationService));
        currentInstallationThread.start();

        return aggregatedDownloadService;
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

    private SortedSet<Update> getAllUpdates() {
        return adapter.getAvailableUpdates();
    }

    private SortedSet<Update> getUpdatesForProgram(final Program program) {
        return new TreeSet<Update>(filter(adapter.getAvailableUpdates(), new Predicate<Update>() {
            @Override
            public boolean apply(Update update) {
                return update != null && update.getPackage() != null
                        && update.getPackage().getProgram() != null
                        && update.getPackage().getProgram().equals(program);
            }
        }));
    }

    private class RunCommandActionListener implements ActionListener {
        private final ProgramSettings programSettings;
        private final Program program;

        RunCommandActionListener(ProgramSettings programSettings) {
            this.programSettings = programSettings;
            this.program = ProgramBuilder.builder().setName(programSettings.getProgramName())
                    .setPathToProgramDirectory(programSettings.getPathToProgramDirectory())
                    .setServerAddress(programSettings.getServerAddress()).build();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ExecutorService executorService = newSingleThreadExecutor();
            executorService.submit(new RunCommand(programSettings, program));
        }
    }

    private class RunCommand implements Runnable {
        private final ProgramSettings programSettings;
        private final Program program;

        RunCommand(ProgramSettings programSettings, Program program) {
            this.programSettings = programSettings;
            this.program = program;
        }

        @Override
        public void run() {
            if (!getUpdatesForProgram(program).isEmpty())
                try {
                    adapter.installUpdatesForProgram(program);
                    currentInstallationThread.join();
                } catch (InterruptedException ex) {
                }

            try {
                adapter.reportInfo(programSettings.getProgramName(),
                        programSettings.getProgramName() + " is starting up", EInfoTarget.ALL);
                EOperatingSystem
                        .current()
                        .getProcessExecutor()
                        .execute(convertMultipleConsoleCommands(programSettings.getPathToProgram()))
                        .rewind();
            } catch (IOException | InvalidCommandException e) {
                adapter.reportWarning(e.toString(), programSettings.getProgramName(),
                        EInfoTarget.ALL);
            }
        }
    }
}
