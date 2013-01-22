package com.autoupdater.client.download.runnables;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.post.download.strategies.ChangelogInfoPostDownloadStrategy;
import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;
import com.autoupdater.client.models.ChangelogEntry;

/**
 * Implementation downloading changelogs.
 * 
 * <p>
 * Use ChangelogInfoDownloadStrategy.
 * </p>
 * 
 * <p>
 * Used by ChangelogInfoDownloadService.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.AbstractDownloadRunnable
 * @see com.autoupdater.client.download.runnables.post.download.strategies.ChangelogInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.services.ChangelogInfoDownloadService
 */
public class ChangelogInfoDownloadRunnable extends
        AbstractDownloadRunnable<SortedSet<ChangelogEntry>> {
    /**
     * Creates ChangelogInfoDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     */
    public ChangelogInfoDownloadRunnable(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected IPostDownloadStrategy<SortedSet<ChangelogEntry>> getPostDownloadStrategy() {
        return new ChangelogInfoPostDownloadStrategy();
    }
}
