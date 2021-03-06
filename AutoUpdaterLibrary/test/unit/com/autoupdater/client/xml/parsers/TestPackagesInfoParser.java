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
import java.util.SortedSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;

public class TestPackagesInfoParser extends AbstractTestXMLParser<List<Program>> {
    @Test
    public void testParsingProgramsInCorrectDocument() throws DocumentException, ParserException {
        // given
        Document document = new SAXReader()
                .read(getInputStreamForString(CorrectXMLExamples.packagesInfo));

        // when
        List<Program> programs = new ArrayList<Program>(
                new PackagesInfoParser().parseDocument(document));

        // then
        assertThat(programs).as(
                "parseDocument() should parse all programs without removing/adding empty").hasSize(
                2);
        assertThat(programs.get(0).getName()).as(
                "parseDocument() should properly parse program's name").isEqualTo("Program 1");
        assertThat(programs.get(1).getName()).as(
                "parseDocument() should properly parse program's name").isEqualTo("Program 2");
    }

    @Test
    public void testParsingPackagesInCorrectDocument() throws DocumentException, ParserException {
        // given
        Document document = new SAXReader()
                .read(getInputStreamForString(CorrectXMLExamples.packagesInfo));

        // when
        SortedSet<Program> programs = new PackagesInfoParser().parseDocument(document);
        SortedSet<Package> packages1 = ("Program 1".equals(programs.first().getName()) ? programs
                .first() : programs.last()).getPackages();
        SortedSet<Package> packages2 = ("Program 2".equals(programs.first().getName()) ? programs
                .first() : programs.last()).getPackages();

        // then
        assertThat(packages1).as(
                "parseDocument() should parse all packages without removing/adding empty").hasSize(
                2);
        assertThat(packages1.first().getName()).as(
                "parseDocument() should properly parse package's name").isEqualTo("Package 1");
        assertThat(packages1.first().getID()).as(
                "parseDocument() should properly parse package's ID").isEqualTo("1");
        assertThat(packages1.last().getName()).as(
                "parseDocument() should properly parse package's name").isEqualTo("Package 2");
        assertThat(packages2).as(
                "parseDocument() should parse all packages without removing/adding empty").hasSize(
                1);
        assertThat(packages2.first().getName()).as(
                "parseDocument() should properly parse package's name").isEqualTo("Package 3");
        assertThat(packages2.first().getID()).as(
                "parseDocument() should properly parse package's ID").isEqualTo("3");
    }
}
