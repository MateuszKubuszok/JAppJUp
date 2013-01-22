package com.autoupdater.client.download.runnables;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import com.autoupdater.client.download.runnables.post.download.strategies.FilePostDownloadStrategy;
import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;

/**
 * Implementation downloading files.
 * 
 * <p>
 * Use FileDownloadStrategy.
 * </p>
 * 
 * <p>
 * Used by FileDownloadService.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.AbstractDownloadRunnable
 * @see com.autoupdater.client.download.runnables.post.download.strategies.FilePostDownloadStrategy
 * @see com.autoupdater.client.download.services.FileDownloadService
 */
public class FileDownloadRunnable extends AbstractDownloadRunnable<File> {
    /**
     * Creates PackagesInfoDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     * @param fileDestinationPath
     *            path to destination file
     */
    public FileDownloadRunnable(HttpURLConnection connection, String fileDestinationPath) {
        super(connection, fileDestinationPath);
    }

    @Override
    protected IPostDownloadStrategy<File> getPostDownloadStrategy() throws IOException {
        return new FilePostDownloadStrategy(getFileDestinationPath());
    }
}
