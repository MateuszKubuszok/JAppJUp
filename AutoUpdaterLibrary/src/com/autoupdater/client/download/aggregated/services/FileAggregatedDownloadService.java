package com.autoupdater.client.download.aggregated.services;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.notifiers.FileAggregatedNotifier;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Models;
import com.autoupdater.client.models.Update;

/**
 * Aggregator that downloads several files at the same time.
 * 
 * <p>
 * Result is aggregated as Set of Updates with File set to downloaded File.
 * </p>
 * 
 * @see com.autoupdater.client.download.services.FileDownloadService
 * @see com.autoupdater.client.download.aggregated.notifiers.FileAggregatedNotifier
 */
public class FileAggregatedDownloadService
        extends
        AbstractAggregatedDownloadService<FileDownloadService, FileAggregatedNotifier, File, SortedSet<Update>, Update> {
    private SortedSet<Update> allUpdates;

    /**
     * Sets all updates that are available - if it is set, then during result
     * calculation all Updates that uses the same file will have File filed
     * compliment.
     * 
     * @param allUpdates
     */
    public void setAllUpdates(SortedSet<Update> allUpdates) {
        this.allUpdates = allUpdates;
    }

    @Override
    public SortedSet<Update> getResult() throws DownloadResultException {
        SortedSet<Update> updates = new TreeSet<Update>();
        for (FileDownloadService service : getServices()) {
            Update update = null;
            if ((update = getAdditionalMessage(service)) != null) {
                update.setFile(service.getResult());
                update.setStatus(EUpdateStatus.DOWNLOADED);
                if (allUpdates != null)
                    for (Update filledUpdate : allUpdates)
                        if (Models.equal(update, filledUpdate,
                                Models.EComparisionType.LOCAL_TO_SERVER)) {
                            filledUpdate.setFile(service.getResult());
                            if (filledUpdate.getStatus().isIntendedToBeChanged())
                                filledUpdate.setStatus(EUpdateStatus.DOWNLOADED);
                        }
                updates.add(update);
            }
        }
        return updates;
    }

    @Override
    protected FileAggregatedNotifier createNotifier() {
        return new FileAggregatedNotifier();
    }
}
