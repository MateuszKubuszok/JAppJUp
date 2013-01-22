package com.autoupdater.client.download.runnables;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.PackagesInfoDownloadRunnable;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestPackagesInfoDownloadRunnable extends AbstractTestDownloadRunnable {
    @Test
    public void testRun() throws MalformedURLException {
        // given
        PackagesInfoDownloadRunnable downloadRunnable = new PackagesInfoDownloadRunnable(
                getConnection(CorrectXMLExamples.packagesInfo));
        SortedSet<Program> programs = null;
        boolean exceptionThrown = false;

        // when
        downloadRunnable.run();
        try {
            programs = downloadRunnable.getResult();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isFalse();
        assertThat(programs).as("getResult() should return result when it's ready").isNotNull()
                .isNotEmpty();
    }
}
