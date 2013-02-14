package com.autoupdater.client.download.events;

/**
 * Listens to changes/progress in some download service.
 */
public interface IDownloadListener {
    /**
     * Triggered when service connected to server.
     * 
     * @param event
     *            download event
     */
    public void downloadStarted(IDownloadEvent event);

    /**
     * Triggered when service actually downloads from server.
     * 
     * @param event
     *            download event
     */
    public void downloadInProgress(IDownloadEvent event);

    /**
     * Triggered when service downloaded all information from server.
     * 
     * @param event
     *            download event
     */
    public void downloadCompleted(IDownloadEvent event);

    /**
     * Triggered when service parsed downloaded content into expected output
     * format.
     * 
     * @param event
     *            download event
     */
    public void downloadProcessed(IDownloadEvent event);

    /**
     * Triggered when download was cancelled.
     * 
     * @param event
     *            download event
     */
    public void downloadCancelled(IDownloadEvent event);

    /**
     * Triggered when download has failed.
     * 
     * @param event
     *            download event
     */
    public void downloadFailed(IDownloadEvent event);

    /**
     * Triggered when download finished, no matter with what result.
     * 
     * <p>
     * Triggered when download finished with status: PROCESSED, CANCELLED or
     * FAILED as last event.
     * </p>
     * 
     * @param event
     *            download event
     */
    public void downloadFinished(IDownloadEvent event);
}
