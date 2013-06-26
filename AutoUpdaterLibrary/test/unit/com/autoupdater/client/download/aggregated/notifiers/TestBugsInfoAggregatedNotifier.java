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
package com.autoupdater.client.download.aggregated.notifiers;

import static com.autoupdater.client.download.EDownloadStatus.PROCESSED;
import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.BugsInfoDownloadService;
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;

public class TestBugsInfoAggregatedNotifier {
    private String recievedMessage;

    @Test
    public void testNotifier() throws MalformedURLException {
        // given
        BugsInfoAggregatedDownloadService aggregatedService = new BugsInfoAggregatedDownloadService();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<bugs>" + ("<bug>Some bug description</bug>")
                        + ("<bug>Some other description</bug>") + "</bugs>");
        aggregatedService.addService(new BugsInfoDownloadService(new HttpURLConnectionMock(new URL(
                "http://127.0.0.1"), xml)), ProgramBuilder.builder().setName("Program")
                .setPathToProgramDirectory("/").setServerAddress("127.0.0.1").build());

        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<bugs>" + ("<bug>Some bug description 2</bug>")
                        + ("<bug>Some other description 2</bug>") + "</bugs>");
        aggregatedService.addService(new BugsInfoDownloadService(new HttpURLConnectionMock(new URL(
                "http://127.0.0.1"), xml)), ProgramBuilder.builder().setName("Program 2")
                .setPathToProgramDirectory("/").setServerAddress("127.0.0.1").build());

        BugsInfoAggregatedNotifier listener = aggregatedService.getNotifier();
        listener.addObserver(new ListenerObserver());

        // when
        aggregatedService.start();
        aggregatedService.joinThread();

        // then
        assertThat(recievedMessage)
                .as("BugsInfoAggregatedNotifier should inform about finishing of all downloads")
                .isNotNull().isEqualTo(PROCESSED.getMessage());
    }

    private class ListenerObserver implements IObserver<DownloadServiceMessage> {
        @Override
        public void update(ObservableService<DownloadServiceMessage> observable,
                DownloadServiceMessage message) {
            recievedMessage = message.getMessage();
        }
    }
}
