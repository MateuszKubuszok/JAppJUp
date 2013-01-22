package com.autoupdater.client.download.runnables;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.autoupdater.client.download.ConnectionConfiguration;

public class HttpURLConnectionMock extends HttpURLConnection {
    private final String content;

    public HttpURLConnectionMock(URL url, String content) {
        super(url);
        this.content = content;
    }

    @Override
    public int getResponseCode() {
        return 200;
    }

    @Override
    public void connect() {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(content.getBytes(ConnectionConfiguration.XML_ENCODING));
    }

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }
}
