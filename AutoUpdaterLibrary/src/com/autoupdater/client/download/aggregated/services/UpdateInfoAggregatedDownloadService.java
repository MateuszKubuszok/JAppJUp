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

import static net.jsdpu.logger.Logger.getLogger;

import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

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
    private static final Logger logger = getLogger(UpdateInfoAggregatedDownloadService.class);

    @Override
    public SortedSet<Update> getResult() throws DownloadResultException {
        logger.debug("Starts calculating results");
        SortedSet<Update> updates = new TreeSet<Update>();
        for (UpdateInfoDownloadService service : getServices()) {
            SortedSet<Update> packageUpdates = service.getResult();
            if (packageUpdates != null) {
                Package _package = getAdditionalMessage(service);
                if (_package != null && _package.getProgram() != null)
                    for (Update update : packageUpdates)
                        if (_package.getProgram().isDevelopmentVersion() == update
                                .isDevelopmentVersion()
                                && (!_package.hasUpdates() || _package.getUpdates().last()
                                        .compareVersions(update) < 0)) {
                            _package.addUpdate(update);
                            updates.add(update);
                        }
            }
        }
        logger.debug("Finshed calculating results");
        return updates;
    }

    @Override
    protected UpdateInfoAggregatedNotifier createNotifier() {
        return new UpdateInfoAggregatedNotifier();
    }
}
