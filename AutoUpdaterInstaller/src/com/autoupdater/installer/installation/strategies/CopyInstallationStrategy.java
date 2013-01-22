package com.autoupdater.installer.installation.strategies;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

/**
 * Copies file from temporary directory into program's directory.
 */
public class CopyInstallationStrategy implements IInstallationStrategy {
    @Override
    public void process(File downloadFile, String destinationFilePath) throws IOException {
        File destinationFile = new File(destinationFilePath);
        Files.createParentDirs(destinationFile);
        Files.copy(downloadFile, destinationFile);
    }
}
