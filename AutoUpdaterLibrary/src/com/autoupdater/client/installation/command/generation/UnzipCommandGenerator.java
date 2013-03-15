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

import static java.io.File.separator;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Update;

/**
 * Generates command for unzipped update.
 */
public class UnzipCommandGenerator implements ICommandGenerator {
    @Override
    public String[] generateCommand(Update update, String pathToInstaller,
            ProgramSettings programSettings) throws InvalidCommandException {
        if (pathToInstaller.toLowerCase().endsWith(".jar"))
            return new String[] { "java", "-jar", pathToInstaller, update.getUniqueIdentifer(),
                    update.getUpdateStrategy().toString(), update.getFile().getAbsolutePath(),
                    calculateTarget(programSettings, update) };
        return new String[] { pathToInstaller, update.getUniqueIdentifer(),
                update.getUpdateStrategy().toString(), update.getFile().getAbsolutePath(),
                calculateTarget(programSettings, update) };
    }

    /**
     * Calculates target directory for an update.
     * 
     * @param programSettings
     *            program's settings
     * @param update
     *            udate to install
     * @return target directory
     */
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
                target += separator + relativePath;
        }

        return target;
    }
}
