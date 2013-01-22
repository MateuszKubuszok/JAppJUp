package com.autoupdater.client.download.runnables;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.post.download.strategies.BugsInfoPostDownloadStrategy;
import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;
import com.autoupdater.client.models.BugEntry;

/**
 * Implementation downloading bugs.
 * 
 * <p>
 * Use BugInfoDownloadStrategy.
 * </p>
 * 
 * <p>
 * Used by BugsInfoDownloadService.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.AbstractDownloadRunnable
 * @see com.autoupdater.client.download.runnables.post.download.strategies.ChangelogInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.services.BugsInfoDownloadService
 */
public class BugsInfoDownloadRunnable extends AbstractDownloadRunnable<SortedSet<BugEntry>> {
    /**
     * Creates BugsInfoDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     */
    public BugsInfoDownloadRunnable(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected IPostDownloadStrategy<SortedSet<BugEntry>> getPostDownloadStrategy() {
        return new BugsInfoPostDownloadStrategy();
    }
}
