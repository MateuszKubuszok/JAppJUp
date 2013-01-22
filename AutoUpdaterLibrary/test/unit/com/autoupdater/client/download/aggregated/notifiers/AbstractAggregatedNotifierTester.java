package com.autoupdater.client.download.aggregated.notifiers;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier;
import com.autoupdater.client.utils.services.ObservableService;

public class AbstractAggregatedNotifierTester extends AbstractAggregatedDownloadNotifier<String> {
    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        hasChanged();
        notifyObservers(message);
    }
}