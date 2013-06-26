/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package com.autoupdater.client.installation.command.generation;

import static net.jsdpu.JavaSystemUtils.getJavaExecutablePath;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;

/**
 * Generates update for execute type command.
 */
public class ExecuteCommandGenerator extends UnzipCommandGenerator {
    @Override
    public String[] generateCommand(Update update, String pathToInstaller,
            ProgramSettings programSettings) throws InvalidCommandException {
        if (pathToInstaller.toLowerCase().endsWith(".jar"))
            return new String[] { getJavaExecutablePath(), "-jar", pathToInstaller,
                    update.getUniqueIdentifer(), update.getUpdateStrategy().toString(),
                    update.getFile().getAbsolutePath(), translateCommand(update, programSettings) };
        return new String[] { pathToInstaller, update.getUniqueIdentifer(),
                update.getUpdateStrategy().toString(), update.getFile().getAbsolutePath(),
                translateCommand(update, programSettings) };
    }

    /**
     * Translates generic command downloaded from server into actual local
     * command.
     * 
     * @param update
     *            update to execute
     * @param programSettings
     *            program's settings
     * @return actual command
     */
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
