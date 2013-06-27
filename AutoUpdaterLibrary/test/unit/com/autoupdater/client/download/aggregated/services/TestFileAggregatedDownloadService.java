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

import static java.io.File.separator;
import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.models.MockModels;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Update;

public class TestFileAggregatedDownloadService {
    @Test
    public void testService() throws MalformedURLException, DownloadResultException {
        // given
        SortedSet<Package> packages = MockModels.program().getPackages();
        Update update1 = packages.first().getUpdates().first();
        Update update2 = packages.last().getUpdates().first();
        FileAggregatedDownloadService aggregatedService = new FileAggregatedDownloadService();

        String content = "some content";
        String filePath1 = Paths.Library.testDir + separator
                + "testFileDownloadAggregatedService1.xml";
        aggregatedService.addService(new FileDownloadService(new HttpURLConnectionMock(new URL(
                "http://127.0.0.1"), content), filePath1), update1);

        content = "some other";
        String filePath2 = Paths.Library.testDir + separator
                + "testFileDownloadAggregatedService2.xml";
        aggregatedService.addService(new FileDownloadService(new HttpURLConnectionMock(new URL(
                "http://127.0.0.1"), content), filePath2), update2);
        aggregatedService.setAllUpdates(new TreeSet<Update>(asList(update1, update2)));

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
