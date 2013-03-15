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
