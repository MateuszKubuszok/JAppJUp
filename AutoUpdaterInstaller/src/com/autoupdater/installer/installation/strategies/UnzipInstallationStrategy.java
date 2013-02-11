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
