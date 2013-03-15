/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package com.autoupdater.client.download.runnables.post.download.strategies;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.junit.Test;

import com.autoupdater.client.download.ConnectionConfiguration;
import com.autoupdater.client.download.DownloadResultException;
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
