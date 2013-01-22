package com.autoupdater.client.xml.parsers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.autoupdater.client.download.ConnectionConfiguration;

public abstract class AbstractTestXMLParser<r> {
    protected InputStream getInputStreamForString(String string) {
        return new ByteArrayInputStream(string.getBytes(ConnectionConfiguration.XML_ENCODING));
    }
}
