package com.autoupdater.client.download.events;

import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.services.AbstractDownloadService;

/**
 * Contains information about download status progress.
 * 
 * Sent by classes responsible for downloading content.
 */
public interface IDownloadEvent {
    /**
     * Whether download was cancelled.
     * 
     * @return true if download was cancelled
     */
    public boolean isCancelled();

    /**
     * Whether download has failed.
     * 
     * @return true if download has failed
     */
    public boolean isFailed();

    /**
     * Whether download was finished - no matter with what result.
     * 
     * @return true if download was finished one way or another
     */
    public boolean isFinished();

    /**
     * Whether download is in progress.
     * 
     * @return true if download in progress
     */
    public boolean isInProcess();

    /**
     * Whether download was completed successfully.
     * 
     * @return true if download completed successfully
     */
    public boolean isSuccessful();

    /**
     * Return message bound to the event.
     * 
     * @return message
     */
    public String getMessage();

    /**
     * Return current process of download.
     * 
     * @return
     */
    public Double getProgress();

    /**
     * Return source of event.
     * 
     * @return source of event
     */
    public AbstractDownloadService<?> getSource();

    /**
     * Return current download status.
     * 
     * @return current status
     */
    public EDownloadStatus getStatus();
}
