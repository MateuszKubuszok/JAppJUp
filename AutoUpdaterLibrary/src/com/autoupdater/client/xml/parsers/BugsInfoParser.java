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

import com.autoupdater.client.models.BugEntry;
import com.autoupdater.client.models.BugEntryBuilder;
import com.autoupdater.client.xml.schema.BugsInfoSchema;

/**
 * Implementation parsing incoming XML data into Bugs set.
 */
public class BugsInfoParser extends AbstractXMLParser<SortedSet<BugEntry>> {
    private static final Logger logger = getLogger(BugsInfoParser.class);

    @Override
    SortedSet<BugEntry> parseDocument(Document document) throws ParserException {
        logger.trace("Parsing bugs info data");
        try {
            SortedSet<BugEntry> bugs = new TreeSet<BugEntry>();

            List<? extends Node> bugsNodes = document.selectNodes("./" + BugsInfoSchema.Bugs.bug_);
            for (Node bugNode : bugsNodes) {
                String description = getContent((Element) bugNode);
                bugs.add(BugEntryBuilder.builder().setDescription(description).build());
            }

            return bugs;
        } catch (Exception e) {
            logger.error("Cannot parse bugs info data's document: " + e.getMessage()
                    + " (exception thrown)", e);
            throw new ParserException("Error occured while parsing response").addSuppresed(e,
                    ParserException.class);
        }
    }
}
