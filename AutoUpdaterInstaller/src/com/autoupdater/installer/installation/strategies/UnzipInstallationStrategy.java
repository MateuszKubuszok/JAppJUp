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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.jsdpu.logger.Logger;

import com.google.common.io.Files;

/**
 * Unzips update into program's directory.
 */
public class UnzipInstallationStrategy implements IInstallationStrategy {
    private static final Logger logger = getLogger(UnzipInstallationStrategy.class);

    @Override
    public void process(File downloadedFile, String destinationDirectoryPath) throws IOException {
        logger.debug("Unzips file " + downloadedFile.getPath() + " into "
                + destinationDirectoryPath);
        File destinationDirectory = new File(destinationDirectoryPath);

        ZipInputStream zis = new ZipInputStream(new FileInputStream(downloadedFile));
        ZipEntry entry;
        File destinationFile;
        FileOutputStream fos;

        byte[] bytes = new byte[8192];
        int read = 0;

        while ((entry = zis.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                destinationFile = new File(destinationDirectory.getAbsolutePath(), entry.getName());
                Files.createParentDirs(destinationFile);
                destinationFile.createNewFile();
                fos = new FileOutputStream(destinationFile);

                while ((read = zis.read(bytes)) != -1)
                    fos.write(bytes, 0, read);

                fos.flush();
                fos.close();
            }
        }

        zis.close();
        logger.debug("File " + downloadedFile.getPath() + "uznipped successfully");
    }
}
