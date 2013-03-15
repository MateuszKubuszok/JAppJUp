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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.PackagesInfoDownloadService;
import com.autoupdater.client.models.Program;

public class TestAbstractAggregatedDownloadService {
    @Test
    public void testService() throws MalformedURLException {
        // given
        AbstractAggregatedDownloadServiceTester aggregatedService = new AbstractAggregatedDownloadServiceTester();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<programs>"
                        + ("<program name=\"Program 1\">"
                                + "<package name=\"Package 1\" id=\"1\" />"
                                + "<package name=\"Package 2\" id=\"2\" />" + "</program>")
                        + "</programs>");
        aggregatedService.addService(new PackagesInfoDownloadService(new HttpURLConnectionMock(
                new URL("http://127.0.0.1"), xml)));

        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<programs>"
                        + ("<program name=\"Program 2\">"
                                + "<package name=\"Package 3\" id=\"3\" />" + "</program>")
                        + "</programs>");
        aggregatedService.addService(new PackagesInfoDownloadService(new HttpURLConnectionMock(
                new URL("http://127.0.0.1"), xml)));

        SortedSet<Program> result = null;
        boolean exceptionThrown = false;

        // when
        aggregatedService.start();
        aggregatedService.joinThread();
        try {
            result = aggregatedService.getResult();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when result is ready, and without errors")
                .isFalse();
        assertThat(result).as("getResult() should aggregate results from all services").isNotNull()
                .hasSize(2);
    }
}
