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

import com.autoupdater.client.installation.EInstallationStatus;
import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.client.window.EInfoTarget;

public class InstallationNotificationListener implements IObserver<InstallationServiceMessage> {
    private final Gui2ClientAdapter adapter;
    private final AggregatedInstallationService aggregatedService;

    public InstallationNotificationListener(Gui2ClientAdapter adapter,
            AggregatedInstallationService aggregatedService) {
        this.adapter = adapter;
        this.aggregatedService = aggregatedService;
    }

    @Override
    public void update(ObservableService<InstallationServiceMessage> observable,
            InstallationServiceMessage message) {
        if (observable == aggregatedService.getNotifier()) {
            if (message.isInterruptedByError())
                adapter.windowOperations().reportError("Installation failed", message.getMessage(),
                        EInfoTarget.ALL);
            else
                adapter.windowOperations().reportInfo("Installation",
                        "Installation: " + aggregatedService.getState(), EInfoTarget.ALL);
        }

        if (aggregatedService.getState() == EInstallationStatus.INSTALLING_UPDATES) {
            int numberOfUpdatesBeingInstalled = aggregatedService.getUpdates().size();
            int numberOfUpdatesMarkedAsDone = 0;

            for (Update update : aggregatedService.getUpdates())
                if (update.getStatus().isUpdateAttemptFinished())
                    numberOfUpdatesMarkedAsDone++;

            adapter.windowOperations().setInstallationProgress(numberOfUpdatesBeingInstalled,
                    numberOfUpdatesMarkedAsDone);
        }
    }
}
