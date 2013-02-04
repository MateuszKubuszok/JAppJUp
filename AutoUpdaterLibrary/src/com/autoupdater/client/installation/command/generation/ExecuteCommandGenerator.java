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
        command = command.replace("{F}", update.getOriginalName());
        command = command.replace("{U}", update.getFile().getAbsolutePath());
        command = command.replace("{T}", calculateTarget(programSettings, update));
        command = command.replace("{I}", programSettings.getPathToProgramDirectory());
        command = command.replace("{R}", update.getRelativePath());
        return command;
    }
}
