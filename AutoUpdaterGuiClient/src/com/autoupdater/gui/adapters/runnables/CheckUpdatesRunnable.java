package com.autoupdater.gui.adapters.runnables;

import java.util.SortedSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.environment.AvailabilityFilter;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.listeners.BugsInfoNotificationListener;
import com.autoupdater.gui.adapters.listeners.ChangelogInfoNotificationListener;
import com.autoupdater.gui.adapters.listeners.UpdateInfoNotificationListener;
import com.autoupdater.gui.window.EWindowStatus;

public class CheckUpdatesRunnable implements Runnable {
    private final Gui2ClientAdapter adapter;
    private final UpdateInfoAggregatedDownloadService aggregatedUpdateInfoService;
    private final ChangelogInfoAggregatedDownloadService aggregatedChangelogInfoService;
    private final BugsInfoAggregatedDownloadService aggregatedBugsInfoService;

    public CheckUpdatesRunnable(Gui2ClientAdapter adapter,
            UpdateInfoAggregatedDownloadService aggregatedUpdateInfoService,
            ChangelogInfoAggregatedDownloadService aggregatedChangelogInfoService,
            BugsInfoAggregatedDownloadService aggregatedBugsInfoService) {
        this.adapter = adapter;
        this.aggregatedUpdateInfoService = aggregatedUpdateInfoService;
        this.aggregatedChangelogInfoService = aggregatedChangelogInfoService;
        this.aggregatedBugsInfoService = aggregatedBugsInfoService;
    }

    @Override
    public void run() {
        SortedSet<Update> availableUpdates = null;
        try {
            aggregatedUpdateInfoService.getNotifier().addObserver(
                    new UpdateInfoNotificationListener(adapter, aggregatedUpdateInfoService));
            aggregatedUpdateInfoService.start();
            aggregatedUpdateInfoService.joinThread();
            availableUpdates = aggregatedUpdateInfoService.getResult();
            adapter.setAvailableUpdates(availableUpdates);

            adapter.refreshGUI();

            aggregatedChangelogInfoService.getNotifier().addObserver(
                    new ChangelogInfoNotificationListener(adapter, aggregatedChangelogInfoService));
            aggregatedChangelogInfoService.start();
            aggregatedChangelogInfoService.joinThread();
            aggregatedChangelogInfoService.getResult();

            adapter.refreshGUI();

            aggregatedBugsInfoService.getNotifier().addObserver(
                    new BugsInfoNotificationListener(adapter, aggregatedBugsInfoService));
            aggregatedBugsInfoService.start();
            aggregatedBugsInfoService.joinThread();
            aggregatedBugsInfoService.getResult();
        } catch (DownloadResultException e) {
            adapter.reportError("Error occured while checking updates", e.getMessage());
            if (adapter.isInitiated())
                adapter.setState(EWindowStatus.IDLE);
            else
                adapter.setState(EWindowStatus.UNINITIALIZED);
        } finally {
            adapter.markAllUpdatesAsIntendedToInstall();

            if (availableUpdates == null
                    || AvailabilityFilter.filterUpdateSelection(availableUpdates).isEmpty())
                adapter.setState(EWindowStatus.UNINITIALIZED);
            else {
                String message = "";
                for (Update update : AvailabilityFilter.filterUpdateSelection(availableUpdates)) {
                    message += update.getPackage().getProgram() + "/" + update.getPackage()
                            + " -> " + update.getVersionNumber() + "\n";
                    message += update.getChanges() + "\n";
                }
                adapter.setState(EWindowStatus.IDLE);
                adapter.reportInfo("Updates are available", message);
            }

            adapter.refreshGUI();
        }
    }
}
