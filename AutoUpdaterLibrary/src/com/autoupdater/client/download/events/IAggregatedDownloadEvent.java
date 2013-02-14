package com.autoupdater.client.download.events;

import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.aggregated.services.AbstractAggregatedDownloadService;

/**
 * Contains information about download queue status.
 * 
 * <p>
 * Sent by classes responsible for downloading content in queues.
 * </p>
 */
public interface IAggregatedDownloadEvent {
    /**
     * Whether download queue started execution.
     * 
     * @return true if downloads started
     */
    public boolean isInProcess();

    /**
     * Whether download queue finished with success.
     * 
     * @return true if all downloads finished successfully
     */
    public boolean isProcessed();

    /**
     * Whether download queue was cancelled.
     * 
     * @return true if queue was cancelled
     */
    public boolean isCancelled();

    /**
     * Whether downloads finished and some of them failed.
     * 
     * @return true if queue was executed and some downloads failed
     */
    public boolean isFailed();

    /**
     * Whether download queue was finished no matter with what results.
     * 
     * @return true if queue execution finished
     */
    public boolean isFinished();

    /**
     * Returns number of all executed downloads.
     * 
     * @return all finished downloads number
     */
    public int getFinishedDownloadsNumber();

    /**
     * Returns number of all downloads.
     * 
     * @return all downloads number
     */
    public int getAllDownloadsNumber();

    /**
     * Returns message bound to event.
     * 
     * @return message for event
     */
    public String getMessage();

    /**
     * Get parental event from DownloadService that triggered update in
     * AggregatedDownloadService.
     * 
     * @return parental download event if there is any
     */
    public IDownloadEvent getParentEvent();

    /**
     * Returns current status of download queue.
     * 
     * @return download queue status
     */
    public EDownloadStatus getStatus();

    /**
     * Returns service being the source of event.
     * 
     * @return service being source of event
     */
    public AbstractAggregatedDownloadService<?, ?, ?, ?, ?> getSource();
}
