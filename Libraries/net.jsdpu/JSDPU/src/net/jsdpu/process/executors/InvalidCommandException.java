package net.jsdpu.process.executors;

import net.jsdpu.SystemOperationException;

/**
 * Thrown when Command passed into AbstractProcessExecutor/Commands is invalid.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 * @see net.jsdpu.process.executors.Commands
 */
public class InvalidCommandException extends SystemOperationException {
    /**
     * Creates instance of InvalidCommandException.
     * 
     * @param message
     *            message to be passed
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
