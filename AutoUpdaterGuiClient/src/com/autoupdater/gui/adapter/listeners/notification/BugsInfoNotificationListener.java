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
package com.autoupdater.gui.adapter.listeners.notification;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;

public class BugsInfoNotificationListener implements IObserver<DownloadServiceMessage> {
    private final Gui2ClientAdapter adapter;
    private final BugsInfoAggregatedDownloadService aggregatedService;

    public BugsInfoNotificationListener(Gui2ClientAdapter adapter,
            BugsInfoAggregatedDownloadService aggregatedService) {
        this.adapter = adapter;
        this.aggregatedService = aggregatedService;
    }

    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        if (observable == aggregatedService.getNotifier())
            adapter.windowOperations().reportQuiet(
                    "Fetching known bugs: " + aggregatedService.getStatus());
    }
}
