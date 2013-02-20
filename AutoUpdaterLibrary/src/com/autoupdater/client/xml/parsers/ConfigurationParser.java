package com.autoupdater.client.xml.parsers;

import static net.jsdpu.logger.Logger.getLogger;

import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ClientSettingsBuilder;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.environment.settings.ProgramSettingsBuilder;
import com.autoupdater.client.xml.schema.ConfigurationSchema;

/**
 * Implementation parsing XML data from file into EnvironmentData.
 */
public class ConfigurationParser extends AbstractXMLParser<EnvironmentData> {
    private static final Logger logger = getLogger(ConfigurationParser.class);

    @Override
    EnvironmentData parseDocument(Document document) throws ParserException {
        logger.trace("Parsing configuration file's document");
        try {
            Element client = (Element) document.selectSingleNode("./"
                    + ConfigurationSchema.Configuration.client_);

            Element locations = (Element) document.selectSingleNode("./"
                    + ConfigurationSchema.Configuration.Client.locations_);
            Element proxy = (Element) document.selectSingleNode("./"
                    + ConfigurationSchema.Configuration.Client.proxy_);

            ClientSettingsBuilder clientSettingsBuilder = ClientSettingsBuilder
                    .builder()
                    .setClientName(
                            client.attributeValue(ConfigurationSchema.Configuration.Client.name))
                    .setClientExecutableName(
                            client.attributeValue(ConfigurationSchema.Configuration.Client.executable))
                    .setPathToClient(
                            locations
                                    .attributeValue(ConfigurationSchema.Configuration.Client.Locations.pathToClient))
                    .setPathToClientDirectory(
                            locations
                                    .attributeValue(ConfigurationSchema.Configuration.Client.Locations.pathToClientDirectory))
                    .setPathToInstaller(
                            locations
                                    .attributeValue(ConfigurationSchema.Configuration.Client.Locations.installer));

            if (proxy != null)
                clientSettingsBuilder
                        .setProxyAddress(
                                proxy.attributeValue(ConfigurationSchema.Configuration.Client.Proxy.address))
                        .setProxyPort(
                                proxy.attributeValue(ConfigurationSchema.Configuration.Client.Proxy.port));

            ClientSettings clientSettings = clientSettingsBuilder.build();

            SortedSet<ProgramSettings> programsSettings = new TreeSet<ProgramSettings>();
            for (Node programNode : document
                    .selectNodes(ConfigurationSchema.Configuration.Programs.program_)) {
                Element program = (Element) programNode;
                programsSettings
                        .add(ProgramSettingsBuilder
                                .builder()
                                .setProgramName(
                                        program.attributeValue(ConfigurationSchema.Configuration.Programs.Program.name))
                                .setProgramExecutableName(
                                        program.attributeValue(ConfigurationSchema.Configuration.Programs.Program.executableName))
                                .setPathToProgramDirectory(
                                        program.attributeValue(ConfigurationSchema.Configuration.Programs.Program.pathToProgramDirectory))
                                .setPathToProgram(
                                        program.attributeValue(ConfigurationSchema.Configuration.Programs.Program.pathToProgram))
                                .setServerAddress(
                                        program.attributeValue(ConfigurationSchema.Configuration.Programs.Program.serverAddress))
                                .setDevelopmentVersion(
                                        program.attributeValue(ConfigurationSchema.Configuration.Programs.Program.developmentVersion))
                                .build());
            }

            return new EnvironmentData(clientSettings, programsSettings);
        } catch (Exception e) {
            logger.error("Cannot parse configuration file's document: " + e.getMessage()
                    + " (exception thrown)", e);
            throw new ParserException("Error occured while parsing configuration file")
                    .addSuppresed(e, ParserException.class);
        }
    }
}
