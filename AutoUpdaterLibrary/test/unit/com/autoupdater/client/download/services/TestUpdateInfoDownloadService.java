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
package com.autoupdater.client.download.services;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestUpdateInfoDownloadService {
    @Test
    public void testService() throws MalformedURLException, InterruptedException {
        // given
        UpdateInfoDownloadService service = new UpdateInfoDownloadService(getConnection());
        Update result = null;
        boolean exceptionThrown = false;

        // when
        service.start();
        service.joinThread();
        try {
            result = service.getResult().first();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(service.getStatus()).as(
                "When no error occured thread should finish with PROCESSED status").isEqualTo(
                EDownloadStatus.PROCESSED);
        assertThat(exceptionThrown).as("Service should return result when processed corretly")
                .isFalse();
        assertThat(result).as("Service should return correct result").isNotNull();
    }

    private HttpURLConnection getConnection() throws MalformedURLException {
        return new HttpURLConnectionMock(new URL("http://127.0.0.1/"),
                CorrectXMLExamples.updateInfo);
    }
}
