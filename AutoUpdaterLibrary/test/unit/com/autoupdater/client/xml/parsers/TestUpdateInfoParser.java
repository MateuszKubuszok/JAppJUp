package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.Values;
import com.autoupdater.client.models.Update;

public class TestUpdateInfoParser extends AbstractTestXMLParser<Update> {
    @Test
    public void testParsingCorrectDocument() throws Exception {
        // given
        Document document = new SAXReader()
                .read(getInputStreamForString(CorrectXMLExamples.updateInfo));

        // when
        Update update = new UpdateInfoParser().parseDocument(document).first();

        // then
        assertThat(update.getPackageName()).as(
                "parseDocument() should properly parse package's name").isEqualTo(
                Values.Update.packageName);
        assertThat(update.getPackageID()).as("parseDocument() should properly parse package's ID")
                .isEqualTo("2");
        assertThat(update.getVersionNumber()).as(
                "parseDocument() should properly parse version number").isEqualTo(
                Values.Update.version);
        assertThat(update.isDevelopmentVersion()).as(
                "parseDocument() should properly parse version type").isTrue();
        assertThat(update.getID()).as("parseDocument() should properly parse update ID").isEqualTo(
                "1");
        assertThat(update.getChanges()).as("parseDocument() should properly parse changelog")
                .isEqualTo(Values.Update.changelog);
    }
}
