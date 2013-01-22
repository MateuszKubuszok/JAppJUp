package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.download.ConnectionConfiguration;

public class TestAbstractXMLParser {
    @Test
    public void testGetXMLParser() {
        // given
        AbstractXMLParser<?> testParser = new AbstractXMLParserTester();

        // then
        assertThat(testParser.getXMLParser()).as("getXmlParser() should return SAXReader instance")
                .isNotNull().isInstanceOf(SAXReader.class);
    }

    @Test
    public void testGetContent() throws Exception {
        // given
        AbstractXMLParser<?> testParser = new AbstractXMLParserTester();

        // when
        String testContent = CorrectXMLExamples.genericXml;
        Element rootElement = new SAXReader()
                .read(new ByteArrayInputStream(testContent
                        .getBytes(ConnectionConfiguration.XML_ENCODING))).getRootElement();

        // then
        assertThat(testParser.getContent(rootElement)).as(
                "getContent(Element) should properly obtain element's content").isEqualTo(
                "test content");
    }
}
