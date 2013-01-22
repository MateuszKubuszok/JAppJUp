package com.autoupdater.client;

/**
 * Superclass of all exceptions thrown by Client library.
 */
public class AutoUpdaterClientException extends Exception {
    /**
     * Creates instance of AutoUpdaterClientException.
     * 
     * @param message
     *            message to be passed
     */
    public AutoUpdaterClientException(String message) {
        super(message);
    }
}
