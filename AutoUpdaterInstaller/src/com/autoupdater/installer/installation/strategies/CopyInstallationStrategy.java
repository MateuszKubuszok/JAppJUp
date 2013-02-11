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
