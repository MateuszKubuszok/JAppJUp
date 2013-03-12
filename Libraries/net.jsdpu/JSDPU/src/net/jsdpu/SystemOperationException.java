package net.jsdpu;

/**
 * Thrown when unable to execute system process/execution resulted in failure.
 */
public class SystemOperationException extends Exception {
    /**
     * Creates exception with a message.
     * 
     * @param message
     *            passed message
     */
    public SystemOperationException(String message) {
        super(message);
    }
}