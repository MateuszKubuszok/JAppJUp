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
