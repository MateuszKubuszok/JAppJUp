package com.autoupdater.client.download.runnables;

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
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestAbstractDownloadRunnable {
    private DownloadServiceMessage message;

    @Test
    public void testCreationForXmL() throws MalformedURLException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));
        DownloadResultException exception = null;

        // when
        try {
            downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exception = e;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Constructor should set initial state to HASNT_STARTED").isEqualTo(
                EDownloadStatus.HASNT_STARTED);
        assertThat(exception)
                .as("getResult() should throw exception when trying to access result prematurely")
                .isNotNull().hasMessage("Cannot obtain results - download hasn't started");
    }

    @Test
    public void testConnectionForXml() throws IOException, InterruptedException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));
        DownloadResultException exception = null;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        message = null;
        downloadRunnable.connectToServer();
        try {
            downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exception = e;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Correct connection should set state to CONNECTED").isEqualTo(
                EDownloadStatus.CONNECTED);
        assertThat(message)
                .as("Correct connection should send message with current download state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(exception)
                .as("getResult() should throw exception when trying to access result prematurely")
                .isNotNull()
                .hasMessage("Cannot obtain results - connection to the server was just made");
    }

    @Test
    public void testDownloadForXml() throws IOException, InterruptedException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));
        DownloadResultException exception = null;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        downloadRunnable.connectToServer();
        message = null;
        downloadRunnable.downloadContent();
        try {
            downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exception = e;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Complete download should set state to COMPLETED").isEqualTo(
                EDownloadStatus.COMPLETED);
        assertThat(message).as("Complete download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(exception)
                .as("getResult() should throw exception when trying to access result prematurely")
                .isNotNull()
                .hasMessage(
                        "Cannot obtain results - download is completed but hasn't been processed yet");
    }

    @Test
    public void testProcessingForXml() throws IOException, InterruptedException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));
        Document document = null;
        boolean exceptionThrown = false;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        downloadRunnable.connectToServer();
        downloadRunnable.downloadContent();
        message = null;
        try {
            downloadRunnable.processDownload();
            document = downloadRunnable.getResult();
        } catch (AutoUpdaterClientException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Processed download should set state to PROCESSED").isEqualTo(
                EDownloadStatus.PROCESSED);
        assertThat(message).as("Processed download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isFalse();
        assertThat(document).as("getResult() should return result when it's ready").isNotNull();
    }

    @Test
    public void testRunForXml() throws MalformedURLException {
        // given
        AbstractDownloadRunnable<Document> downloadRunnable = new AbstractDownloadRunnableXmlTester(
                getConnection(CorrectXMLExamples.genericXml));
        DownloadResultException exception = null;
        Document document = null;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        message = null;
        downloadRunnable.run();
        try {
            document = downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exception = e;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Processed download should set state to PROCESSED").isEqualTo(
                EDownloadStatus.PROCESSED);
        assertThat(message).as("Processed download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(exception).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isNull();
        assertThat(document).as("getResult() should return result when it's ready").isNotNull();
    }

    @Test
    public void testCreationForFile() throws MalformedURLException {
        // given
        String filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);
        DownloadResultException exception = null;

        // when
        try {
            downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exception = e;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Constructor should set initial state to HASNT_STARTED").isEqualTo(
                EDownloadStatus.HASNT_STARTED);
        assertThat(exception)
                .as("getResult() should throw exception when trying to access result prematurely")
                .isNotNull().hasMessage("Cannot obtain results - download hasn't started");
    }

    @Test
    public void testConnectionForFile() throws IOException, InterruptedException {
        // given
        String filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);
        DownloadResultException exception = null;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        message = null;
        downloadRunnable.connectToServer();
        try {
            downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exception = e;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Correct connection should set state to CONNECTED").isEqualTo(
                EDownloadStatus.CONNECTED);
        assertThat(message)
                .as("Correct connection should send message with current download state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(exception)
                .as("getResult() should throw exception when trying to access result prematurely")
                .isNotNull()
                .hasMessage("Cannot obtain results - connection to the server was just made");
    }

    @Test
    public void testDownloadForFile() throws IOException, InterruptedException {
        // given
        String filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);
        DownloadResultException exception = null;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        downloadRunnable.connectToServer();
        message = null;
        downloadRunnable.downloadContent();
        try {
            downloadRunnable.getResult().deleteOnExit();
        } catch (DownloadResultException e) {
            exception = e;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Complete download should set state to COMPLETED").isEqualTo(
                EDownloadStatus.COMPLETED);
        assertThat(message).as("Complete download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(exception)
                .as("getResult() should throw exception when trying to access result prematurely")
                .isNotNull()
                .hasMessage(
                        "Cannot obtain results - download is completed but hasn't been processed yet");
    }

    @Test
    public void testProcessingForFile() throws IOException, InterruptedException {
        // given
        File file = null;
        String filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);
        boolean exceptionThrown = false;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        downloadRunnable.connectToServer();
        downloadRunnable.downloadContent();
        message = null;
        try {
            downloadRunnable.processDownload();
            file = downloadRunnable.getResult();
            file.deleteOnExit();
        } catch (AutoUpdaterClientException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Processed download should set state to PROCESSED").isEqualTo(
                EDownloadStatus.PROCESSED);
        assertThat(message).as("Processed download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isFalse();
        assertThat(file).as("getResult() should return result when it's ready").isNotNull()
                .exists().hasContent(CorrectXMLExamples.genericXml);
    }

    @Test
    public void testRunForFile() throws MalformedURLException {
        String filePath = null;
        File result;
        // given
        result = null;
        filePath = getFilePath();
        AbstractDownloadRunnable<File> downloadRunnable = new AbstractDownloadRunnableFileTester(
                getConnection(CorrectXMLExamples.genericXml), filePath);
        DownloadResultException exception = null;

        // when
        downloadRunnable.addObserver(new MessagesObserver());
        message = null;
        downloadRunnable.run();
        try {
            result = downloadRunnable.getResult();
            result.deleteOnExit();
        } catch (DownloadResultException e) {
            exception = e;
        }

        // then
        assertThat(downloadRunnable.getState()).as(
                "Processed download should set state to PROCESSED").isEqualTo(
                EDownloadStatus.PROCESSED);
        assertThat(message).as("Processed download should send message with current state")
                .isNotNull().isInstanceOf(DownloadServiceMessage.class);
        assertThat(exception).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isEqualTo(null);
        assertThat(result).as("getResult() should return result when it's ready").isNotNull()
                .exists().hasContent(CorrectXMLExamples.genericXml);
    }

    private HttpURLConnection getConnection(String content) throws MalformedURLException {
        return new HttpURLConnectionMock(new URL("http://127.0.0.1"), content);
    }

    private String getFilePath() {
        return Paths.Library.testDir + File.separator + "testAbstractDownloadRunnableTmpFile.xml";
    }

    private class MessagesObserver implements IObserver<DownloadServiceMessage> {
        @Override
        public void update(ObservableService<DownloadServiceMessage> observable,
                DownloadServiceMessage passedMessage) {
            message = passedMessage;
        }
    }
}
