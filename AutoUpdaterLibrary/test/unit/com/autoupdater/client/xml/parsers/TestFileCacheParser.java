package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.models.ChangelogEntry;

public class TestFileCacheParser extends AbstractTestXMLParser<List<ChangelogEntry>> {
    @Test
    public void testParsingCorrectDocument() throws DocumentException, ParserException {
        // given
        Document document = new SAXReader()
                .read(getInputStreamForString(CorrectXMLExamples.fileCache));

        // when
        Map<String, String> fileCache = new FileCacheParser().parseDocument(document);

        // then
        assertThat(fileCache).as(
                "parseDocument() should parse all file caches without removing/adding empty")
                .hasSize(2);
        assertThat(fileCache.get("/file1")).as("parseDocument() should properly parse fileCache")
                .isNotNull().isEqualTo("1234567890");
        assertThat(fileCache.get("/file2")).as("parseDocument() should properly parse fileCache")
                .isNotNull().isEqualTo("0987654321");
    }
}
