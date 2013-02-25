package com.autoupdater.client.installation.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;

/**
 * Class responsible for generating commands, that will call installer and pass
 * it arguments, such as source, target and strategy.
 * 
 * <p>
 * Used by InstallationRunnable.
 * </p>
 * 
 * @see com.autoupdater.client.installation.runnable.InstallationRunnable
 */
class CommandGenerationHelper {
    private final EnvironmentData environmentData;

    /**
     * Creates helper that will generate command for installer call.
     * 
     * @param environmentData
     *            environmentData instance
     */
    public CommandGenerationHelper(EnvironmentData environmentData) {
        this.environmentData = environmentData;
    }

    /**
     * Creates list of installation commands for a set of Updates to install.
     * 
     * @param updates Updates that should be installed
     * @return list of installation commands
     * @throws ProgramSettingsNotFoundException
     *             thrown if not ProgramSettings could be found for some of
     *             Updates
     * @throws InvalidCommandException
     *             thrown when there is error in shape of command
     */
    public List<String[]> getUpdateExecutionCommands(SortedSet<Update> updates)
            throws ProgramSettingsNotFoundException, InvalidCommandException {
        List<String[]> commands = new ArrayList<String[]>();
        for (Update update : updates)
            if (update != null && update.getStatus().isIntendedToBeChanged()
                    && update.getFile() != null)
                commands.add(getSingleUpdateExecutionCommand(update));
        return commands;
    }

    /**
     * Creates installation command for a single Update.
     * 
     * @param update
     *            update to install
     * @return installation command
     * @throws ProgramSettingsNotFoundException
     *             thrown if not ProgramSettings instance could be found for
     *             given Update
     * @throws InvalidCommandException
     *             thrown when there is error in shape of command
     */
    public String[] getSingleUpdateExecutionCommand(Update update)
            throws ProgramSettingsNotFoundException, InvalidCommandException {
        return update.getUpdateStrategy().getCommandGenerator()
                .generateCommand(update, getPathToInstaller(), findProgramSettings(update));
    }

    /**
     * Returns path to installer.
     * 
     * @return path to installer
     */
    private String getPathToInstaller() {
        return environmentData.getClientSettings().getPathToInstaller();
    }

    /**
     * Returns ProgramSettings for update.
     * 
     * @param update
     *            Update for which we search ProgramSettings
     * @return ProgramSettings
     * @throws ProgramSettingsNotFoundException
     *             thrown if ProgramSettings cannot be found
     */
    private ProgramSettings findProgramSettings(Update update)
            throws ProgramSettingsNotFoundException {
        return environmentData.findProgramSettingsForUpdate(update);
    }
}
