package com.autoupdater.client.download.runnables;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.ChangelogInfoDownloadRunnable;
import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestChangelogInfoDownloadRunnable extends AbstractTestDownloadRunnable {
    @Test
    public void testRun() throws MalformedURLException {
        // given
        ChangelogInfoDownloadRunnable downloadRunnable = new ChangelogInfoDownloadRunnable(
                getConnection(CorrectXMLExamples.changelogInfo));
        SortedSet<ChangelogEntry> changelogs = null;
        boolean exceptionThrown = false;

        // when
        downloadRunnable.run();
        try {
            changelogs = downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isFalse();
        assertThat(changelogs).as("getResult() should return result when it's ready").isNotNull()
                .isNotEmpty();
    }

}
