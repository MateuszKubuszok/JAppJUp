package com.autoupdater.client.download.runnables.post.download.strategies;

import java.util.SortedSet;

import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.xml.parsers.AbstractXMLParser;
import com.autoupdater.client.xml.parsers.ChangelogInfoParser;

/**
 * Implementation of DownloadStorageStrategyInterface used for parsing results
 * into Changelogs.
 * 
 * @see com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLPostDownloadStrategy
 */
public class ChangelogInfoPostDownloadStrategy extends
        AbstractXMLPostDownloadStrategy<SortedSet<ChangelogEntry>> {
    @Override
    protected AbstractXMLParser<SortedSet<ChangelogEntry>> getParser() {
        return new ChangelogInfoParser();
    }
}