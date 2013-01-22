package com.autoupdater.installer.installation.strategies;

import java.io.File;
import java.io.IOException;

import net.jsdpu.EOperatingSystem;
import net.jsdpu.process.executors.Commands;
import net.jsdpu.process.executors.InvalidCommandException;


/**
 * Executes command passed into installer.
 */
public class ExecuteInstallationStrategy implements IInstallationStrategy {
    @Override
    public void process(File ignoredFile, String executedCommand) throws IOException,
            InvalidCommandException {
        EOperatingSystem.current().getProcessExecutor()
                .execute(Commands.convertConsoleCommands(executedCommand)).rewind();
    }
}
