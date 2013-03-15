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
package com.autoupdater.client.xml.schema;

/**
 * Describes XML schema of file cache document.
 * 
 * Used by FileCacheXMLCreator and CileChacheXMLParser.
 * 
 * @see com.autoupdater.client.xml.creators.FileCacheXMLCreator
 * @see com.autoupdater.client.xml.parsers.FileCacheParser
 */
public class FileCacheSchema {
    public static final String files = "files";

    public static final class Files {

        public static final String file = "file";
        public static final String file_ = files + "/" + file;

        public static class File {
            public static final String path = "path";
            public static final String path_ = file_ + path;

            public static final String hash = "hash";
            public static final String hash_ = file_ + hash;
        }
    }
}
