package com.autoupdater.client.download.runnables.post.download.strategies;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.junit.Test;

import com.autoupdater.client.download.ConnectionConfiguration;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLPostDownloadStrategy;
import com.autoupdater.client.xml.parsers.CorrectXMLExamples;
import com.autoupdater.client.xml.parsers.ParserException;

public class TestAbstractXMLPostDownloadStrategy {
    @Test
    public void testWriteAndGetXml() throws UnsupportedEncodingException {
        // given
        String content = CorrectXMLExamples.genericXml;
        AbstractXMLPostDownloadStrategy<Document> strategy = new AbstractXMLDownloadStrategyTester();

        // when
        strategy.write((content).getBytes(ConnectionConfiguration.XML_ENCODING), content.length());

        // then
        assertThat(strategy.getXml())
                .isEqualTo(CorrectXMLExamples.genericXml)
                .as("write(byte[]) should write into storage, and getXml() should return downloaded XML document's content");
    }

    @Test
    public void testProcessDownload() {
        // given
        String content = CorrectXMLExamples.genericXml;
        AbstractXMLPostDownloadStrategy<Document> strategy = new AbstractXMLDownloadStrategyTester();
        Document document = null;
        boolean exceptionThrown = false;

        // when
        strategy.write((content).getBytes(ConnectionConfiguration.XML_ENCODING), content.length());
        try {
            document = strategy.processDownload();
        } catch (ParserException | DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "processDownload() should not throw exception for correct XML").isFalse();
        assertThat(document).isNotNull().as(
                "processDownload() should return effect of AbstractXmlParser processing");
    }
}
