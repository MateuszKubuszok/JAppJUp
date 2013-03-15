/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
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
    public static final int CONNECTION_TIME_OUT = 60 * 1000;

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
     * Defines path to file that stores cache.
     */
    public static final String CACHE_INFO_FILE = DOWNLOAD_DIRECTORY + separator + "cache.dat";

    /**
     * Defines default number of maximal amount of parallel downloads.
     */
    public static final int DEFAULT_MAX_PARALLEL_DOWNLOADS_NUMBER = 4;
}
