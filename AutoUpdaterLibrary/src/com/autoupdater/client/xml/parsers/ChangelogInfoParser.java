package com.autoupdater.client.xml.parsers;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.models.ChangelogEntryBuilder;
import com.autoupdater.client.xml.schema.ChangelogInfoSchema;

/**
 * Implementation parsing incoming XML data into Changelog.
 */
public class ChangelogInfoParser extends AbstractXMLParser<SortedSet<ChangelogEntry>> {
    @Override
    SortedSet<ChangelogEntry> parseDocument(Document document) throws ParserException {
        try {
            SortedSet<ChangelogEntry> changelogs = new TreeSet<ChangelogEntry>();

            List<? extends Node> changelogsNodes = document.selectNodes("./"
                    + ChangelogInfoSchema.Changelogs.changelog_);
            for (Node changelogNode : changelogsNodes) {
                String version = ((Element) changelogNode)
                        .attributeValue(ChangelogInfoSchema.Changelogs.version);
                String changelog = getContent((Element) changelogNode);
                changelogs.add(ChangelogEntryBuilder.builder().setDescription(changelog)
                        .setVersionNumber(version).build());
            }

            return changelogs;
        } catch (Exception e) {
            throw new ParserException("Error occured while parsing response");
        }
    }
}
