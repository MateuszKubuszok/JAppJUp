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
package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.SortedSet;

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
        SortedSet<ProgramSettings> programsSettings = environmentData.getProgramsSettings();

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

        assertThat(programsSettings.first().getProgramName())
                .as("parseDocument() should parse program's name").isNotNull()
                .isEqualTo(Values.ProgramSettings.programName);
        assertThat(programsSettings.first().getPathToProgram())
                .as("parseDocument() should parse program's path").isNotNull()
                .isEqualTo(Paths.Installations.Program.programPath);
        assertThat(programsSettings.first().getPathToProgramDirectory()).isNotNull().isEqualTo(
                Paths.Installations.Program.programDir);
        assertThat(programsSettings.first().getServerAddress())
                .as("parseDocument() should parse server's address").isNotNull()
                .isEqualTo(Values.ProgramSettings.serverAddress);

        assertThat(programsSettings.last().getProgramName())
                .as("parseDocument() should parse program's name").isNotNull()
                .isEqualTo(Values.ProgramSettings2.programName);
        assertThat(programsSettings.last().getPathToProgram())
                .as("parseDocument() should parse program's path").isNotNull()
                .isEqualTo(Paths.Installations.Program2.programPath);
        assertThat(programsSettings.last().getPathToProgramDirectory()).isNotNull().isEqualTo(
                Paths.Installations.Program2.programDir);
        assertThat(programsSettings.last().getServerAddress())
                .as("parseDocument() should parse server's address").isNotNull()
                .isEqualTo(Values.ProgramSettings2.serverAddress);
    }
}
