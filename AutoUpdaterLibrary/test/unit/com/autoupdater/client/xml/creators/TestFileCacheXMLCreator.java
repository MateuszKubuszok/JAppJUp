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

import static java.io.File.separator;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.autoupdater.client.Paths;

public class TestFileCacheXMLCreator {
    @Test
    public void testCreation() throws IOException {
        // given
        String filePath = Paths.Library.testDir + separator + "testCache.dat";
        File documentXML = new File(filePath);
        documentXML.deleteOnExit();

        Map<String, String> fileCache = new HashMap<String, String>();
        fileCache.put("/test1", "1234567890");
        fileCache.put("/test2", "0987654321");

        // when
        new FileCacheXMLCreator().createXML(documentXML, fileCache);

        // then
        assertThat(documentXML).as("createXML() should save file in chosen location").exists();
    }
}
