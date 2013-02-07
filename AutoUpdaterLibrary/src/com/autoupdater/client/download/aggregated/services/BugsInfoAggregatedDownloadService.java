package com.autoupdater.client.download.aggregated.services;

import static net.jsdpu.logger.Logger.getLogger;

import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

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
    private static final Logger logger = getLogger(BugsInfoAggregatedDownloadService.class);

    @Override
    public SortedSet<Program> getResult() throws DownloadResultException {
        logger.debug("Starts calculating results");
        SortedSet<Program> result = new TreeSet<Program>();
        for (BugsInfoDownloadService service : getServices()) {
            Program program = null;
            if ((program = getAdditionalMessage(service)) != null) {
                program.setBugs(service.getResult());
                result.add(program);
            }
        }
        logger.debug("Finshed calculating results");
        return result;
    }

    @Override
    protected BugsInfoAggregatedNotifier createNotifier() {
        return new BugsInfoAggregatedNotifier();
    }
}
