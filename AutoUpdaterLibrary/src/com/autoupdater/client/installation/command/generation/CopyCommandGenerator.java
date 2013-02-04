package com.autoupdater.client.installation.command.generation;

import static java.io.File.separator;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;

/**
 * Generates update for copy-and-overwrite type update.
 */
public class CopyCommandGenerator extends UnzipCommandGenerator {
    @Override
    protected String calculateTarget(ProgramSettings programSettings, Update update) {
        return super.calculateTarget(programSettings, update) + separator
                + update.getOriginalName();
    }
}
