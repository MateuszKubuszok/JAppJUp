package com.autoupdater.client.installation.command.generation;

import java.io.File;

import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;

public class UnzipCommandGenerator implements ICommandGenerator {
    @Override
    public String[] generateCommand(Update update, String pathToInstaller,
            ProgramSettings programSettings) throws InvalidCommandException {
        return new String[] { "java", "-jar", pathToInstaller, update.getUniqueIdentifer(),
                update.getUpdateStrategy().toString(), update.getFile().getAbsolutePath(),
                calculateTarget(programSettings, update) };
    }

    protected String calculateTarget(ProgramSettings programSettings, Update update) {
        String target = programSettings.getPathToProgramDirectory();

        if (target.endsWith("/") || target.endsWith("\\"))
            target = target.substring(0, target.length() - 1);

        String relativePath = update.getRelativePath();
        if (relativePath != null) {
            if (relativePath.startsWith("/") || relativePath.startsWith("\\"))
                relativePath = relativePath.substring(1);
            if (target.endsWith("/") || relativePath.endsWith("\\"))
                relativePath = relativePath.substring(0, relativePath.length() - 1);
            if (!relativePath.isEmpty())
                target += File.separator + relativePath;
        }

        return target;
    }
}
