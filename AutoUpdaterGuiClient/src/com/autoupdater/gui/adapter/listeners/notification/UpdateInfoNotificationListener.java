package com.autoupdater.gui.adapter.listeners.notification;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;

public class UpdateInfoNotificationListener implements IObserver<DownloadServiceMessage> {
    private final Gui2ClientAdapter adapter;
    private final UpdateInfoAggregatedDownloadService aggregatedService;

    public UpdateInfoNotificationListener(Gui2ClientAdapter adapter,
            UpdateInfoAggregatedDownloadService aggregatedService) {
        this.adapter = adapter;
        this.aggregatedService = aggregatedService;
    }

    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        if (observable == aggregatedService.getNotifier())
            adapter.windowOperations().reportQuiet(
                    "Fetching update information: " + aggregatedService.getStatus());
    }
}
