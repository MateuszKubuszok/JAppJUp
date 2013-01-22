package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.Values;
import com.autoupdater.client.models.ChangelogEntry;

public class TestChangelogInfoParser extends AbstractTestXMLParser<List<ChangelogEntry>> {
    @Test
    public void testParsingCorrectDocument() throws DocumentException, ParserException {
        // given
        Document document = new SAXReader()
                .read(getInputStreamForString(CorrectXMLExamples.changelogInfo));

        // when
        List<ChangelogEntry> changelogs = new ArrayList<ChangelogEntry>(
                new ChangelogInfoParser().parseDocument(document));

        // then
        assertThat(changelogs).as(
                "parseDocument() should parse all changelogs without removing/adding empty")
                .hasSize(2);
        assertThat(changelogs.get(0).getChanges()).as(
                "parseDocument() should properly parse changelog").isEqualTo(
                Values.Changelog.content);
        assertThat(changelogs.get(0).getVersionNumber()).as(
                "parseDocument() should properly parse version number").isEqualTo(
                Values.Changelog.version);
        assertThat(changelogs.get(1).getChanges()).as(
                "parseDocument() should properly parse changelog").isEqualTo(
                Values.Changelog2.content);
        assertThat(changelogs.get(1).getVersionNumber()).as(
                "parseDocument() should properly parse version number").isEqualTo(
                Values.Changelog2.version);
    }
}
