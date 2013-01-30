package com.autoupdater.gui.adapters.runnables;

import static com.autoupdater.client.environment.AvailabilityFilter.filterUpdateSelection;
import static com.autoupdater.gui.window.EWindowStatus.*;

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.listeners.BugsInfoNotificationListener;
import com.autoupdater.gui.adapters.listeners.ChangelogInfoNotificationListener;
import com.autoupdater.gui.adapters.listeners.UpdateInfoNotificationListener;

public class CheckUpdatesRunnable implements Runnable {
    private static final SortedSet<Update> DISPLAYED_UPDATES = new TreeSet<Update>();

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
                adapter.setState(IDLE);
            else
                adapter.setState(UNINITIALIZED);
        } finally {
            adapter.markAllUpdatesAsIntendedToInstall();

            if (availableUpdates == null || filterUpdateSelection(availableUpdates).isEmpty())
                adapter.setState(UNINITIALIZED);
            else {
                SortedSet<Update> notDisplayedUpdates = filterUpdatesNotification(filterUpdateSelection(availableUpdates));
                StringBuilder builder = new StringBuilder();
                for (Update update : notDisplayedUpdates)
                    builder.append(update.getPackage().getProgram()).append('/')
                            .append(update.getPackage()).append(" -> ")
                            .append(update.getVersionNumber()).append("\n")
                            .append(update.getChanges()).append("\n");

                adapter.setState(IDLE);
                if (!notDisplayedUpdates.isEmpty()) {
                    adapter.reportInfo("New updates are available", builder.toString());
                    DISPLAYED_UPDATES.addAll(notDisplayedUpdates);
                }
            }

            adapter.refreshGUI();
        }
    }

    public SortedSet<Update> filterUpdatesNotification(SortedSet<Update> availableUpdates) {
        SortedSet<Update> notDisplayedUpdates = new TreeSet<Update>(availableUpdates);
        notDisplayedUpdates.removeAll(DISPLAYED_UPDATES);
        return notDisplayedUpdates;
    }
}
