package com.autoupdater.client.download.runnables.post.download.strategies;

import java.util.SortedSet;

import com.autoupdater.client.models.BugEntry;
import com.autoupdater.client.xml.parsers.AbstractXMLParser;
import com.autoupdater.client.xml.parsers.BugsInfoParser;

/**
 * Implementation of DownloadStorageStrategyInterface used for parsing results
 * into Bugs.
 * 
 * @see com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLPostDownloadStrategy
 */
public class BugsInfoPostDownloadStrategy extends AbstractXMLPostDownloadStrategy<SortedSet<BugEntry>> {
    @Override
    protected AbstractXMLParser<SortedSet<BugEntry>> getParser() {
        return new BugsInfoParser();
    }
}