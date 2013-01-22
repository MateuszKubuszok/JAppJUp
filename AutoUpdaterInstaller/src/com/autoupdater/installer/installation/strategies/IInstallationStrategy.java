package com.autoupdater.installer.installation.strategies;

import java.io.File;
import java.io.IOException;

import net.jsdpu.process.executors.InvalidCommandException;


/**
 * Executes installation according to strategy.
 * 
 * <p>
 * Used by InstallationPerformer.
 * </p>
 * 
 * @see com.autoupdater.installer.InstallationPerformer
 */
public interface IInstallationStrategy {
    /**
     * according to strategy.
     * 
     * @param downloadedFile
     *            source file
     * @param destinationPath
     *            destinationPath (treating relies on chosen strategy)
     * @throws IOException
     *             thrown if IO error occurs during installation
     * @throws InvalidCommandException
     *             thrown if post-installation command is invalid
     */
    public void process(File downloadedFile, String destinationPath) throws IOException,
            InvalidCommandException;
}
