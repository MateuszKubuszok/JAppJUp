package com.autoupdater.client.download.aggregated.services;

import static net.jsdpu.logger.Logger.getLogger;

import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.notifiers.ChangelogInfoAggregatedNotifier;
import com.autoupdater.client.download.services.ChangelogInfoDownloadService;
import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.models.Package;

/**
 * Aggregator that downloads several changelogs at the same time.
 * 
 * <p>
 * Result is aggregated as Set of Packages with their changelog set.
 * </p>
 * 
 * @see com.autoupdater.client.download.services.ChangelogInfoDownloadService
 * @see com.autoupdater.client.download.aggregated.notifiers.ChangelogInfoAggregatedNotifier
 */
public class ChangelogInfoAggregatedDownloadService
        extends
        AbstractAggregatedDownloadService<ChangelogInfoDownloadService, ChangelogInfoAggregatedNotifier, SortedSet<ChangelogEntry>, SortedSet<Package>, Package> {
    private static final Logger logger = getLogger(ChangelogInfoAggregatedDownloadService.class);

    @Override
    public SortedSet<Package> getResult() throws DownloadResultException {
        logger.debug("Starts calculating results");
        SortedSet<Package> result = new TreeSet<Package>();
        for (ChangelogInfoDownloadService service : getServices()) {
            Package _package = null;
            if ((_package = getAdditionalMessage(service)) != null) {
                _package.setChangelog(service.getResult());
                result.add(_package);
            }
        }
        logger.debug("Finshed calculating results");
        return result;
    }

    @Override
    protected ChangelogInfoAggregatedNotifier createNotifier() {
        return new ChangelogInfoAggregatedNotifier();
    }
}
