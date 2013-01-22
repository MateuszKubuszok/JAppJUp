package com.autoupdater.client.installation.command.generation;

import java.io.File;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;

public class CopyCommandGenerator extends UnzipCommandGenerator {
    @Override
    protected String calculateTarget(ProgramSettings programSettings, Update update) {
        return super.calculateTarget(programSettings, update) + File.separator
                + update.getOriginalName();
    }
}
