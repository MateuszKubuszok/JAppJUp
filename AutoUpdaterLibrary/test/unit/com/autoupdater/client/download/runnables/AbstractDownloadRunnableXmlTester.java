package com.autoupdater.client.download.runnables;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.dom4j.Document;

import com.autoupdater.client.download.runnables.post.download.strategies.AbstractXMLDownloadStrategyTester;
import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;

public class AbstractDownloadRunnableXmlTester extends AbstractDownloadRunnable<Document> {
    public AbstractDownloadRunnableXmlTester(HttpURLConnection connection) {
        super(connection);
    }

    @Override
    protected IPostDownloadStrategy<Document> getPostDownloadStrategy() throws IOException {
        return new AbstractXMLDownloadStrategyTester();
    }
}