package com.autoupdater.client.download.services;

import static com.autoupdater.client.download.EDownloadStatus.*;

import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.events.IDownloadEvent;

/**
 * Implementation of IDownloadEvent.
 * 
 * @see com.autoupdater.client.download.events.IDownloadEvent
 */
class DownloadEventImpl implements IDownloadEvent {
    private final AbstractDownloadService<?> downloadService;
    private final EDownloadStatus status;
    private final String message;
    private final Double progress;

    /**
     * Creates event for download (in progress).
     * 
     * @param downloadService
     *            service being source of event
     * @param message
     *            message to pass
     * @param progress
     *            current progress of download
     */
    DownloadEventImpl(AbstractDownloadService<?> downloadService, String message, Double progress) {
        this.downloadService = downloadService;
        this.message = message;
        this.progress = progress;
        this.status = downloadService.getStatus();
    }

    /**
     * Creates event for download after/before actual data download.
     * 
     * @param downloadService
     *            service being source of event
     * @param message
     *            message to pass
     */
    DownloadEventImpl(AbstractDownloadService<?> downloadService, String message) {
        this(downloadService, message, (downloadService.getStatus().equals(COMPLETED)
                || downloadService.getStatus().equals(PROCESSED) ? 1.0 : 0.0));
    }

    @Override
    public boolean isInProcess() {
        return status.equals(IN_PROCESS);
    }

    @Override
    public boolean isCompleted() {
        return status.equals(COMPLETED);
    }

    @Override
    public boolean isProcessed() {
        return status.equals(PROCESSED);
    }

    @Override
    public boolean isCancelled() {
        return status.equals(CANCELLED);
    }

    @Override
    public boolean isFailed() {
        return status.equals(FAILED);
    }

    @Override
    public boolean isFinished() {
        return status.isDownloadFinished();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Double getProgress() {
        return progress;
    }

    @Override
    public AbstractDownloadService<?> getSource() {
        return downloadService;
    }

    @Override
    public EDownloadStatus getStatus() {
        return status;
    }
}
