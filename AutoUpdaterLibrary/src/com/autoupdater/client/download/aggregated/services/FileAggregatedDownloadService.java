/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package com.autoupdater.client.download.aggregated.services;

import static com.autoupdater.client.models.EUpdateStatus.DOWNLOADED;
import static com.autoupdater.client.models.Models.equal;
import static com.google.common.collect.Iterables.filter;
import static net.jsdpu.logger.Logger.getLogger;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.notifiers.FileAggregatedNotifier;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.models.Models;
import com.autoupdater.client.models.Update;
import com.google.common.base.Predicate;

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
    private static final Logger logger = getLogger(FileAggregatedDownloadService.class);

    private SortedSet<Update> allUpdates;

    /**
     * Sets all updates that are available - if it is set, then during result
     * calculation all Updates that uses the same file will have File filed
     * compliment.
     * 
     * @param allUpdates
     *            all known Updates
     */
    public void setAllUpdates(SortedSet<Update> allUpdates) {
        this.allUpdates = allUpdates;
    }

    @Override
    public SortedSet<Update> getResult() throws DownloadResultException {
        logger.debug("Starts calculating results");
        DownloadResultException exception = null;
        SortedSet<Update> updates = new TreeSet<Update>();
        for (FileDownloadService service : getServices()) {
            Update update = null;
            if ((update = getAdditionalMessage(service)) != null)
                try {
                    update.setFile(service.getResult());
                    update.setStatus(DOWNLOADED);
                    if (allUpdates != null) {
                        final Update downloadedUpdate = update;
                        for (Update filledUpdate : filter(allUpdates, new Predicate<Update>() {
                            @Override
                            public boolean apply(Update filledUpdate) {
                                return equal(downloadedUpdate, filledUpdate,
                                        Models.EComparisionType.LOCAL_TO_SERVER);
                            }
                        })) {
                            filledUpdate.setFile(service.getResult());
                            if (filledUpdate.getStatus().isIntendedToBeChanged())
                                filledUpdate.setStatus(DOWNLOADED);
                        }
                    }
                    updates.add(update);
                } catch (DownloadResultException e) {
                    exception = e;
                }
        }
        if (exception != null) {
            logger.debug("Exception occured for some file download - failing aggregated file download");
            throw exception;
        }
        logger.debug("Finshed calculating results");
        return updates;
    }

    @Override
    protected FileAggregatedNotifier createNotifier() {
        return new FileAggregatedNotifier();
    }
}
