package com.autoupdater.gui.adapters.listeners;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;

public class PackagesInfoNotificationListener implements IObserver<DownloadServiceMessage> {
    private final Gui2ClientAdapter adapter;
    private final PackagesInfoAggregatedDownloadService aggregatedService;

    public PackagesInfoNotificationListener(Gui2ClientAdapter adapter,
            PackagesInfoAggregatedDownloadService aggregatedService) {
        this.adapter = adapter;
        this.aggregatedService = aggregatedService;
    }

    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        adapter.setStatusMessage("Checking repositories: " + aggregatedService.getState());
    }
}
