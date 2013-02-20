package com.autoupdater.gui.adapter.runnables;

import static com.autoupdater.client.environment.AvailabilityFilter.filterUpdateNotInstalled;
import static com.autoupdater.gui.client.window.EInfoTarget.*;
import static com.autoupdater.gui.client.window.EWindowStatus.*;

import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.adapter.listeners.notification.InstallationNotificationListener;

public class InstallUpdatesRunnable implements Runnable {
    private final Gui2ClientAdapter adapter;
    private final FileAggregatedDownloadService aggregatedDownloadService;
    private final AggregatedInstallationService aggregatedInstallationService;

    public InstallUpdatesRunnable(Gui2ClientAdapter adapter,
            FileAggregatedDownloadService aggregatedDownloadService,
            AggregatedInstallationService aggregatedInstallationService) {
        this.adapter = adapter;
        this.aggregatedDownloadService = aggregatedDownloadService;
        this.aggregatedInstallationService = aggregatedInstallationService;
    }

    @Override
    public void run() {
        try {
            if (aggregatedDownloadService.getServices() == null
                    || aggregatedDownloadService.getServices().isEmpty()) {
                adapter.windowOperations().setState(UNINITIALIZED).setInstallationInactive()
                        .reportQuiet("There are no updates available to install");
                return;
            }

            adapter.windowOperations()
                    .reportInfo("Preparing to download", "Preparing download queues.", ALL)
                    .bindDownloadServicesToUpdateInformationPanels(aggregatedDownloadService);

            adapter.windowOperations().reportInfo("Downloading updates",
                    "Downloading updates from repositories.", ALL);
            aggregatedDownloadService.start();
            aggregatedDownloadService.joinThread();
            aggregatedDownloadService.throwExceptionIfErrorOccured();

            adapter.windowOperations().reportInfo("Preparing to install",
                    "Preparing downloaded updates to install.", ALL);
            aggregatedDownloadService.getResult();

            aggregatedInstallationService.getNotifier().addObserver(
                    new InstallationNotificationListener(adapter, aggregatedInstallationService));

            adapter.windowOperations().setState(INSTALLING_UPDATES)
                    .reportInfo("Installation in progress", "Updates are being installed", ALL);
            aggregatedInstallationService.start();
            aggregatedInstallationService.joinThread();
            aggregatedInstallationService.throwExceptionIfErrorOccured();
            aggregatedInstallationService.getResult();
        } catch (Throwable e) {
            adapter.windowOperations()
                    .reportError("Error occured during installation", e.getMessage(), ALL)
                    .setState(IDLE);
        } finally {
            if (hasAllUpdatesInstalledSuccessfully()) {
                adapter.clientOperations().cleanTemp();
                adapter.windowOperations()
                        .reportInfo("Installation finished",
                                "All updates were installed successfully.", ALL)
                        .setState(UNINITIALIZED).setInstallationInactive();
            } else {
                adapter.windowOperations()
                        .reportError(
                                "Installation failed",
                                "Not all updates were installed successfully, check details for more information.",
                                TOOLTIP).setState(IDLE).setInstallationInactive();
            }
            adapter.windowOperations().refreshGUI();
            adapter.installationUtils().CurrentInstallationThreadFinished();
        }
    }

    private boolean hasAllUpdatesInstalledSuccessfully() {
        return filterUpdateNotInstalled(adapter.dataStorage().getAvailableUpdates()).isEmpty();
    }
}
