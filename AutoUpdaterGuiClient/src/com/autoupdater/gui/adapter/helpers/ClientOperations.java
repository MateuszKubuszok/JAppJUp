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

import static com.autoupdater.gui.client.window.EInfoTarget.*;
import static com.autoupdater.gui.client.window.EWindowStatus.*;

import java.io.IOException;
import java.util.Date;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.adapter.runnables.PeridicalUpdateCheckRunnable;

public class ClientOperations {
    private final Gui2ClientAdapter adapter;

    private Thread informationUpdatingThread;
    private Date lastUpdateCheckTime;

    public ClientOperations(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
    }

    public void startUpdateCheck() {
        informationUpdatingThread = new Thread(new PeridicalUpdateCheckRunnable(this));
        informationUpdatingThread.start();
    }

    public void stopUpdateCheck() {
        informationUpdatingThread.interrupt();
    }

    public boolean isCheckingUpdatesPerodically() {
        return !informationUpdatingThread.isInterrupted();
    }

    public synchronized void checkUpdates(final boolean onDemand) {
        if (onDemand)
            adapter.windowOperations().reportInfo("Checking updates", "Checking updates on demand",
                    TOOLTIP);

        if (adapter.clientWindow() == null
                || (adapter.clientWindow().getStatus() != UNINITIALIZED && adapter.clientWindow()
                        .getStatus() != IDLE))
            return;

        adapter.windowOperations().setState(FETCHING_UPDATE_INFO);
        adapter.windowOperations().setInstallationInactive();
        lastUpdateCheckTime = new Date();

        (new Thread() {
            @Override
            public void run() {
                try {
                    adapter.queryUtils().queryServerForInformation().join();
                    if (onDemand)
                        adapter.windowOperations().reportInfo("Checking updates",
                                "Updates fetched", TOOLTIP);
                } catch (DownloadResultException | IOException | ProgramSettingsNotFoundException
                        | InterruptedException e) {
                    adapter.windowOperations().reportError("Error occured during update checking",
                            e.getMessage(), ALL);
                    adapter.windowOperations().setState(
                            adapter.dataStorage().isInitiated() ? IDLE : UNINITIALIZED);
                }
            }
        }).start();
    }

    public Date getLastUpdateCheckTime() {
        return lastUpdateCheckTime;
    }

    public void cleanTemp() {
        adapter.client().cleanTemp();
    }
}
