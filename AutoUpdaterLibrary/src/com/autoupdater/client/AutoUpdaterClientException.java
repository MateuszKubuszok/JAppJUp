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

    /**
     * Wraps {@link Throwable#addSuppressed(Throwable)} allowing chaining.
     * 
     * @param throwable
     *            suppressed throwable
     * @param resultClass
     *            expected return type
     * @return itself
     */
    @SuppressWarnings("unchecked")
    public <T extends AutoUpdaterClientException> T addSuppresed(Throwable throwable,
            Class<T> resultClass) {
        addSuppressed(throwable);
        return (T) this;
    }
}
