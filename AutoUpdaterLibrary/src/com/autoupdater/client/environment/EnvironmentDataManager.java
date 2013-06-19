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
package com.autoupdater.client.environment;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

import com.autoupdater.client.environment.settings.ClientSettingsBuilder;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.xml.creators.ConfigurationXMLCreator;
import com.autoupdater.client.xml.creators.InstallationDataXMLCreator;
import com.autoupdater.client.xml.parsers.ConfigurationParser;
import com.autoupdater.client.xml.parsers.InstallationDataParser;
import com.autoupdater.client.xml.parsers.ParserException;

/**
 * Manages EnvironmentData - in particular, saves and stores/reads settings and
 * installation data into/from files. Also allows to create default
 * EnvironmentData.
 * 
 * <p>
 * Uses EnvironmentContext as:
 * <ul>
 * <li>a source of default settings,</li>
 * <li>a way to find location of client's setting/installation data.</li>
 * </ul>
 * </p>
 * 
 * @see com.autoupdater.client.environment.EnvironmentData
 */
public class EnvironmentDataManager {
    private static final Logger logger = getLogger(EnvironmentDataManager.class);

    private final EnvironmentContext environmentContext;

    /**
     * Creates instance of EnvironmentDataManager.
     */
    public EnvironmentDataManager() {
        environmentContext = new EnvironmentContext();
    }

    /**
     * Creates instance of EnvironmentDataManager with custom
     * EnvironmentContext.
     * 
     * @param environmentContext
     *            context describing default options
     */
    public EnvironmentDataManager(EnvironmentContext environmentContext) {
        this.environmentContext = environmentContext;
    }

    /**
     * Returns currently used EnvironmentContext.
     * 
     * @return current EnvironmentContext
     */
    public EnvironmentContext getEnviromentContext() {
        return environmentContext;
    }

    /**
     * Returns EnvironmentData, that can be used to initiate Client instance.
     * 
     * <p>
     * Reads settings and installation data from files described in
     * EnvironmentContext.
     * </p>
     * 
     * @return new EnvironmentData
     * @throws ClientEnvironmentException
     *             thrown if settings/installation data cannot be parsed
     * @throws IOException
     *             thrown if file with settings/installation data cannot be read
     */
    public EnvironmentData getEnvironmentData() throws ClientEnvironmentException, IOException {
        try {
            logger.info("Reads settings from files");
            File settingsXMLFile = new File(environmentContext.getSettingsXMLPath());
            if (!settingsXMLFile.exists() || !settingsXMLFile.canRead())
                throw new IOException("File does not exists or cannot be read");

            EnvironmentData environmentData = new ConfigurationParser().parseXML(settingsXMLFile);

            File installationDataXMLFile = new File(environmentContext.getInstallationDataXMLPath());
            if (installationDataXMLFile.exists() && installationDataXMLFile.canRead())
                environmentData.setInstallationsData(new InstallationDataParser()
                        .parseXML(installationDataXMLFile));

            return environmentData.setEnvironmentDataManager(this);
        } catch (ParserException e) {
            logger.warning("Settings read failed", e);
            throw new ClientEnvironmentException(e.getMessage()).addSuppresed(e,
                    ClientEnvironmentException.class);
        }
    }

    /**
     * Saves EnvironmentData into files described in EnvironmentContext.
     * 
     * @param environmentData
     *            EnvironmentData intended to save
     * @throws IOException
     *             thrown if settings cannot be written to a file
     */
    public void setEnvironmentData(EnvironmentData environmentData) throws IOException {
        logger.info("Saves settings to files");

        new ConfigurationXMLCreator().createXML(new File(environmentContext.getSettingsXMLPath()),
                environmentData.getClientSettings(), environmentData.getProgramsSettings());

        SortedSet<Program> currentAndLegacyInstallations = new TreeSet<Program>(
                environmentData.getInstallationsData());
        currentAndLegacyInstallations.addAll(environmentData.getLegacyInstallationData());
        new InstallationDataXMLCreator().createXML(
                new File(environmentContext.getInstallationDataXMLPath()),
                currentAndLegacyInstallations);
    }

    /**
     * Creates EnvironmentData with settings defined as default in
     * EnvironmetnContext (without proxy).
     * 
     * @see #createDefaultSettingsWithProxy()
     * 
     * @return new EnvironmentData
     */
    public EnvironmentData createDefaultSettings() {
        logger.info("Creates default settings");

        return new EnvironmentData(ClientSettingsBuilder.builder()
                .setClientName(environmentContext.getDefaultClientName())
                .setClientExecutableName(environmentContext.getDefaultClientExecutable())
                .setPathToClient(environmentContext.getDefaultPathToClient())
                .setPathToClientDirectory(environmentContext.getDefaultPathToClientDirectory())
                .setPathToInstaller(environmentContext.getDefaultPathToInstaller()).build(),
                new TreeSet<ProgramSettings>()).setEnvironmentDataManager(this);
    }

    /**
     * Creates EnvironmentData with settings defined as default in
     * EnvironmetnContext (with proxy).
     * 
     * @see #createDefaultSettings()
     * 
     * @return new EnvironmentData
     */
    public EnvironmentData createDefaultSettingsWithProxy() {
        logger.info("Creates default settings with proxy");

        return new EnvironmentData(ClientSettingsBuilder.builder()
                .setClientName(environmentContext.getDefaultClientName())
                .setClientExecutableName(environmentContext.getDefaultClientExecutable())
                .setPathToClient(environmentContext.getDefaultPathToClient())
                .setPathToClientDirectory(environmentContext.getDefaultPathToClientDirectory())
                .setPathToInstaller(environmentContext.getDefaultPathToInstaller())
                .setProxyAddress(environmentContext.getDefaultProxyAddress())
                .setProxyPort(environmentContext.getDefaultProxyPort()).build(),
                new TreeSet<ProgramSettings>()).setEnvironmentDataManager(this);
    }
}
