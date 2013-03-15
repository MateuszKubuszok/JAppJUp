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
package com.autoupdater.client.download.connections;

import java.util.HashMap;
import java.util.Map;

import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;

/**
 * Factory used for obtaining PerProgramConnectionFactories.
 * 
 * <p>
 * At least one instance of ConnectionFactory is required in client to create
 * connections used by DownloadServices.
 * </p>
 * 
 * <p>
 * ConnectionFactory is bound to ClientConfiguration, and as such should be used
 * for creating connection only for original Client.
 * </p>
 * 
 * @see com.autoupdater.client.download.connections.PerProgramConnectionFactory
 * @see com.autoupdater.client.download.services.AbstractDownloadService
 */
public class ConnectionFactory {
    protected final ClientSettings clientConfiguration;

    private final Map<ProgramSettings, PerProgramConnectionFactory> perProgramConnectionFactories;

    /**
     * Creates ConnectionFactory instance.
     * 
     * @param clientSettings
     *            client's settings required by ConnectionFactory
     */
    public ConnectionFactory(ClientSettings clientSettings) {
        this.clientConfiguration = clientSettings;
        this.perProgramConnectionFactories = new HashMap<ProgramSettings, PerProgramConnectionFactory>();
    }

    /**
     * Returns PerProgramConnectionFactory for specified ProgramSettings.
     * 
     * <p>
     * For each ConnectionFactory, for given ProgramSettings always the same
     * PerProgramConnecitonFactory instance is returned.
     * </p>
     * 
     * @param programSettings
     *            program's settings
     * @return PerProgramConnectionFactory instance
     */
    public PerProgramConnectionFactory getPerProgramConnectionFactory(
            ProgramSettings programSettings) {
        if (perProgramConnectionFactories.containsKey(programSettings))
            return perProgramConnectionFactories.get(programSettings);

        PerProgramConnectionFactory newPerProgramConnectionFactory = new PerProgramConnectionFactory(
                clientConfiguration, programSettings);
        perProgramConnectionFactories.put(programSettings, newPerProgramConnectionFactory);
        return newPerProgramConnectionFactory;
    }
}
