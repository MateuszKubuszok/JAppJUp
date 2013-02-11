package com.autoupdater.installer.backup;

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
            return EErrorCode.INVALID_ARGUMENT;

        for (File file : from.listFiles()) {
            File newFile = new File(to.getAbsoluteFile() + separator + file.getName());
            if (file.isFile()) {
                if (copyFile(file, newFile) != EErrorCode.SUCCESS)
                    return EErrorCode.BACKUP_ERROR;
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
                return EErrorCode.SUCCESS;

            createParentDirs(to);
            copy(from, to);

            return EErrorCode.SUCCESS;
        } catch (IOException e) {
            logger.error("Couldn't create backup of: " + from.getPath(), e);
            return EErrorCode.BACKUP_ERROR;
        }
    }
}
