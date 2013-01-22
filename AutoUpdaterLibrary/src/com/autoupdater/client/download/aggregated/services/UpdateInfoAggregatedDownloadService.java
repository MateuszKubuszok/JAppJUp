package com.autoupdater.client.download.aggregated.services;

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.notifiers.UpdateInfoAggregatedNotifier;
import com.autoupdater.client.download.services.UpdateInfoDownloadService;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Update;

/**
 * Aggregator that downloads information about several updates at the same time.
 * 
 * <p>
 * Result is aggregated as Set of Updates.
 * </p>
 * 
 * @see com.autoupdater.client.download.services.UpdateInfoDownloadService
 * @see com.autoupdater.client.download.aggregated.notifiers.UpdateInfoAggregatedNotifier
 */
public class UpdateInfoAggregatedDownloadService
        extends
        AbstractAggregatedDownloadService<UpdateInfoDownloadService, UpdateInfoAggregatedNotifier, SortedSet<Update>, SortedSet<Update>, Package> {
    @Override
    public SortedSet<Update> getResult() throws DownloadResultException {
        SortedSet<Update> updates = new TreeSet<Update>();
        for (UpdateInfoDownloadService service : getServices()) {
            SortedSet<Update> packageUpdates = service.getResult();
            if (packageUpdates != null) {
                Package _package = getAdditionalMessage(service);
                if (_package != null && _package.getProgram() != null)
                    for (Update update : packageUpdates)
                        if (_package.getProgram().isDevelopmentVersion() == update
                                .isDevelopmentVersion()
                                && (_package.getUpdate() == null || _package.getUpdate()
                                        .getVersionNumber().compareTo(update.getVersionNumber()) < 0)) {
                            _package.setUpdate(update);
                            updates.add(update);
                        }
            }
        }
        return updates;
    }

    @Override
    protected UpdateInfoAggregatedNotifier createNotifier() {
        return new UpdateInfoAggregatedNotifier();
    }
}
