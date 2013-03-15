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
package com.autoupdater.client.download.runnables;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestFileDownloadRunnable extends AbstractTestDownloadRunnable {
    @Test
    public void testRun() throws MalformedURLException {
        // given
        String filePath = Paths.Library.testDir + File.separator + "testFileDownloadRunnable.xml";
        FileDownloadRunnable downloadRunnable = new FileDownloadRunnable(
                getConnection(CorrectXMLExamples.changelogInfo), filePath);
        File result = null;
        boolean exceptionThrown = false;

        // when
        downloadRunnable.run();
        try {
            result = downloadRunnable.getResult();
            result.deleteOnExit();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isFalse();
        assertThat(result).as("getResult() should return result when it's ready").isNotNull()
                .exists();

        // clean
        if (result != null)
            result.delete();
    }
}
