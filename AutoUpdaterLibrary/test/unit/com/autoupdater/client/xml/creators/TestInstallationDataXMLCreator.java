package com.autoupdater.client.xml.creators;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.models.Program;

public class TestInstallationDataXMLCreator {
    @Test
    public void testCreation() {
        // given
        String filePath = Paths.Library.testDir + File.separator + "testInstallationData.xml";
        File documentXML = new File(filePath);
        documentXML.deleteOnExit();

        SortedSet<Program> installationData = new TreeSet<Program>();

        boolean exceptionThrown = false;

        // when
        try {
            new InstallationDataXMLCreator().createXML(documentXML, installationData);
        } catch (IOException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "createXML() shouldn't throw exception while saving file in accesible place")
                .isFalse();
        assertThat(documentXML).as("createXML() should save file in chosen location").exists();
    }
}
