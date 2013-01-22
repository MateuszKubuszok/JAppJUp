package com.autoupdater.client.download;

import java.io.File;
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
    public static final Charset XML_ENCODING = Charset.availableCharsets().get(XML_ENCODING_NAME);

    /**
     * Defines directory where downloaded updates should be stored.
     */
    public static final String FILE_DOWNLOAD_DIR = System.getProperty("java.io.tmpdir")
            + "AutoUpdater" + File.separator + "Updates";

    /**
     * Defines default number of maximal amount of parallel downloads.
     */
    public static final int DEFAULT_MAX_PARALLEL_DOWNLOADS_NUMBER = 4;
}
