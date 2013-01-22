package com.autoupdater.client.download.runnables;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.models.BugEntry;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestBugsInfoDownloadRunnable extends AbstractTestDownloadRunnable {
    @Test
    public void testRun() throws MalformedURLException {
        // given
        BugsInfoDownloadRunnable downloadRunnable = new BugsInfoDownloadRunnable(
                getConnection(CorrectXMLExamples.bugsInfo));
        SortedSet<BugEntry> bugs = null;
        boolean exceptionThrown = false;

        // when
        downloadRunnable.run();
        try {
            bugs = downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isFalse();
        assertThat(bugs).as("getResult() should return result when it's ready").isNotNull()
                .isNotEmpty();
    }

}
