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
package com.autoupdater.installer.installation.strategies;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.File;
import java.io.IOException;

import net.jsdpu.logger.Logger;

import com.google.common.io.Files;

/**
 * Copies file from temporary directory into program's directory.
 */
public class CopyInstallationStrategy implements IInstallationStrategy {
    private static final Logger logger = getLogger(CopyInstallationStrategy.class);

    @Override
    public void process(File downloadedFile, String destinationFilePath) throws IOException {
        logger.debug("Copies file " + downloadedFile.getPath() + " into " + destinationFilePath);
        File destinationFile = new File(destinationFilePath);
        Files.createParentDirs(destinationFile);
        Files.copy(downloadedFile, destinationFile);
        logger.debug("Copyed file " + downloadedFile.getPath() + " successfully");
    }
}
