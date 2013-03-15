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
package com.autoupdater.gui.adapter;

import static com.autoupdater.gui.client.window.EInfoTarget.TOOLTIP;
import static com.autoupdater.gui.config.GuiConfiguration.WINDOW_TITLE;

import java.io.IOException;

import com.autoupdater.client.Client;
import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.EnvironmentDataManager;
import com.autoupdater.gui.adapter.helpers.ClientOperations;
import com.autoupdater.gui.adapter.helpers.DataStorage;
import com.autoupdater.gui.adapter.helpers.InstallationUtils;
import com.autoupdater.gui.adapter.helpers.QueryUtils;
import com.autoupdater.gui.adapter.helpers.WindowOperations;
import com.autoupdater.gui.client.window.GuiClientWindow;

public class Gui2ClientAdapter {
    private final EnvironmentData environmentData;
    private final Client client;

    private final DataStorage dataStorage;
    private final ClientOperations clientOperations;
    private final WindowOperations windowOperations;
    private final QueryUtils queryUtils;
    private final InstallationUtils installationUtils;

    // GUI instances
    private GuiClientWindow clientWindow;

    public Gui2ClientAdapter(EnvironmentDataManager environmentDataManager)
            throws ClientEnvironmentException, IOException {
        client = new Client(environmentData = environmentDataManager.getEnvironmentData());

        dataStorage = new DataStorage(this);
        clientOperations = new ClientOperations(this);
        windowOperations = new WindowOperations(this);
        queryUtils = new QueryUtils(this);
        installationUtils = new InstallationUtils(this);
    }

    public Client client() {
        return client;
    }

    public EnvironmentData environmentData() {
        return environmentData;
    }

    public DataStorage dataStorage() {
        return dataStorage;
    }

    public ClientOperations clientOperations() {
        return clientOperations;
    }

    public WindowOperations windowOperations() {
        return windowOperations;
    }

    public QueryUtils queryUtils() {
        return queryUtils;
    }

    public InstallationUtils installationUtils() {
        return installationUtils;
    }

    public GuiClientWindow clientWindow() {
        return clientWindow;
    }

    public Gui2ClientAdapter clientWindow(final GuiClientWindow clientWindow) {
        this.clientWindow = clientWindow;
        windowOperations
                .configureClientWindow(clientWindow)
                .reportInfo(
                        WINDOW_TITLE + " initalized",
                        "Updater is initialized and ready to work. Click right mouse button on tray icon to check/install updates or run installed applications.",
                        TOOLTIP);
        clientOperations.startUpdateCheck();
        return this;
    }
}
