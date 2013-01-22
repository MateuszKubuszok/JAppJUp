package com.autoupdater.client.download.services;

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
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.AbstractDownloadRunnableFileTester;
import com.autoupdater.client.download.runnables.AbstractDownloadRunnableXmlTester;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestAbstractDownloadService {
    @Test
    public void testDownloadForXml() throws MalformedURLException, InterruptedException {
        // given
        AbstractDownloadService<Document> service = new AbstractDownloadServiceXmlTester(
                getConnection());
        Document result = null;
        boolean exceptionThrown = false;

        // when
        service.start();
        service.joinThread();
        try {
            result = service.getResult();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(service.getState()).as(
                "When no error occured thread should finish with PROCESSED status").isEqualTo(
                EDownloadStatus.PROCESSED);
        assertThat(exceptionThrown).as("Service should return result when processed corretly")
                .isFalse();
        assertThat(result).as("Service should return correct result").isNotNull();
    }

    @Test
    public void testCancelForXml() throws MalformedURLException {
        // given
        AbstractDownloadService<Document> service = new AbstractDownloadServiceXmlTester(
                getCancellableConnection());
        boolean exceptionThrown = false;

        // when
        service.start();
        service.cancel();
        try {
            service.joinThread();
        } catch (InterruptedException e) {
        }
        try {
            service.getResult();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(service.getState()).as(
                "Cancelled download thread should finish with CANCELLED status").isEqualTo(
                EDownloadStatus.CANCELLED);
        assertThat(exceptionThrown)
                .as("Service should throw exception when trying to obtain result from cancelled process")
                .isTrue();
    }

    @Test
    public void testDownloadForFile() throws MalformedURLException, InterruptedException {
        // given
        String filePath = Paths.Library.testDir + File.separator + "testAbstractService.xml";
        AbstractDownloadService<File> service = new AbstractDownloadServiceFileTester(
                getConnection(), filePath);
        File result = null;
        boolean exceptionThrown = false;

        // when
        service.start();
        service.joinThread();
        try {
            result = service.getResult();
            result.deleteOnExit();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(service.getState()).as(
                "When no error occured thread should finish with PROCESSED status").isEqualTo(
                EDownloadStatus.PROCESSED);
        assertThat(exceptionThrown).as("Service should return result when processed corretly")
                .isFalse();
        assertThat(result).as("Service should return correct result").isNotNull();
    }

    @Test
    public void testCancellationForFile() throws MalformedURLException {
        // given
        AbstractDownloadService<File> service = new AbstractDownloadServiceFileTester(
                getCancellableConnection(), "");
        boolean exceptionThrown = false;

        // when
        service.start();
        service.cancel();
        try {
            service.joinThread();
        } catch (InterruptedException e) {
        }
        try {
            service.getResult();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(service.getState()).as(
                "Cancelled download thread should finish with CANCELLED status").isEqualTo(
                EDownloadStatus.CANCELLED);
        assertThat(exceptionThrown)
                .as("Service should throw exception when trying to obtain result from cancelled process")
                .isTrue();
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
