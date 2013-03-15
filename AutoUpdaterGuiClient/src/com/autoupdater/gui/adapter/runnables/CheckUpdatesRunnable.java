/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package com.autoupdater.gui.adapter.runnables;

import static com.autoupdater.client.environment.AvailabilityFilter.filterUpdateSelection;
import static com.autoupdater.client.models.VersionNumber.UNVERSIONED;
import static com.autoupdater.gui.client.window.EInfoTarget.TOOLTIP;
import static com.autoupdater.gui.client.window.EWindowStatus.*;

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.adapter.listeners.notification.BugsInfoNotificationListener;
import com.autoupdater.gui.adapter.listeners.notification.ChangelogInfoNotificationListener;
import com.autoupdater.gui.adapter.listeners.notification.UpdateInfoNotificationListener;

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
        SortedSet<Update> availableUpdates = adapter.dataStorage().getAvailableUpdates();
        try {
            aggregatedUpdateInfoService.getNotifier().addObserver(
                    new UpdateInfoNotificationListener(adapter, aggregatedUpdateInfoService));
            aggregatedUpdateInfoService.start();
            aggregatedUpdateInfoService.joinThread();
            aggregatedUpdateInfoService.throwExceptionIfErrorOccured();
            availableUpdates.addAll(aggregatedUpdateInfoService.getResult());
            adapter.dataStorage().setAvailableUpdates(availableUpdates);

            adapter.windowOperations().refreshGUI();

            aggregatedChangelogInfoService.getNotifier().addObserver(
                    new ChangelogInfoNotificationListener(adapter, aggregatedChangelogInfoService));
            aggregatedChangelogInfoService.start();
            aggregatedChangelogInfoService.joinThread();
            aggregatedChangelogInfoService.throwExceptionIfErrorOccured();
            aggregatedChangelogInfoService.getResult();

            adapter.windowOperations().refreshGUI();

            aggregatedBugsInfoService.getNotifier().addObserver(
                    new BugsInfoNotificationListener(adapter, aggregatedBugsInfoService));
            aggregatedBugsInfoService.start();
            aggregatedBugsInfoService.joinThread();
            aggregatedBugsInfoService.throwExceptionIfErrorOccured();
            aggregatedBugsInfoService.getResult();
        } catch (Throwable e) {
            adapter.windowOperations().reportError("Error occured while checking updates",
                    e.getMessage(), TOOLTIP);
            if (adapter.dataStorage().isInitiated())
                adapter.windowOperations().setState(IDLE);
            else
                adapter.windowOperations().setState(UNINITIALIZED);
        } finally {
            adapter.installationUtils().markAllUpdatesAsIntendedToInstall();

            if (availableUpdates == null || filterUpdateSelection(availableUpdates).isEmpty())
                adapter.windowOperations().setState(UNINITIALIZED);
            else {
                SortedSet<Update> notDisplayedUpdates = filterUpdatesNotification(filterUpdateSelection(availableUpdates));
                StringBuilder builder = new StringBuilder();
                for (Update update : notDisplayedUpdates)
                    builder.append(update.getPackage().getProgram())
                            .append('/')
                            .append(update.getPackage().getName())
                            .append(' ')
                            .append(update.getPackage().getVersionNumber().equals(UNVERSIONED) ? "not installed"
                                    : update.getPackage().getVersionNumber()).append(" -> ")
                            .append(update.getVersionNumber()).append("\n")
                            .append(update.getChanges()).append("\n");

                adapter.windowOperations().setState(IDLE);
                if (!notDisplayedUpdates.isEmpty()) {
                    adapter.windowOperations().reportInfo("New updates are available",
                            builder.toString(), TOOLTIP);
                    DISPLAYED_UPDATES.addAll(notDisplayedUpdates);
                }
            }

            adapter.windowOperations().refreshGUI();
        }
    }

    public SortedSet<Update> filterUpdatesNotification(SortedSet<Update> availableUpdates) {
        SortedSet<Update> notDisplayedUpdates = new TreeSet<Update>(availableUpdates);
        notDisplayedUpdates.removeAll(DISPLAYED_UPDATES);
        return notDisplayedUpdates;
    }
}
