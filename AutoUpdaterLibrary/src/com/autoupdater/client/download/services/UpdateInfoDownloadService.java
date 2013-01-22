package com.autoupdater.client.download.services;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.UpdateInfoDownloadRunnable;
import com.autoupdater.client.models.Update;

/**
 * Service, that download update info from given connection, and returns result
 * as Update model.
 */
public class UpdateInfoDownloadService extends AbstractDownloadService<SortedSet<Update>> {
    /**
     * Creates instance of UpdateInfoDownloadService.
     * 
     * @param connection
     *            connection used to obtain data
     */
    public UpdateInfoDownloadService(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected AbstractDownloadRunnable<SortedSet<Update>> getRunnable() {
        return new UpdateInfoDownloadRunnable(getConnection());
    }
}
