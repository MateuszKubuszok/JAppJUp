package com.autoupdater.client.download.runnables.post.download.strategies;

import org.dom4j.Document;

import com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLPostDownloadStrategy;
import com.autoupdater.client.xml.parsers.AbstractXMLParser;
import com.autoupdater.client.xml.parsers.AbstractXMLParserTester;

public class AbstractXMLDownloadStrategyTester extends AbstractXMLPostDownloadStrategy<Document> {
    @Override
    protected AbstractXMLParser<Document> getParser() {
        return new AbstractXMLParserTester();
    }
}