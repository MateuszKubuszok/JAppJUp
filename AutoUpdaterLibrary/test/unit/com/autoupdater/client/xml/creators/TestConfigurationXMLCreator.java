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

import static org.fest.assertions.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ClientSettingsBuilder;
import com.autoupdater.client.environment.settings.ProgramSettings;

public class TestConfigurationXMLCreator {
    @Test
    public void testCreation() {
        try {
            // given
            String filePath = Paths.Library.testDir + File.separator + "testConfiguration.xml";
            File documentXML = new File(filePath);
            documentXML.deleteOnExit();

            ClientSettings clientSettings = ClientSettingsBuilder.builder().build();
            SortedSet<ProgramSettings> programsSettings = new TreeSet<ProgramSettings>();

            // when
            new ConfigurationXMLCreator().createXML(documentXML, clientSettings, programsSettings);

            // then
            assertThat(documentXML).as("createXML() should save file in chosen location").exists();
        } catch (IOException e) {
            fail("createXML() shouldn't throw exception while saving file in accesible place");
        }
    }
}
