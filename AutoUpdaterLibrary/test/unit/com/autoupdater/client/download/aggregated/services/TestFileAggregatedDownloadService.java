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
package com.autoupdater.client.download.aggregated.services;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.UpdateBuilder;

public class TestFileAggregatedDownloadService {
    @Test
    public void testService() throws MalformedURLException, DownloadResultException {
        // given
        FileAggregatedDownloadService aggregatedService = new FileAggregatedDownloadService();

        String content = "some content";
        String filePath1 = Paths.Library.testDir + File.separator
                + "testFileDownloadAggregatedService1.xml";
        aggregatedService.addService(new FileDownloadService(new HttpURLConnectionMock(new URL(
                "http://127.0.0.1"), content), filePath1), UpdateBuilder.builder().setID("1")
                .setPackageName("Name").setVersionNumber("1.0.0.0").setDevelopmentVersion(true)
                .setOriginalName("name.zip").setRelativePath("/").setUpdateStrategy("unzip")
                .build());

        content = "some other";
        String filePath2 = Paths.Library.testDir + File.separator
                + "testFileDownloadAggregatedService2.xml";
        aggregatedService
                .addService(
                        new FileDownloadService(new HttpURLConnectionMock(new URL(
                                "http://127.0.0.1"), content), filePath2),
                        UpdateBuilder.builder().setID("2").setPackageName("Name")
                                .setVersionNumber("2.0.0.0").setDevelopmentVersion(false)
                                .setOriginalName("name.zip").setRelativePath("/")
                                .setUpdateStrategy("copy").build());

        SortedSet<Update> result = null;

        // when
        aggregatedService.start();
        aggregatedService.joinThread();
        result = aggregatedService.getResult();
        for (Update update : result)
            update.getFile().deleteOnExit();

        // then
        assertThat(result).as("getResult() should aggregate results from all services").isNotNull()
                .hasSize(2);
    }
}
