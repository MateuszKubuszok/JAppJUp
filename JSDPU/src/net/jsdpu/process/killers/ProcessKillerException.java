package net.jsdpu.process.killers;

import net.jsdpu.SystemOperationException;

/**
 * Thrown when ProcessKiller is unable to kill process.
 */
public class ProcessKillerException extends SystemOperationException {
    /**
     * Creates exception with a message.
     * 
     * @param message
     *            passed message
     */
    public ProcessKillerException(String message) {
        super(message);
    }
}
