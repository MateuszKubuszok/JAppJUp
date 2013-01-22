package com.autoupdater.client.download.runnables;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;
import com.autoupdater.client.download.runnables.post.download.strategies.UpdateInfoPostDownloadStrategy;
import com.autoupdater.client.models.Update;

/**
 * Implementation downloading update info.
 * 
 * <p>
 * Use UpdateInfoDownloadStrategy.
 * </p>
 * 
 * <p>
 * Used by UpdateInfoDownloadService.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.AbstractDownloadRunnable
 * @see com.autoupdater.client.download.runnables.post.download.strategies.UpdateInfoPostDownloadStrategy
 * @see com.autoupdater.client.download.services.UpdateInfoDownloadService
 */
public class UpdateInfoDownloadRunnable extends AbstractDownloadRunnable<SortedSet<Update>> {
    /**
     * Creates UpdateInfoDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     */
    public UpdateInfoDownloadRunnable(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected IPostDownloadStrategy<SortedSet<Update>> getPostDownloadStrategy() {
        return new UpdateInfoPostDownloadStrategy();
    }
}
