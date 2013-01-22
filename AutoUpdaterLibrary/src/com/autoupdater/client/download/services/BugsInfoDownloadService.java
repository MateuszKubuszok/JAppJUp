package com.autoupdater.client.download.services;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.BugsInfoDownloadRunnable;
import com.autoupdater.client.models.BugEntry;

/**
 * Service, that download bugs info from given connection, and returns result as
 * a Bugs set.
 */
public class BugsInfoDownloadService extends AbstractDownloadService<SortedSet<BugEntry>> {
    /**
     * Creates instance of BugsInfoDownloadService.
     * 
     * @param connection
     *            connection used to obtain data
     */
    public BugsInfoDownloadService(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected AbstractDownloadRunnable<SortedSet<BugEntry>> getRunnable() {
        return new BugsInfoDownloadRunnable(getConnection());
    }
}
