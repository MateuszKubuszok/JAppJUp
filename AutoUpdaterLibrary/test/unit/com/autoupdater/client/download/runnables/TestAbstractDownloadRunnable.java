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

import static com.autoupdater.client.download.EDownloadStatus.*;
import static java.io.File.separator;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.junit.Test;

import com.autoupdater.client.AutoUpdaterClientException;
import com.autoupdater.client.Paths;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestAbstractDownloadRunnable {
    private DownloadServiceMessage message;

    @Test(expected = DownloadResultException.class)
    public void testCreationForXmL() throws MalformedURLException, DownloadResultException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));

        // when

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Constructor should set initial state to HASNT_STARTED").isEqualTo(HASNT_STARTED);
        downloadRunnable.getResult(); // exception
    }

    @Test(expected = DownloadResultException.class)
    public void testConnectionForXml() throws IOException, InterruptedException,
            DownloadResultException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        message = null;
        downloadRunnable.connectToServer();

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Correct connection should set state to CONNECTED").isEqualTo(CONNECTED);
        assertThat(message)
                .as("Correct connection should send message with current download state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        downloadRunnable.getResult(); // exception
    }

    @Test(expected = DownloadResultException.class)
    public void testDownloadForXml() throws IOException, InterruptedException,
            DownloadResultException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        downloadRunnable.connectToServer();
        message = null;
        downloadRunnable.downloadContent();

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Complete download should set state to COMPLETED").isEqualTo(COMPLETED);
        assertThat(message).as("Complete download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        downloadRunnable.getResult(); // exception
    }

    @Test
    public void testProcessingForXml() throws IOException, InterruptedException,
            AutoUpdaterClientException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));
        Document document = null;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        downloadRunnable.connectToServer();
        downloadRunnable.downloadContent();
        message = null;
        downloadRunnable.processDownload();
        document = downloadRunnable.getResult();

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Processed download should set state to PROCESSED").isEqualTo(PROCESSED);
        assertThat(message).as("Processed download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(document).as("getResult() should return result when it's ready").isNotNull();
    }

    @Test
    public void testRunForXml() throws MalformedURLException, DownloadResultException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));
        Document document = null;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        message = null;
        downloadRunnable.run();
        document = downloadRunnable.getResult();

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Processed download should set state to PROCESSED").isEqualTo(PROCESSED);
        assertThat(message).as("Processed download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(document).as("getResult() should return result when it's ready").isNotNull();
    }

    @Test(expected = DownloadResultException.class)
    public void testCreationForFile() throws MalformedURLException, DownloadResultException {
        // given
        String filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);

        // when

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Constructor should set initial state to HASNT_STARTED").isEqualTo(HASNT_STARTED);
        downloadRunnable.getResult(); // exception
    }

    @Test(expected = DownloadResultException.class)
    public void testConnectionForFile() throws IOException, InterruptedException,
            DownloadResultException {
        // given
        String filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        message = null;
        downloadRunnable.connectToServer();

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Correct connection should set state to CONNECTED").isEqualTo(CONNECTED);
        assertThat(message)
                .as("Correct connection should send message with current download state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        downloadRunnable.getResult(); // exception
    }

    @Test(expected = DownloadResultException.class)
    public void testDownloadForFile() throws IOException, InterruptedException,
            DownloadResultException {
        // given
        String filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        downloadRunnable.connectToServer();
        message = null;
        downloadRunnable.downloadContent();

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Complete download should set state to COMPLETED").isEqualTo(COMPLETED);
        assertThat(message).as("Complete download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        downloadRunnable.getResult(); // exception
    }

    @Test
    public void testProcessingForFile() throws IOException, InterruptedException,
            AutoUpdaterClientException {
        // given
        File file = null;
        String filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        downloadRunnable.connectToServer();
        downloadRunnable.downloadContent();
        message = null;
        downloadRunnable.processDownload();
        file = downloadRunnable.getResult();
        file.deleteOnExit();

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Processed download should set state to PROCESSED").isEqualTo(PROCESSED);
        assertThat(message).as("Processed download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(file).as("getResult() should return result when it's ready").isNotNull()
                .exists().hasContent(CorrectXMLExamples.genericXml);
    }

    @Test
    public void testRunForFile() throws MalformedURLException, DownloadResultException {
        String filePath = null;
        File result;
        // given
        result = null;
        filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        message = null;
        downloadRunnable.run();
        result = downloadRunnable.getResult();
        result.deleteOnExit();

        // then
        assertThat(downloadRunnable.getStatus()).as(
                "Processed download should set state to PROCESSED").isEqualTo(PROCESSED);
        assertThat(message).as("Processed download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(result).as("getResult() should return result when it's ready").isNotNull()
                .exists().hasContent(CorrectXMLExamples.genericXml);
    }

    private HttpURLConnection getConnection(String content) throws MalformedURLException {
        return new HttpURLConnectionMock(new URL("http://127.0.0.1"), content);
    }

    private String getFilePath() {
        return Paths.Library.testDir + separator + "testAbstractDownloadRunnableTmpFile.xml";
    }

    private class MessagesObserver implements IObserver<DownloadServiceMessage> {
        @Override
        public void update(ObservableService<DownloadServiceMessage> observable,
                DownloadServiceMessage passedMessage) {
            message = passedMessage;
        }
    }
}
