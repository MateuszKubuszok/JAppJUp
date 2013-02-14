package com.autoupdater.client.download.events;

/**
 * Abstract listener implementation that can be used for listening to only some
 * specific events.
 * 
 * @see com.autoupdater.client.download.events.IDownloadEvent
 * @see com.autoupdater.client.download.events.IDownloadListener
 */
public abstract class AbstractDownloadListener implements IDownloadListener {
    @Override
    public void downloadStarted(IDownloadEvent event) {
    }

    @Override
    public void downloadInProgress(IDownloadEvent event) {
    }

    @Override
    public void downloadCompleted(IDownloadEvent event) {
    }

    @Override
    public void downloadProcessed(IDownloadEvent event) {
    }

    @Override
    public void downloadCancelled(IDownloadEvent event) {
    }

    @Override
    public void downloadFailed(IDownloadEvent event) {
    }

    @Override
    public void downloadFinished(IDownloadEvent event) {
    }
}
