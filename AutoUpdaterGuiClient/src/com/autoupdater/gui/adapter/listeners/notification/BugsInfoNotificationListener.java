package com.autoupdater.gui.adapter.listeners.notification;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;

public class BugsInfoNotificationListener implements IObserver<DownloadServiceMessage> {
    private final Gui2ClientAdapter adapter;
    private final BugsInfoAggregatedDownloadService aggregatedService;

    public BugsInfoNotificationListener(Gui2ClientAdapter adapter,
            BugsInfoAggregatedDownloadService aggregatedService) {
        this.adapter = adapter;
        this.aggregatedService = aggregatedService;
    }

    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        if (observable == aggregatedService.getNotifier())
            adapter.windowOperations().reportQuiet(
                    "Fetching known bugs: " + aggregatedService.getStatus());
    }
}
