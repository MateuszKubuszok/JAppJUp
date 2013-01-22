package com.autoupdater.client.download;

import com.autoupdater.client.utils.services.ObservableService;

/**
 * Used for passing messages to/from DownloadServices.
 */
public class DownloadServiceMessage {
    private final String message;
    private final boolean InterruptedByError;
    private final ObservableService<DownloadServiceMessage> originalService;

    /**
     * Creates an instance of DownloadServiceMessage.
     * 
     * @param originalService
     *            original sender of the message
     * @param message
     *            message that should be passed
     */
    public DownloadServiceMessage(ObservableService<DownloadServiceMessage> originalService,
            String message) {
        this.originalService = originalService;
        this.message = message;
        InterruptedByError = false;
    }

    /**
     * Creates an instance of DownloadServiceMessage.
     * 
     * @param originalService
     *            original sender of the message
     * @param message
     *            message that should be passed
     * @param errorOccured
     *            whether or not error occurred
     */
    public DownloadServiceMessage(ObservableService<DownloadServiceMessage> originalService,
            String message, boolean errorOccured) {
        this.originalService = originalService;
        this.message = message;
        this.InterruptedByError = errorOccured;
    }

    /**
     * Returns message.
     * 
     * @return passed message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns this object as instance of DownloadServiceProgressMessage ,if it
     * is one or null, if it is just common DownloadServiceMessage.
     * 
     * @return this message casted or null
     */
    public DownloadServiceProgressMessage getProgressMessage() {
        return null;
    }

    /**
     * Returns original service that spawned message.
     * 
     * @return original service
     */
    public ObservableService<DownloadServiceMessage> getOriginalService() {
        return originalService;
    }

    /**
     * Informs whether or not download was interrupted.
     * 
     * @return true if download was interrupted
     */
    public boolean isInterruptedByError() {
        return InterruptedByError;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
