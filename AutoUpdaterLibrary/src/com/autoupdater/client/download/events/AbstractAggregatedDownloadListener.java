package com.autoupdater.client.download.events;

/**
 * Abstract aggregated download listener implementation that can be used for
 * listening to only some specific events.
 * 
 * @see com.autoupdater.client.download.events.IAggregatedDownloadEvent
 * @see com.autoupdater.client.download.events.IAggregatedDownloadListener
 */
public abstract class AbstractAggregatedDownloadListener implements IAggregatedDownloadListener {
    @Override
    public void downloadQueueStarted(IAggregatedDownloadEvent event) {
    }

    @Override
    public void downloadQueueProcessed(IAggregatedDownloadEvent event) {
    }

    @Override
    public void downloadQueueCancelled(IAggregatedDownloadEvent event) {
    }

    @Override
    public void downloadQueueFailed(IAggregatedDownloadEvent event) {
    }

    @Override
    public void downloadQueueFinished(IAggregatedDownloadEvent event) {
    }
}
