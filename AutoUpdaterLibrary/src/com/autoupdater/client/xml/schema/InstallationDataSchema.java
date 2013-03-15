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
 * Describes XML schema of installation data document.
 * 
 * Used by InstallationDataXMLCreator and InstallationDataXMLParser.
 * 
 * @see com.autoupdater.client.xml.creators.InstallationDataXMLCreator
 * @see com.autoupdater.client.xml.parsers.InstallationDataParser
 */
public class InstallationDataSchema {
    public static final String installed = "installed";

    public static final class Installed {
        public static final String program = "program";
        public static final String program_ = installed + "/" + program;

        public static final class Program {
            public static final String name = "name";
            public static final String name_ = program_ + "/" + name;

            public static final String pathToDirectory = "pathToDirectory";
            public static final String pathToDirectory_ = program_ + "/" + pathToDirectory;

            public static final String serverAddress = "serverAddress";
            public static final String serverAddress_ = program_ + "/" + serverAddress;

            public static final String _package = "package";
            public static final String package_ = program_ + "/" + name;

            public static final class Package {
                @SuppressWarnings("hiding")
                public static final String name = "name";
                @SuppressWarnings("hiding")
                public static final String name_ = package_ + "/" + name;

                public static final String id = "id";
                public static final String id_ = package_ + "/" + id;

                public static final String version = "version";
                public static final String version_ = package_ + "/" + version;
            }
        }
    }
}
