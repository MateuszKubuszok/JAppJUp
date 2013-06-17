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

import static com.autoupdater.gui.client.window.EInfoTarget.ALL;
import static com.google.common.collect.Collections2.filter;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.google.common.base.Predicate;

public class DataStorage {
    private final Gui2ClientAdapter adapter;

    private SortedSet<Program> availableOnServer;
    private SortedSet<Update> availableUpdates;

    private boolean initalized;

    public DataStorage(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
    }

    public SortedSet<Update> getAvailableUpdates() {
        if (availableUpdates == null)
            return new TreeSet<Update>();
        return availableUpdates;
    }

    public void setAvailableUpdates(SortedSet<Update> availableUpdates) {
        this.availableUpdates = availableUpdates;
    }

    public SortedSet<Program> getProgramsThatShouldBeDisplayed() {
        return adapter.client().getInstalledPrograms();
    }

    public boolean isInitiated() {
        return availableUpdates != null && initalized;
    }

    public void setInitiated(boolean initialized) {
        this.initalized = initialized;
    }

    public synchronized SortedSet<Program> getAvailableOnServer() throws IOException,
            DownloadResultException {
        if (!isInitiated()) {
            adapter.windowOperations().reportInfo("Repository checkout",
                    "Attempting to download available programs from repositories", ALL);
            availableOnServer = adapter.queryUtils().queryServerForPackages();
            adapter.windowOperations()
                    .reportInfo("Repository checkout",
                            "Fetched available programs from repositories", ALL).refreshGUI();
            setInitiated(true);
        }

        return availableOnServer;
    }

    public SortedSet<Program> getSelectedPrograms() throws IOException, DownloadResultException {
        return adapter
                .client()
                .getAvailabilityFilter()
                .findProgramsAvailableToInstallOrInstalledWithDefinedSettings(
                        adapter.dataStorage().getAvailableOnServer());
    }

    public SortedSet<Package> getSelectedPackages() throws IOException, DownloadResultException {
        return adapter
                .client()
                .getAvailabilityFilter()
                .findPackagesAvailableToHaveTheirUpdateChecked(
                        adapter.dataStorage().getAvailableOnServer());
    }

    public SortedSet<Update> getAllUpdates() {
        return adapter.dataStorage().getAvailableUpdates();
    }

    public SortedSet<Update> getUpdatesForProgram(Program program) {
        return new TreeSet<Update>(filter(adapter.dataStorage().getAvailableUpdates(),
                updateBelongsToProgram(program)));
    }

    private Predicate<Update> updateBelongsToProgram(final Program program) {
        return new Predicate<Update>() {
            @Override
            public boolean apply(Update update) {
                return update != null && program.hasMember(update.getPackage())
                        && update.isDevelopmentVersion() == program.isDevelopmentVersion();
            }
        };
    }
}
