package com.autoupdater.client.download.services;

import java.io.File;
import java.net.HttpURLConnection;

import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.FileDownloadRunnable;

/**
 * Service, that download file from given connection, and returns result as a
 * File.
 */
public class FileDownloadService extends AbstractDownloadService<File> {
    /**
     * Creates instance of FileDownloadService.
     * 
     * @param connection
     *            connection used to obtain data
     * @param fileDestinationPath
     *            path to file where result should be stored
     */
    public FileDownloadService(HttpURLConnection connection, String fileDestinationPath) {
        super(connection, fileDestinationPath);
    }

    @Override
    protected AbstractDownloadRunnable<File> getRunnable() {
        return new FileDownloadRunnable(getConnection(), getFileDestinationPath());
    }
}
