package com.autoupdater.client.installation;

/**
 * Used for passing messages to/from InstallationService.
 */
public class InstallationServiceMessage {
    private final String  message;
    private final boolean interruptedByError;

    /**
     * Creates new installation message.
     * 
     * @param message
     *            message to pass
     */
    public InstallationServiceMessage(String message) {
        this.message = message;
        interruptedByError = false;
    }

    /**
     * Creates new installation message with information about error.
     * 
     * @param message
     *            message to pass
     * @param errorOccured
     *            whether or not error occurred
     */
    public InstallationServiceMessage(String message, boolean errorOccured) {
        this.message = message;
        this.interruptedByError = errorOccured;
    }

    /**
     * Returns passed message.
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Whether or not installation service was interrupted by error.
     * 
     * @return true if error occurred, false otherwise
     */
    public boolean isInterruptedByError() {
        return interruptedByError;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
