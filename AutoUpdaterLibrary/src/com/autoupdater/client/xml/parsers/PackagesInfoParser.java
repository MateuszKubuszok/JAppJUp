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

import static net.jsdpu.logger.Logger.getLogger;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

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
    private static final Logger logger = getLogger(PackagesInfoParser.class);

    @Override
    SortedSet<Program> parseDocument(Document document) throws ParserException {
        logger.trace("Parsing packages info data");
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
            logger.error("Cannot parse packages info data's document: " + e.getMessage()
                    + " (exception thrown)", e);
            throw new ParserException("Error occured while parsing response").addSuppresed(e,
                    ParserException.class);
        }
    }
}
