package com.autoupdater.client.download.aggregated.services;

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.notifiers.BugsInfoAggregatedNotifier;
import com.autoupdater.client.download.services.BugsInfoDownloadService;
import com.autoupdater.client.models.BugEntry;
import com.autoupdater.client.models.Program;

/**
 * Aggregator that downloads several bugs sets at the same time.
 * 
 * <p>
 * Result is aggregated as Set of Programs with their bugs sets set.
 * </p>
 * 
 * @see com.autoupdater.client.download.services.BugsInfoDownloadService
 * @see com.autoupdater.client.download.aggregated.notifiers.BugsInfoAggregatedNotifier
 */
public class BugsInfoAggregatedDownloadService
        extends
        AbstractAggregatedDownloadService<BugsInfoDownloadService, BugsInfoAggregatedNotifier, SortedSet<BugEntry>, SortedSet<Program>, Program> {
    @Override
    public SortedSet<Program> getResult() throws DownloadResultException {
        SortedSet<Program> result = new TreeSet<Program>();
        for (BugsInfoDownloadService service : getServices()) {
            Program program = null;
            if ((program = getAdditionalMessage(service)) != null) {
                program.setBugs(service.getResult());
                result.add(program);
            }
        }
        return result;
    }

    @Override
    protected BugsInfoAggregatedNotifier createNotifier() {
        return new BugsInfoAggregatedNotifier();
    }
}
