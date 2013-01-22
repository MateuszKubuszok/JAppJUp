package com.autoupdater.client.download;

import com.autoupdater.client.AutoUpdaterClientException;

/**
 * Exception thrown when result is impossible to obtain: either result is not
 * yet ready, or download failed/was cancelled.
 */
public class DownloadResultException extends AutoUpdaterClientException {
    /**
     * Creates instance of DownloadResultException.
     * 
     * @param message
     *            message to be passed
     */
    public DownloadResultException(String message) {
        super(message);
    }
}
