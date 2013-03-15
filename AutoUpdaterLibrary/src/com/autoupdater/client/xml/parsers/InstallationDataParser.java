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
import com.autoupdater.client.xml.schema.InstallationDataSchema;

/**
 * Implementation parsing XML data from file into SortedSet of installed
 * Programs.
 */
public class InstallationDataParser extends AbstractXMLParser<SortedSet<Program>> {
    private static final Logger logger = getLogger(InstallationDataParser.class);

    @Override
    SortedSet<Program> parseDocument(Document document) throws ParserException {
        logger.trace("Parsing installation data file's document");
        try {
            SortedSet<Program> programs = new TreeSet<Program>();

            for (Node programNode : document.selectNodes("./"
                    + InstallationDataSchema.Installed.program_)) {
                Element program = (Element) programNode;
                String programName = program
                        .attributeValue(InstallationDataSchema.Installed.Program.name);
                String pathToDirectory = program
                        .attributeValue(InstallationDataSchema.Installed.Program.pathToDirectory);
                String serverAddress = program
                        .attributeValue(InstallationDataSchema.Installed.Program.serverAddress);

                if (programName != null && !programName.isEmpty() && pathToDirectory != null
                        && !pathToDirectory.isEmpty() && serverAddress != null
                        && !serverAddress.isEmpty()) {
                    SortedSet<Package> packages = new TreeSet<Package>();
                    for (Node packageNode : program.selectNodes("./"
                            + InstallationDataSchema.Installed.Program._package)) {
                        Element _package = (Element) packageNode;
                        String packageName = _package
                                .attributeValue(InstallationDataSchema.Installed.Program.Package.name);
                        String packageVersion = _package
                                .attributeValue(InstallationDataSchema.Installed.Program.Package.version);
                        packages.add(PackageBuilder.builder().setName(packageName)
                                .setVersionNumber(packageVersion).build());
                    }

                    programs.add(ProgramBuilder.builder().setName(programName)
                            .setPathToProgramDirectory(pathToDirectory)
                            .setServerAddress(serverAddress).setPackages(packages).build());
                }
            }

            return programs;
        } catch (Exception e) {
            logger.error("Cannot parse installation data file's document: " + e.getMessage()
                    + " (exception thrown)", e);
            throw new ParserException("Error occured while parsing installation data file")
                    .addSuppresed(e, ParserException.class);
        }
    }
}
