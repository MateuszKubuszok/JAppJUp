package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;

public class TestConfigurationParser extends AbstractTestXMLParser<EnvironmentData> {
    @Test
    public void testParsingCorrectDocument() throws DocumentException, ParserException {
        // given
        Document document = new SAXReader()
                .read(getInputStreamForString(CorrectXMLExamples.clientConfiguration));

        // when
        EnvironmentData environmentData = new ConfigurationParser().parseDocument(document);
        ClientSettings clientSettings = environmentData.getClientSettings();
        List<ProgramSettings> programsSettings = new ArrayList<ProgramSettings>(
                environmentData.getProgramsSettings());

        // then
        assertThat(clientSettings).as("parseDocument() should parse client's settings").isNotNull();
        assertThat(clientSettings.getPathToClientDirectory()).as(
                "parseDocument() should parse updater's directory").isEqualTo(
                Paths.Library.clientDir);
        assertThat(clientSettings.getPathToClient()).as(
                "parseDocument() should parse updater executable's path").isEqualTo(
                Paths.Library.clientPath);
        assertThat(clientSettings.getPathToInstaller()).as(
                "parseDocument() should parse installer's path").isEqualTo(
                Paths.Library.installerPath);
        assertThat(clientSettings.getProxyAddress()).as(
                "parseDocument() should parse proxy's address").isEqualTo(
                Values.ClientSettings.proxyAddress);
        assertThat(clientSettings.getProxyPort()).as("parseDocument() should parse proxy's port")
                .isEqualTo(Values.ClientSettings.proxyPort);

        assertThat(programsSettings)
                .as("parseDocument() should parse all programs configurations without removing/adding empty")
                .isNotNull().hasSize(2);

        assertThat(programsSettings.get(0).getProgramName())
                .as("parseDocument() should parse program's name").isNotNull()
                .isEqualTo(Values.ProgramSettings.programName);
        assertThat(programsSettings.get(0).getPathToProgram())
                .as("parseDocument() should parse program's path").isNotNull()
                .isEqualTo(Paths.Installations.Program.programPath);
        assertThat(programsSettings.get(0).getPathToProgramDirectory()).isNotNull().isEqualTo(
                Paths.Installations.Program.programDir);
        assertThat(programsSettings.get(0).getServerAddress())
                .as("parseDocument() should parse server's address").isNotNull()
                .isEqualTo(Values.ProgramSettings.serverAddress);

        assertThat(programsSettings.get(1).getProgramName())
                .as("parseDocument() should parse program's name").isNotNull()
                .isEqualTo(Values.ProgramSettings2.programName);
        assertThat(programsSettings.get(1).getPathToProgram())
                .as("parseDocument() should parse program's path").isNotNull()
                .isEqualTo(Paths.Installations.Program2.programPath);
        assertThat(programsSettings.get(1).getPathToProgramDirectory()).isNotNull().isEqualTo(
                Paths.Installations.Program2.programDir);
        assertThat(programsSettings.get(1).getServerAddress())
                .as("parseDocument() should parse server's address").isNotNull()
                .isEqualTo(Values.ProgramSettings2.serverAddress);
    }
}
