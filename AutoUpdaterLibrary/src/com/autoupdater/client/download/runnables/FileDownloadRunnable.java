package com.autoupdater.client.download.runnables;

import static com.autoupdater.client.download.FileCache.*;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import com.autoupdater.client.download.EDownloadStatus;
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
    public void run() {
        if (isFileDownloaded(getFileDestinationPath())) {
            result = new File(getFileDestinationPath());
            try {
                reportChange("File retrived from cache", EDownloadStatus.PROCESSED);
            } catch (InterruptedException e) {
                reportCancelled();
            }
        } else {
            super.run();
            if (getState() == EDownloadStatus.PROCESSED)
                setFileDownloaded(getFileDestinationPath());
        }
    }

    @Override
    protected IPostDownloadStrategy<File> getPostDownloadStrategy() throws IOException {
        return new FilePostDownloadStrategy(getFileDestinationPath());
    }
}
