package com.autoupdater.client.installation.command.generation;

import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;

public class ExecuteCommandGenerator extends UnzipCommandGenerator {
    @Override
    public String[] generateCommand(Update update, String pathToInstaller,
            ProgramSettings programSettings) throws InvalidCommandException {
        return new String[] { "java", "-jar", pathToInstaller, update.getUniqueIdentifer(),
                update.getUpdateStrategy().toString(), update.getFile().getAbsolutePath(),
                translateCommand(update, programSettings) };
    }

    protected String translateCommand(Update update, ProgramSettings programSettings) {
        String command = update.getCommand();
        command = command.replaceAll("{F}", update.getOriginalName());
        command = command.replaceAll("{U}", update.getFile().getAbsolutePath());
        command = command.replaceAll("{T}", calculateTarget(programSettings, update));
        command = command.replaceAll("{I}", programSettings.getPathToProgramDirectory());
        command = command.replaceAll("{R}", update.getRelativePath());
        return command;
    }
}
