package com.autoupdater.client.download.runnables.post.download.strategies;

import java.util.SortedSet;

import com.autoupdater.client.models.Update;
import com.autoupdater.client.xml.parsers.AbstractXMLParser;
import com.autoupdater.client.xml.parsers.UpdateInfoParser;

/**
 * Implementation of DownloadStorageStrategyInterface used for parsing results
 * into Update.
 * 
 * @see com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy
 * @see com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLPostDownloadStrategy
 */
public class UpdateInfoPostDownloadStrategy extends AbstractXMLPostDownloadStrategy<SortedSet<Update>> {
    @Override
    protected AbstractXMLParser<SortedSet<Update>> getParser() {
        return new UpdateInfoParser();
    }
}
