package com.autoupdater.client.xml.creators;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

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
