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
package com.autoupdater.client.download.runnables.post.download.strategies;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.ConnectionConfiguration;
import com.google.common.io.Files;

public class TestFilePostDownloadStrategy {
    @Test
    public void testStrategy() throws IOException {
        // given
        File temp = new File(Paths.Library.testDir + File.separator + "fileDownloadStrategy.txt");
        Files.createParentDirs(temp);
        temp.createNewFile();
        temp.deleteOnExit();

        File result = null;
        temp.deleteOnExit();
        FilePostDownloadStrategy strategy = new FilePostDownloadStrategy(temp);
        String content = "some test content", contentWritten = "";

        // when
        strategy.write(content.getBytes(ConnectionConfiguration.XML_ENCODING), content.length());
        result = strategy.processDownload();
        result.deleteOnExit();
        contentWritten = Files.toString(temp, ConnectionConfiguration.XML_ENCODING);

        // then
        assertThat(result).as("processDownload() should properly return File").exists();
        assertThat(contentWritten).as("strategy should properly write to file").isEqualTo(content);
    }
}
