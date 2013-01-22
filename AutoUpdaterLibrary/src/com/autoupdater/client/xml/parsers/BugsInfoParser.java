package com.autoupdater.client.xml.parsers;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
    @Override
    SortedSet<BugEntry> parseDocument(Document document) throws ParserException {
        try {
            SortedSet<BugEntry> bugs = new TreeSet<BugEntry>();

            List<? extends Node> bugsNodes = document.selectNodes("./" + BugsInfoSchema.Bugs.bug_);
            for (Node bugNode : bugsNodes) {
                String description = getContent((Element) bugNode);
                bugs.add(BugEntryBuilder.builder().setDescription(description).build());
            }

            return bugs;
        } catch (Exception e) {
            throw new ParserException("Error occured while parsing response");
        }
    }
}
