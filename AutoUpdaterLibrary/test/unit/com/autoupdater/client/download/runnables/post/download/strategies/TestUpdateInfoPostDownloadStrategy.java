package com.autoupdater.client.download.runnables.post.download.strategies;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.autoupdater.client.download.ConnectionConfiguration;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.post.download.strategies.UpdateInfoPostDownloadStrategy;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;
import com.autoupdater.client.xml.parsers.ParserException;

public class TestUpdateInfoPostDownloadStrategy {
    @Test
    public void testStrategy() {
        // given
        UpdateInfoPostDownloadStrategy strategy = new UpdateInfoPostDownloadStrategy();
        String content = CorrectXMLExamples.updateInfo;
        Update result = null;
        boolean exceptionThrown = false;

        // when
        strategy.write(content.getBytes(ConnectionConfiguration.XML_ENCODING), content.length());
        try {
            result = strategy.processDownload().first();
        } catch (ParserException | DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "processDownload() should not throw exception for correct XML").isFalse();
        assertThat(result).isNotNull().as(
                "processDownload() should properly parse packages' information");
    }
}
