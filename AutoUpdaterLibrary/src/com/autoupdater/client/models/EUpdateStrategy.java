package com.autoupdater.client.models;

import com.autoupdater.client.installation.command.generation.CopyCommandGenerator;
import com.autoupdater.client.installation.command.generation.ExecuteCommandGenerator;
import com.autoupdater.client.installation.command.generation.ICommandGenerator;
import com.autoupdater.client.installation.command.generation.UnzipCommandGenerator;

/**
 * Defines the type of update that should be performed.
 */
public enum EUpdateStrategy {
    /**
     * Defines update as performed by extraction of ZIP archive into program's
     * (sub)directory.
     */
    UNZIP("Unzip", new UnzipCommandGenerator()),

    /**
     * Defines update as performed by copying file into program's
     * (sub)directory.
     */
    COPY("Copy", new CopyCommandGenerator()),

    /**
     * Defines update as performed by executing file through command passed by
     * Update information.
     */
    EXECUTE("Execute", new ExecuteCommandGenerator());

    private final String message;
    private final ICommandGenerator commandGenerator;

    private EUpdateStrategy(String message, ICommandGenerator commandGenerator) {
        this.message = message;
        this.commandGenerator = commandGenerator;
    }

    /**
     * Returns message describing update strategy.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns command generator for update strategy.
     * 
     * @see com.autoupdater.client.installation.command.generation.ICommandGenerator
     *
     * @return command generator
     */
    public ICommandGenerator getCommandGenerator() {
        return commandGenerator;
    }

    @Override
    public String toString() {
        return message.toLowerCase();
    }
}
