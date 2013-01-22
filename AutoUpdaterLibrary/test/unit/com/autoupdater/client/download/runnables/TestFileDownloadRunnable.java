package com.autoupdater.client.download.runnables;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;

public class TestFileDownloadRunnable extends AbstractTestDownloadRunnable {
    @Test
    public void testRun() throws MalformedURLException {
        // given
        String filePath = Paths.Library.testDir + File.separator + "testFileDownloadRunnable.xml";
        FileDownloadRunnable downloadRunnable = new FileDownloadRunnable(
                getConnection(CorrectXMLExamples.changelogInfo), filePath);
        File result = null;
        boolean exceptionThrown = false;

        // when
        downloadRunnable.run();
        try {
            result = downloadRunnable.getResult();
            result.deleteOnExit();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when trying to access parsed result")
                .isFalse();
        assertThat(result).as("getResult() should return result when it's ready").isNotNull()
                .exists();

        // clean
        result.delete();
    }
}
