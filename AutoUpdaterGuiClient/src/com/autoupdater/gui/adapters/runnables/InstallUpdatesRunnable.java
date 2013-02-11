package com.autoupdater.gui.adapters.runnables;

import static com.autoupdater.client.environment.AvailabilityFilter.filterUpdateNotInstalled;
import static com.autoupdater.gui.window.EWindowStatus.*;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.listeners.InstallationNotificationListener;

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
                adapter.setState(UNINITIALIZED);
                adapter.setInstallationInactive();
                adapter.reportQuiet("There are no updates available to install");
                return;
            }

            adapter.reportInfo("Preparing to download", "Preparing download queues");
            adapter.bindDownloadServicesToUpdateInformationPanels(aggregatedDownloadService);

            adapter.reportInfo("Downloading updates", "Downloading updates from repositories");
            aggregatedDownloadService.start();
            aggregatedDownloadService.joinThread();

            adapter.reportInfo("Preparing to install", "Preparing downloaded updates to install");
            aggregatedDownloadService.getResult();

            aggregatedInstallationService.getNotifier().addObserver(
                    new InstallationNotificationListener(adapter, aggregatedInstallationService));

            adapter.setState(INSTALLING_UPDATES);
            adapter.reportInfo("Installation in progress", "Updates are being installed");
            aggregatedInstallationService.start();
            aggregatedInstallationService.joinThread();
            aggregatedInstallationService.getResult();
        } catch (DownloadResultException e) {
            adapter.reportError("Error occured during installation", e.getMessage());
            adapter.setState(IDLE);
        } finally {
            if (hasAllUpdatesInstalledSuccessfully()) {
                adapter.cleanTemp();
                adapter.reportInfo("Installation finished",
                        "All updates were installed successfully.");
                adapter.setState(UNINITIALIZED);
            } else {
                adapter.reportError("Installation failed",
                        "Not all updates were installed successfully, check details for more information.");
                adapter.setState(IDLE);
            }
            adapter.refreshGUI();
        }
    }

    private boolean hasAllUpdatesInstalledSuccessfully() {
        return filterUpdateNotInstalled(adapter.getAvailableUpdates()).isEmpty();
    }
}
