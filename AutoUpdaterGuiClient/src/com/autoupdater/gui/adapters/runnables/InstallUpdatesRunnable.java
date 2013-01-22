package com.autoupdater.gui.adapters.runnables;

import static com.autoupdater.client.environment.AvailabilityFilter.filterUpdateNotInstalled;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.installation.EInstallationStatus;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.listeners.InstallationNotificationListener;
import com.autoupdater.gui.window.EWindowStatus;

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
                adapter.setState(EWindowStatus.UNINITIALIZED);
                adapter.setInstallationInactive();
                adapter.setStatusMessage("There are no updates available to install");
                return;
            }

            adapter.setStatusMessage("Preparing download queues");
            adapter.bindDownloadServicesToUpdateInformationPanels(aggregatedDownloadService);

            adapter.setStatusMessage("Downloading updates from repositories");
            aggregatedDownloadService.start();
            aggregatedDownloadService.joinThread();

            adapter.setStatusMessage("Preparing downloaded updates to install");
            aggregatedDownloadService.getResult();

            aggregatedInstallationService.getNotifier().addObserver(
                    new InstallationNotificationListener(adapter, aggregatedInstallationService));

            adapter.setState(EWindowStatus.INSTALLING_UPDATES);
            adapter.reportInfo("Installation in progress", "Updates are being installed");
            aggregatedInstallationService.start();
            aggregatedInstallationService.joinThread();
            aggregatedInstallationService.getResult();

            adapter.setState(aggregatedInstallationService.getState() == EInstallationStatus.INSTALLED ? EWindowStatus.UNINITIALIZED
                    : EWindowStatus.IDLE);
        } catch (DownloadResultException e) {
            adapter.setStatusMessage("Error occured: " + e.getMessage());
            adapter.reportError("Error occured during installation", e.getMessage());
            adapter.setState(EWindowStatus.IDLE);
        } finally {
            if (filterUpdateNotInstalled(adapter.getAvailableUpdates()).isEmpty()) {
                adapter.reportInfo("Installation finished",
                        "All updates were installed successfully.");
                adapter.setState(EWindowStatus.UNINITIALIZED);
            } else {
                adapter.reportError("Installation failed",
                        "Not all updates were installed successfully, check details for more inforamtion.");
            }
            adapter.refreshGUI();
        }
    }
}
