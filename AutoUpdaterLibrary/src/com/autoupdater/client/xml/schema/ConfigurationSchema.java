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
 * Describes XML schema of configuration document.
 * 
 * Used by ConfigurationXMLCreator and ConfigurationXMLParser.
 * 
 * @see com.autoupdater.client.xml.creators.ConfigurationXMLCreator
 * @see com.autoupdater.client.xml.parsers.ConfigurationParser
 */
public class ConfigurationSchema {
    public static final String configuration = "configuration";

    public static final class Configuration {
        public static final String client = "client";
        public static final String client_ = configuration + "/" + client;

        public static final class Client {
            public static final String name = "name";
            public static final String name_ = client_ + "/" + name;

            public static final String executable = "executable";
            public static final String executable_ = client_ + "/" + executable;

            public static final String locations = "locations";
            public static final String locations_ = client_ + "/" + locations;

            public static final class Locations {
                public static final String pathToClientDirectory = "clientDir";
                public static final String pathToClientDirectory_ = locations_ + "/"
                        + pathToClientDirectory;

                public static final String pathToClient = "console";
                public static final String pathToClient_ = locations_ + "/" + pathToClient;

                public static final String installer = "installer";
                public static final String installer_ = locations_ + "/" + installer;

                public static final String uacHandler = "uacHandler";
                public static final String uacHandler_ = locations_ + "/" + uacHandler;
            }

            public static final String proxy = "proxy";
            public static final String proxy_ = client_ + "/" + proxy;

            public static final class Proxy {
                public static final String address = "host";
                public static final String address_ = proxy_ + "/" + address;

                public static final String port = "port";
                public static final String port_ = proxy_ + "/" + port;
            }
        }

        public static final String programs = "programs";
        public static final String programs_ = configuration + "/" + programs;

        public static final class Programs {
            public static final String program = "program";
            public static final String program_ = programs_ + "/" + program;

            public static final class Program {
                public static final String name = "name";
                public static final String name_ = program_ + "/" + name;

                public static final String executableName = "executableName";
                public static final String executableName_ = program_ + "/" + executableName;

                public static final String pathToProgramDirectory = "programDir";
                public static final String pathToProgramDirectory_ = program_ + "/"
                        + pathToProgramDirectory;

                public static final String pathToProgram = "console";
                public static final String pathToProgram_ = program_ + "/" + pathToProgram;

                public static final String serverAddress = "serverURL";
                public static final String serverAddress_ = program_ + "/" + serverAddress;

                public static final String developmentVersion = "developmentVersion";
                public static final String developmentVersion_ = program_ + "/"
                        + developmentVersion;
            }
        }
    }
}
