package com.autoupdater.client.xml.parsers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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
        List<Program> programs = new ArrayList<Program>(
                new PackagesInfoParser().parseDocument(document));
        List<Package> packages1 = new ArrayList<Package>(("Program 1".equals(programs.get(0)
                .getName()) ? programs.get(0) : programs.get(1)).getPackages());
        List<Package> packages2 = new ArrayList<Package>(("Program 2".equals(programs.get(0)
                .getName()) ? programs.get(0) : programs.get(1)).getPackages());

        // then
        assertThat(packages1).as(
                "parseDocument() should parse all packages without removing/adding empty").hasSize(
                2);
        assertThat(packages1.get(0).getName()).as(
                "parseDocument() should properly parse package's name").isEqualTo("Package 1");
        assertThat(packages1.get(0).getID()).as(
                "parseDocument() should properly parse package's ID").isEqualTo("1");
        assertThat(packages1.get(1).getName()).as(
                "parseDocument() should properly parse package's name").isEqualTo("Package 2");
        assertThat(packages2).as(
                "parseDocument() should parse all packages without removing/adding empty").hasSize(
                1);
        assertThat(packages2.get(0).getName()).as(
                "parseDocument() should properly parse package's name").isEqualTo("Package 3");
        assertThat(packages2.get(0).getID()).as(
                "parseDocument() should properly parse package's ID").isEqualTo("3");
    }
}
