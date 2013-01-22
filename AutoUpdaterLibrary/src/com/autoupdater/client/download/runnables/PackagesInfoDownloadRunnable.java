package com.autoupdater.client.download.runnables;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;
import com.autoupdater.client.download.runnables.post.download.strategies.PackagesInfoPostDownloadStrategy;
import com.autoupdater.client.models.Program;

/**
 * Implementation downloading programs/packages info.
 * 
 * <p>
 * Use PackagesInfoDownloadStrategy.
 * </p>
 * 
 * <p>
 * Used by PackagesInfoDownloadService.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.AbstractDownloadRunnable
 * @see com.autoupdater.client.download.runnables.post.download.strategies.PackagesInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.services.PackagesInfoDownloadService
 */
public class PackagesInfoDownloadRunnable extends AbstractDownloadRunnable<SortedSet<Program>> {
    /**
     * Creates PackagesInfoDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     */
    public PackagesInfoDownloadRunnable(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected IPostDownloadStrategy<SortedSet<Program>> getPostDownloadStrategy() {
        return new PackagesInfoPostDownloadStrategy();
    }
}
