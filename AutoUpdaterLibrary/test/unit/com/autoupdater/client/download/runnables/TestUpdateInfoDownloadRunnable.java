package com.autoupdater.client.download.runnables;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestUpdateInfoDownloadRunnable extends AbstractTestDownloadRunnable {
    @Test
    public void testRun() throws MalformedURLException {
        // given
        UpdateInfoDownloadRunnable downloadRunnable = new UpdateInfoDownloadRunnable(
                getConnection(CorrectXMLExamples.updateInfo));
        Update update = null;
        boolean exceptionThrown = false;

        // when
        downloadRunnable.run();
        try {
            update = downloadRunnable.getResult().first();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isFalse();
        assertThat(update).as("getResult() should return result when it's ready").isNotNull();
    }
}
