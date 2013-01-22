package com.autoupdater.client.download;

/**
 * Describes current download status.
 */
public enum EDownloadStatus {
    /**
     * Status of newly created service that didn't connect to server yet.
     */
    HASNT_STARTED("Download hasn't started yet", false),

    /**
     * Status of service that already connected to server, but didn't start the
     * actual download.
     */
    CONNECTED("Connected to server", false),

    /**
     * Status of service that currently download data from server.
     */
    IN_PROCESS("Download in process", false),

    /**
     * Status of service that completed download but didn't processed (e.g.
     * parsed) it yet.
     */
    COMPLETED("Download completed", false),

    /**
     * Status of service that finished all tasks and has the result of execution
     * available to obtain.
     */
    PROCESSED("Download processed", true),

    /**
     * Status of service that failed due to some kind of error.
     */
    FAILED("Download failed", true),

    /**
     * Status of service, that was cancelled by user.
     */
    CANCELLED("Download cancelled", true);

    private final String message;
    private final boolean downloadFinished;

    private EDownloadStatus(String message, boolean downloadFinished) {
        this.message = message;
        this.downloadFinished = downloadFinished;
    }

    /**
     * Returns message describing download status.
     * 
     * @return message describing download status
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns information whether or not Service/AggregatedService finished
     * download.
     * 
     * @return true if download is finished (no matter result), false if
     *         download is still in progress
     */
    public boolean isDownloadFinished() {
        return downloadFinished;
    }

    @Override
    public String toString() {
        return message;
    }
}
