package com.autoupdater.client.xml.parsers;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.PackageBuilder;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.client.xml.schema.PackagesInfoSchema;

/**
 * Implementation parsing incoming XML data into SortedSet of Programs.
 */
public class PackagesInfoParser extends AbstractXMLParser<SortedSet<Program>> {
    @Override
    SortedSet<Program> parseDocument(Document document) throws ParserException {
        try {
            SortedSet<Program> programs = new TreeSet<Program>();

            List<? extends Node> programsNode = document.selectNodes("./"
                    + PackagesInfoSchema.Programs.program_);
            for (Node programNode : programsNode) {
                String name = ((Element) programNode)
                        .attributeValue(PackagesInfoSchema.Programs.Program.programName);

                SortedSet<Package> packages = new TreeSet<Package>();
                for (Node packageNode : programNode.selectNodes("./"
                        + PackagesInfoSchema.Programs.Program._package)) {
                    Element packageElement = (Element) packageNode;
                    packages.add(PackageBuilder
                            .builder()
                            .setName(
                                    packageElement
                                            .attributeValue(PackagesInfoSchema.Programs.Program.Package.name))
                            .setID(packageElement
                                    .attributeValue(PackagesInfoSchema.Programs.Program.Package.id))
                            .build());
                }
                programs.add(ProgramBuilder.builder().setName(name).setPackages(packages).build());
            }
            return programs;
        } catch (Exception e) {
            throw new ParserException("Error occured while parsing response");
        }
    }
}
