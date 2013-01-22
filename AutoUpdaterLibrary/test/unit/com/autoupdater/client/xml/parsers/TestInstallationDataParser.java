package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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
        List<Program> installationData = new ArrayList<Program>(
                new InstallationDataParser().parseDocument(document));

        // then
        assertThat(installationData).as("parseDocument() should parse installed programs")
                .isNotNull().hasSize(2);

        assertThat(installationData.get(0).getName()).as(
                "parseDocument() should parse program's name").isEqualTo(Values.Program.name);

        List<Package> packages = new ArrayList<Package>(installationData.get(0).getPackages());
        assertThat(packages).as("parseDocument() should parse program's packages").hasSize(2);
        assertThat(packages.get(0).getName()).as("parseDocument() should parse packages's name")
                .isEqualTo(Values.Package.name);
        assertThat(packages.get(1).getName()).as("parseDocument() should parse packages's name")
                .isEqualTo(Values.Package2.name);

        assertThat(installationData.get(1).getName()).as(
                "parseDocument() should parse program's name").isEqualTo(Values.Program2.name);

        packages = new ArrayList<Package>(installationData.get(1).getPackages());
        assertThat(packages).as("parseDocument() should parse program's packages").hasSize(1);
        assertThat(packages.get(0).getName()).as("parseDocument() should parse packages's name")
                .isEqualTo(Values.Package3.name);
    }
}
