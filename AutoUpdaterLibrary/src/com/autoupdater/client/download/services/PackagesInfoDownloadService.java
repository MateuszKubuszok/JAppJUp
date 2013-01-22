package com.autoupdater.client.download.services;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.PackagesInfoDownloadRunnable;
import com.autoupdater.client.models.Program;

/**
 * Service, that download program/packages info from given connection, and
 * returns result as set of Program models.
 */
public class PackagesInfoDownloadService extends AbstractDownloadService<SortedSet<Program>> {
    /**
     * Creates instance of PackagesInfoDownloadService.
     * 
     * @param connection
     *            connection used to obtain data
     */
    public PackagesInfoDownloadService(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected AbstractDownloadRunnable<SortedSet<Program>> getRunnable() {
        return new PackagesInfoDownloadRunnable(getConnection());
    }
}
