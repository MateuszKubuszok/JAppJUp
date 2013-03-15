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
