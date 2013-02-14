package com.autoupdater.client.download.events;

/**
 * Listens to changes/progress in some aggregated download service.
 */
public interface IAggregatedDownloadListener {
    /**
     * Triggered when download queue has started.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueStarted(IAggregatedDownloadEvent event);

    /**
     * Triggered when download queue finished successfully.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueProcessed(IAggregatedDownloadEvent event);

    /**
     * Triggered when download queue was cancelled.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueCancelled(IAggregatedDownloadEvent event);

    /**
     * Triggered when download queue finished but some downloads failed.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueFailed(IAggregatedDownloadEvent event);

    /**
     * Triggered when download queue finished no matter with what results.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueFinished(IAggregatedDownloadEvent event);
}
