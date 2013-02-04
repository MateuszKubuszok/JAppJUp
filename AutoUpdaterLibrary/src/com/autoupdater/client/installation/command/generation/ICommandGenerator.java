package com.autoupdater.client.installation.command.generation;

import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;

/**
 * Generates command for given strategy.
 */
public interface ICommandGenerator {
    /**
     * Generates update command for some strategy.
     * 
     * @param update
     *            update to install
     * @param pathToInstaller
     *            path to installer
     * @param programSettings
     *            program's settings
     * @return actual installation command
     * @throws InvalidCommandException
     *             thrown if resulting command is invalid
     */
    public String[] generateCommand(Update update, String pathToInstaller,
            ProgramSettings programSettings) throws InvalidCommandException;
}
