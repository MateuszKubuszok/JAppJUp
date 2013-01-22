package com.autoupdater.client.download.services;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.ChangelogInfoDownloadRunnable;
import com.autoupdater.client.models.ChangelogEntry;

/**
 * Service, that download changelog info from given connection, and returns
 * result as a Changelog.
 */
public class ChangelogInfoDownloadService extends
        AbstractDownloadService<SortedSet<ChangelogEntry>> {
    /**
     * Creates instance of ChangelogInfoDownloadService.
     * 
     * @param connection
     *            connection used to obtain data
     */
    public ChangelogInfoDownloadService(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected AbstractDownloadRunnable<SortedSet<ChangelogEntry>> getRunnable() {
        return new ChangelogInfoDownloadRunnable(getConnection());
    }
}
