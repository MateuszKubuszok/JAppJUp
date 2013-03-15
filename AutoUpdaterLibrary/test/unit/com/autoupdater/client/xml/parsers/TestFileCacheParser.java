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
