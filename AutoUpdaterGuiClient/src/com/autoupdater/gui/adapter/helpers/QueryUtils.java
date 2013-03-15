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
package com.autoupdater.gui.adapter.helpers;

import java.io.IOException;
import java.util.SortedSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.adapter.listeners.notification.PackagesInfoNotificationListener;
import com.autoupdater.gui.adapter.runnables.CheckUpdatesRunnable;

public class QueryUtils {
    private final Gui2ClientAdapter adapter;

    public QueryUtils(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
    }

    public SortedSet<Program> queryServerForPackages() throws IOException, DownloadResultException {
        PackagesInfoAggregatedDownloadService aggregatedService = adapter.client()
                .createPackagesInfoAggregatedDownloadService();
        aggregatedService.getNotifier().addObserver(
                new PackagesInfoNotificationListener(adapter, aggregatedService));

        aggregatedService.start();
        aggregatedService.joinThread();

        return aggregatedService.getResult();
    }

    public Thread queryServerForInformation() throws DownloadResultException, IOException,
            ProgramSettingsNotFoundException {
        SortedSet<Program> selectedPrograms = adapter.dataStorage().getSelectedPrograms();
        SortedSet<Package> selectedPackages = adapter.dataStorage().getSelectedPackages();

        UpdateInfoAggregatedDownloadService aggregatedUpdateInfoService = adapter.client()
                .createUpdateInfoAggregatedDownloadService(selectedPackages);

        ChangelogInfoAggregatedDownloadService aggregatedChangelogInfoService = adapter.client()
                .createChangelogInfoAggregatedDownloadService(selectedPackages);

        BugsInfoAggregatedDownloadService aggregatedBugsInfoService = adapter.client()
                .createBugsInfoAggregatedDownloadService(selectedPrograms);

        Thread query = new Thread(new CheckUpdatesRunnable(adapter, aggregatedUpdateInfoService,
                aggregatedChangelogInfoService, aggregatedBugsInfoService));
        query.start();
        return query;
    }
}
