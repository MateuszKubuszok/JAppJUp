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
 * Describes XML schema of updates information document.
 * 
 * Used by UpdateInfoXMLParser.
 * 
 * @see com.autoupdater.client.xml.parsers.UpdateInfoParser
 */
public class UpdateInfoSchema {
    public static final String updates = "updates";

    public static final class Updates {
        public static final String update = "update";
        public static final String update_ = updates + "/" + update;

        public static final class Update {
            public static final String id = "id";
            public static final String id_ = update + "/" + id;

            public static final String packageName = "packageName";
            public static final String packageName_ = update + "/" + packageName;

            public static final String packageID = "packageId";
            public static final String packageID_ = update + "/" + packageID;

            public static final String version = "version";
            public static final String version_ = update + "/" + version;

            public static final String developmentVersion = "developmentVersion";
            public static final String developmentVersion_ = update + "/" + developmentVersion;

            public static final String changelog = "changelog";
            public static final String changelog_ = update + "/" + changelog;

            public static final String type = "type";
            public static final String type_ = update + "/" + type;

            public static final String originalName = "originalName";
            public static final String originalName_ = update + "/" + originalName;

            public static final String relativePath = "relativePath";
            public static final String relativePath_ = update + "/" + relativePath;

            public static final String command = "command";
            public static final String command_ = update + "/" + command;
        }
    }
}
