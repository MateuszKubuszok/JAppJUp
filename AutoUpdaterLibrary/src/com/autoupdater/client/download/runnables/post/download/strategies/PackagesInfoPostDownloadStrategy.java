package com.autoupdater.client.download.runnables.post.download.strategies;

import java.util.SortedSet;

import com.autoupdater.client.models.Program;
import com.autoupdater.client.xml.parsers.AbstractXMLParser;
import com.autoupdater.client.xml.parsers.PackagesInfoParser;

/**
 * Implementation of DownloadStorageStrategyInterface used for parsing results
 * into set of Program models.
 * 
 * @see com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLPostDownloadStrategy
 */
public class PackagesInfoPostDownloadStrategy extends AbstractXMLPostDownloadStrategy<SortedSet<Program>> {
    @Override
    protected AbstractXMLParser<SortedSet<Program>> getParser() {
        return new PackagesInfoParser();
    }
}