package com.autoupdater.client.download;

import static java.io.File.separator;
import static java.nio.charset.Charset.availableCharsets;

import java.nio.charset.Charset;

/**
 * Contains hardcoded connections configuration.
 */
public final class ConnectionConfiguration {
    /**
     * Defines connection time out.
     */
    public static final int CONNECTION_TIME_OUT = 60000;

    /**
     * Defines size of buffer used for loading data from stream.
     */
    public static final int MAX_BUFFER_SIZE = 512;

    /**
     * Defines name of encoding used by parsers.
     */
    public static final String XML_ENCODING_NAME = "UTF-8";

    /**
     * Defines charset used by parsers.
     */
    public static final Charset XML_ENCODING = availableCharsets().get(XML_ENCODING_NAME);

    /**
     * Defines directory where downloaded updates should be stored.
     */
    public static final String DOWNLOAD_DIRECTORY;
    static {
        String dir = System.getProperty("java.io.tmpdir");
        if (dir.endsWith("\\") || dir.endsWith("/"))
            dir = dir.substring(0, dir.length() - 1);
        dir += separator + "AutoUpdater" + separator + "Updates";
        DOWNLOAD_DIRECTORY = dir;
    }

    /**
     * Defines default number of maximal amount of parallel downloads.
     */
    public static final int DEFAULT_MAX_PARALLEL_DOWNLOADS_NUMBER = 4;
}
