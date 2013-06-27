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
package com.autoupdater.installer.backup;

import static com.autoupdater.commons.error.codes.EErrorCode.*;
import static com.autoupdater.commons.installer.configuration.InstallerConfiguration.BACKUP_DIRECTORY;
import static com.google.common.io.Files.*;
import static java.io.File.separator;
import static net.jsdpu.logger.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.jsdpu.logger.Logger;

import com.autoupdater.commons.error.codes.EErrorCode;

/**
 * Creates backup for given file/directory.
 * 
 * <p>
 * Backup is stored inside {backupDirectory}/{currentDate}/{updateID} catalog.
 * </p>
 * 
 * <p>
 * Used by InstallationPerformer.
 * </p>
 * 
 * @see com.autoupdater.installer.InstallationPerformer
 */
public class BackupPerformer {
    private static final Logger logger = getLogger(BackupPerformer.class);

    /**
     * Creates backup for given file/directory.
     * 
     * @param id
     *            Update's ID (used in path generation)
     * @param source
     *            path to source directory
     * @return error code describing result of operation
     */
    public EErrorCode createBackup(String id, String source) {
        logger.info("Performing backup of: " + source);
        DateFormat date = new SimpleDateFormat("YMdHms");
        File sourceFile = new File(source);
        String backupDestinationPath = BACKUP_DIRECTORY + separator + date.format(new Date())
                + separator + id + separator + sourceFile.getName();
        File backupDestination = new File(backupDestinationPath);

        return sourceFile.isDirectory() ? copyDirectory(sourceFile, backupDestination) : copyFile(
                sourceFile, backupDestination);
    }

    /**
     * Makes backup of a directory.
     * 
     * @param from
     *            source directory
     * @param to
     *            target directory
     * @return backup result
     */
    private EErrorCode copyDirectory(File from, File to) {
        if (!to.exists())
            to.mkdirs();

        if (from.listFiles() == null)
            return INVALID_ARGUMENT;

        for (File file : from.listFiles()) {
            File newFile = new File(to.getAbsoluteFile() + separator + file.getName());
            if (file.isFile()) {
                if (copyFile(file, newFile) != SUCCESS)
                    return BACKUP_ERROR;
            } else if (file.isDirectory())
                copyDirectory(file, newFile);
        }

        return EErrorCode.SUCCESS;
    }

    /**
     * Makes backup of a single file.
     * 
     * @param from
     *            source file
     * @param to
     *            target file
     * @return backup result
     */
    private EErrorCode copyFile(File from, File to) {
        try {
            if (!from.exists())
                return SUCCESS;

            createParentDirs(to);
            copy(from, to);

            return SUCCESS;
        } catch (IOException e) {
            logger.error("Couldn't create backup of: " + from.getPath(), e);
            return BACKUP_ERROR;
        }
    }
}
