package com.autoupdater.client.xml.creators;

import static org.fest.assertions.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.autoupdater.client.Paths;

public class TestFileCacheXMLCreator {
    @Test
    public void testCreation() {
        try {
            // given
            String filePath = Paths.Library.testDir + File.separator + "testCache.dat";
            File documentXML = new File(filePath);
            documentXML.deleteOnExit();

            Map<String, String> fileCache = new HashMap<String, String>();
            fileCache.put("/test1", "1234567890");
            fileCache.put("/test2", "0987654321");

            // when
            new FileCacheXMLCreator().createXML(documentXML, fileCache);

            // then
            assertThat(documentXML).as("createXML() should save file in chosen location").exists();
        } catch (IOException e) {
            fail("createXML() shouldn't throw exception while saving file in accesible place");
        }
    }
}
