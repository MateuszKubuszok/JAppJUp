package com.autoupdater.client.download.services;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.ChangelogInfoDownloadService;
import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestBugsInfoDownloadService {
    @Test
    public void testService() throws MalformedURLException, InterruptedException {
        // given
        ChangelogInfoDownloadService service = new ChangelogInfoDownloadService(getConnection());
        SortedSet<ChangelogEntry> result = null;
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
        assertThat(result).as("Service should return correct result").isNotNull().isNotEmpty();
    }

    private HttpURLConnection getConnection() throws MalformedURLException {
        return new HttpURLConnectionMock(new URL("http://127.0.0.1/"),
                CorrectXMLExamples.changelogInfo);
    }
}
