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
package com.autoupdater.client.xml.creators;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import net.jsdpu.logger.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.xml.schema.ConfigurationSchema;
import com.google.common.io.Files;

/**
 * Creates configuration XML.
 * 
 * <p>
 * Document generated by this creator can be parsed by ConfigurationParser.
 * </p>
 * 
 * @see com.autoupdater.client.xml.creators.XMLCreationConfiguration
 * @see com.autoupdater.client.xml.parsers.ConfigurationParser
 * @see com.autoupdater.client.xml.schema.ConfigurationSchema
 */
public class ConfigurationXMLCreator {
    private static final Logger logger = getLogger(ConfigurationXMLCreator.class);

    /**
     * Creates XML document with installation data and stores it info file.
     * 
     * @param destination
     *            destination file
     * @param clientSettings
     *            client's settings
     * @param programsSettings
     *            programs's settings
     * @throws IOException
     *             thrown when error occurs during storing data to file
     */
    public void createXML(File destination, ClientSettings clientSettings,
            Set<ProgramSettings> programsSettings) throws IOException {
        logger.debug("Save confuguration data at: " + destination.getCanonicalPath());
        Document settings = DocumentHelper.createDocument();
        settings.addComment(XMLCreationConfiguration.DO_NOT_EDIT_FILE_MANUALLY_WARNING);

        Element configuration = settings.addElement(ConfigurationSchema.configuration);

        Element client = configuration.addElement(ConfigurationSchema.Configuration.client);
        client.addAttribute(ConfigurationSchema.Configuration.Client.name,
                clientSettings.getClientName());
        client.addAttribute(ConfigurationSchema.Configuration.Client.executable,
                clientSettings.getClientExecutableName());

        addLocationsSettings(client, clientSettings);

        addProxySettings(client, clientSettings);

        addProgramsSettings(configuration, programsSettings);

        Files.createParentDirs(destination);
        Files.write(settings.asXML(), destination, XMLCreationConfiguration.XML_ENCODING);
        logger.trace("Saved configuration cache data at: " + destination.getCanonicalPath());
    }

    /**
     * Creates node for client's location settings.
     * 
     * @param client
     *            root element of document
     * @param clientSettings
     *            client's settings
     */
    private void addLocationsSettings(Element client, ClientSettings clientSettings) {
        Element locations = client.addElement(ConfigurationSchema.Configuration.Client.locations);
        locations.addAttribute(
                ConfigurationSchema.Configuration.Client.Locations.pathToClientDirectory,
                clientSettings.getPathToClientDirectory());
        locations.addAttribute(ConfigurationSchema.Configuration.Client.Locations.pathToClient,
                clientSettings.getPathToClient());
        locations.addAttribute(ConfigurationSchema.Configuration.Client.Locations.installer,
                clientSettings.getPathToInstaller());
    }

    /**
     * Creates node for client's proxy settings.
     * 
     * @param configuration
     *            configuration node
     * @param clientSettings
     *            client's settings
     */
    private void addProxySettings(Element configuration, ClientSettings clientSettings) {
        if (clientSettings.getProxyAddress() != null && !clientSettings.getProxyAddress().isEmpty()) {
            Element proxy = configuration
                    .addElement(ConfigurationSchema.Configuration.Client.proxy);
            proxy.addAttribute(ConfigurationSchema.Configuration.Client.Proxy.address,
                    clientSettings.getProxyAddress());
            proxy.addAttribute(ConfigurationSchema.Configuration.Client.Proxy.port,
                    String.valueOf(clientSettings.getProxyPort()));
        }
    }

    /**
     * Creates node for each programs' settings.
     * 
     * @param configuration
     *            configuration node
     * @param programsSettings
     *            program's settings
     */
    private void addProgramsSettings(Element configuration, Set<ProgramSettings> programsSettings) {
        if (programsSettings != null && !programsSettings.isEmpty()) {
            Element programs = configuration.addElement(ConfigurationSchema.Configuration.programs);

            for (ProgramSettings programSettings : programsSettings)
                addProgramSettings(programs, programSettings);
        }
    }

    /**
     * Creates node for a program settings.
     * 
     * @param programs
     *            programs' node
     * @param programSettings
     *            program's settings
     */
    private void addProgramSettings(Element programs, ProgramSettings programSettings) {
        Element program = programs.addElement(ConfigurationSchema.Configuration.Programs.program);
        program.addAttribute(ConfigurationSchema.Configuration.Programs.Program.name,
                programSettings.getProgramName());
        program.addAttribute(ConfigurationSchema.Configuration.Programs.Program.executableName,
                programSettings.getProgramExecutableName());
        program.addAttribute(
                ConfigurationSchema.Configuration.Programs.Program.pathToProgramDirectory,
                programSettings.getPathToProgramDirectory());
        program.addAttribute(ConfigurationSchema.Configuration.Programs.Program.pathToProgram,
                programSettings.getPathToProgram());
        program.addAttribute(ConfigurationSchema.Configuration.Programs.Program.serverAddress,
                programSettings.getServerAddress());
        program.addAttribute(ConfigurationSchema.Configuration.Programs.Program.developmentVersion,
                programSettings.isDevelopmentVersion() ? "true" : "false");
    }
}
