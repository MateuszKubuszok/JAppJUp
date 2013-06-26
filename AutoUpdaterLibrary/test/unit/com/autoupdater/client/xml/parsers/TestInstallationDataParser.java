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
import java.util.SortedSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.autoupdater.client.Values;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;

public class TestInstallationDataParser extends AbstractTestXMLParser<List<Program>> {
    @Test
    public void testParsingCorrectDocument() throws DocumentException, ParserException {
        // given
        Document document = new SAXReader()
                .read(getInputStreamForString(CorrectXMLExamples.installationData));

        // when
        SortedSet<Program> installationData = new InstallationDataParser().parseDocument(document);

        // then
        assertThat(installationData).as("parseDocument() should parse installed programs")
                .isNotNull().hasSize(2);

        assertThat(installationData.first().getName()).as(
                "parseDocument() should parse program's name").isEqualTo(Values.Program.name);

        SortedSet<Package> packages = installationData.first().getPackages();
        assertThat(packages).as("parseDocument() should parse program's packages").hasSize(2);
        assertThat(packages.first().getName()).as("parseDocument() should parse packages's name")
                .isEqualTo(Values.Package.name);
        assertThat(packages.last().getName()).as("parseDocument() should parse packages's name")
                .isEqualTo(Values.Package2.name);

        assertThat(installationData.last().getName()).as(
                "parseDocument() should parse program's name").isEqualTo(Values.Program2.name);

        packages = installationData.last().getPackages();
        assertThat(packages).as("parseDocument() should parse program's packages").hasSize(1);
        assertThat(packages.first().getName()).as("parseDocument() should parse packages's name")
                .isEqualTo(Values.Package3.name);
    }
}
