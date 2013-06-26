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

import static com.autoupdater.client.download.EDownloadStatus.*;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.AbstractDownloadRunnableFileTester;
import com.autoupdater.client.download.runnables.AbstractDownloadRunnableXmlTester;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestAbstractDownloadService {
    @Test
    public void testDownloadForXml() throws MalformedURLException, InterruptedException,
            DownloadResultException {
        // given
        AbstractDownloadService<Document> service = new AbstractDownloadServiceXmlTester(
                getConnection());
        Document result = null;

        // when
        service.start();
        service.joinThread();
        result = service.getResult();

        // then
        assertThat(service.getStatus()).as(
                "When no error occured thread should finish with PROCESSED status").isEqualTo(
                PROCESSED);
        assertThat(result).as("Service should return correct result").isNotNull();
    }

    @Test(expected = DownloadResultException.class)
    public void testCancelForXml() throws MalformedURLException, InterruptedException,
            DownloadResultException {
        // given
        AbstractDownloadService<Document> service = new AbstractDownloadServiceXmlTester(
                getCancellableConnection());

        // when
        service.start();
        service.cancel();
        service.joinThread();

        // then
        assertThat(service.getStatus()).as(
                "Cancelled download thread should finish with CANCELLED status").isEqualTo(
                CANCELLED);
        service.getResult(); // exception
    }

    @Test
    public void testDownloadForFile() throws MalformedURLException, InterruptedException,
            DownloadResultException {
        // given
        String filePath = Paths.Library.testDir + File.separator + "testAbstractService.xml";
        AbstractDownloadService<File> service = new AbstractDownloadServiceFileTester(
                getConnection(), filePath);
        File result = null;

        // when
        service.start();
        service.joinThread();
        result = service.getResult();
        result.deleteOnExit();

        // then
        assertThat(service.getStatus()).as(
                "When no error occured thread should finish with PROCESSED status").isEqualTo(
                PROCESSED);
        assertThat(result).as("Service should return correct result").isNotNull();
    }

    @Test(expected = DownloadResultException.class)
    public void testCancellationForFile() throws MalformedURLException, InterruptedException,
            DownloadResultException {
        // given
        AbstractDownloadService<File> service = new AbstractDownloadServiceFileTester(
                getCancellableConnection(), "");

        // when
        service.start();
        service.cancel();
        service.joinThread();

        // then
        assertThat(service.getStatus()).as(
                "Cancelled download thread should finish with CANCELLED status").isEqualTo(
                CANCELLED);
        service.getResult(); // exception
    }

    private HttpURLConnection getConnection() throws MalformedURLException {
        return new HttpURLConnectionMock(new URL("http://127.0.0.1/"),
                CorrectXMLExamples.genericXml);
    }

    private HttpURLConnection getCancellableConnection() throws MalformedURLException {
        return new HttpURLConnectionCancelMock();
    }

    private class AbstractDownloadServiceXmlTester extends AbstractDownloadService<Document> {
        public AbstractDownloadServiceXmlTester(HttpURLConnection connection) {
            super(connection);
        }

        @Override
        protected AbstractDownloadRunnable<Document> getRunnable() {
            return new AbstractDownloadRunnableXmlTester(getConnection());
        }
    }

    private class AbstractDownloadServiceFileTester extends AbstractDownloadService<File> {
        public AbstractDownloadServiceFileTester(HttpURLConnection connection,
                String fileDestinationPath) {
            super(connection, fileDestinationPath);
        }

        @Override
        protected AbstractDownloadRunnable<File> getRunnable() {
            return new AbstractDownloadRunnableFileTester(getConnection(), getFileDestinationPath());
        }
    }

    public class HttpURLConnectionCancelMock extends HttpURLConnectionMock {
        public HttpURLConnectionCancelMock() throws MalformedURLException {
            super(new URL("http://127.0.0.1/"), CorrectXMLExamples.genericXml);
        }

        @Override
        public InputStream getInputStream() {
            return new InputStreamCancelMock();
        }
    }

    public class InputStreamCancelMock extends InputStream {
        @Override
        public int read() throws IOException {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return 0;
        }
    }
}
