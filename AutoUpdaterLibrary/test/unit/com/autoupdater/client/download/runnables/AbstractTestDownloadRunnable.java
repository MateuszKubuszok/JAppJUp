package com.autoupdater.client.download.runnables;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractTestDownloadRunnable {
    protected HttpURLConnection getConnection(String content) throws MalformedURLException {
        return new HttpURLConnectionMock(new URL("http://127.0.0.1"), content);
    }
}
