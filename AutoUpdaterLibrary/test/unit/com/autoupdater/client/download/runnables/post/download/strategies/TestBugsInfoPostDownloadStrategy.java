package com.autoupdater.client.download.runnables.post.download.strategies;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.ConnectionConfiguration;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.post.download.strategies.BugsInfoPostDownloadStrategy;
import com.autoupdater.client.models.BugEntry;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;
import com.autoupdater.client.xml.parsers.ParserException;

public class TestBugsInfoPostDownloadStrategy {
    @Test
    public void testStrategy() {
        // given
        BugsInfoPostDownloadStrategy strategy = new BugsInfoPostDownloadStrategy();
        String content = CorrectXMLExamples.bugsInfo;
        SortedSet<BugEntry> result = null;
        boolean exceptionThrown = false;

        // when
        strategy.write(content.getBytes(ConnectionConfiguration.XML_ENCODING), content.length());
        try {
            result = strategy.processDownload();
        } catch (ParserException | DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "processDownload() should not throw exception for correct XML").isFalse();
        assertThat(result).isNotNull().isNotEmpty()
                .as("processDownload() should properly parse changelog information");
    }
}
