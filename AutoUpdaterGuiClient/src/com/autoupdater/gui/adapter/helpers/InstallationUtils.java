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

import static com.autoupdater.client.environment.AvailabilityFilter.filterNewestForEachPackage;
import static com.autoupdater.gui.client.window.EWindowStatus.*;

import java.io.IOException;
import java.util.SortedSet;

import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.adapter.runnables.InstallUpdatesRunnable;
import com.autoupdater.gui.client.window.EInfoTarget;

public class InstallationUtils {
    private final Gui2ClientAdapter adapter;

    private FileAggregatedDownloadService currentDownloadSession = null;
    private Thread currentInstallationThread;

    public InstallationUtils(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
    }

    public Thread getCurrentInstallationThread() {
        return currentInstallationThread;
    }

    public void CurrentInstallationThreadFinished() {
        currentInstallationThread = null;
    }

    public synchronized void installAllUpdates() {
        adapter.windowOperations().setState(FETCHING_UPDATES);
        adapter.windowOperations().setInstallationIndetermined();

        try {
            FileAggregatedDownloadService aggregatedDownloadService = adapter.client()
                    .createFileAggregatedDownloadService(
                            filterNewestForEachPackage(adapter.dataStorage().getAllUpdates()));
            AggregatedInstallationService aggregatedInstallationService = adapter.client()
                    .createInstallationAggregatedService(
                            filterNewestForEachPackage(adapter.dataStorage().getAllUpdates()));

            new Thread(new InstallUpdatesRunnable(adapter, aggregatedDownloadService,
                    aggregatedInstallationService)).start();

            currentDownloadSession = aggregatedDownloadService;
        } catch (ProgramSettingsNotFoundException | IOException e) {
            adapter.windowOperations().reportError("Error occured during installation",
                    e.getMessage(), EInfoTarget.TOOLTIP);
            adapter.windowOperations().setState(IDLE);
        }
    }

    public synchronized void installUpdatesForProgram(Program program) {
        adapter.windowOperations().setState(FETCHING_UPDATES);
        adapter.windowOperations().setInstallationIndetermined();

        try {
            SortedSet<Update> updatesForProgram = adapter.dataStorage().getUpdatesForProgram(
                    program);

            FileAggregatedDownloadService aggregatedDownloadService = adapter.client()
                    .createFileAggregatedDownloadService(
                            filterNewestForEachPackage(updatesForProgram));
            AggregatedInstallationService aggregatedInstallationService = adapter.client()
                    .createInstallationAggregatedService(
                            filterNewestForEachPackage(updatesForProgram));

            currentInstallationThread = new Thread(new InstallUpdatesRunnable(adapter,
                    aggregatedDownloadService, aggregatedInstallationService));
            currentInstallationThread.start();

            currentDownloadSession = aggregatedDownloadService;
        } catch (ProgramSettingsNotFoundException | IOException ex) {
            adapter.windowOperations().reportError("Error occured during installation",
                    ex.getMessage(), EInfoTarget.TOOLTIP);
            adapter.windowOperations().setState(IDLE);
        }
    }

    public void markAllUpdatesAsIntendedToInstall() {
        if (adapter.dataStorage().isInitiated())
            for (Update update : adapter.dataStorage().getAvailableUpdates())
                if (update.getPackage().getVersionNumber().compareTo(update.getVersionNumber()) < 0)
                    update.setStatus(EUpdateStatus.SELECTED);
    }

    public void cancelDownloads() {
        currentDownloadSession.cancel();
    }
}
