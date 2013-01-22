package com.autoupdater.gui.adapters.listeners;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;

public class ChangelogInfoNotificationListener implements IObserver<DownloadServiceMessage> {
    private final Gui2ClientAdapter adapter;
    private final ChangelogInfoAggregatedDownloadService aggregatedService;

    public ChangelogInfoNotificationListener(Gui2ClientAdapter adapter,
            ChangelogInfoAggregatedDownloadService aggregatedService) {
        this.adapter = adapter;
        this.aggregatedService = aggregatedService;
    }

    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        if (observable == aggregatedService.getNotifier())
            adapter.setStatusMessage("Fetching changelogs: " + aggregatedService.getState());
    }
}
