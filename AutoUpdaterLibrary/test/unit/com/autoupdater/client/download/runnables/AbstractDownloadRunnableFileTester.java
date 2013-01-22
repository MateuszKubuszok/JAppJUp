package com.autoupdater.client.download.runnables;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.post.download.strategies.FilePostDownloadStrategy;
import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;

public class AbstractDownloadRunnableFileTester extends AbstractDownloadRunnable<File> {
    public AbstractDownloadRunnableFileTester(HttpURLConnection connection,
            String fileDestinationPath) {
        super(connection, fileDestinationPath);
    }

    @Override
    protected IPostDownloadStrategy<File> getPostDownloadStrategy() throws IOException {
        return new FilePostDownloadStrategy(new File(getFileDestinationPath()));
    }
}