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
                public static final String name = "name";
                public static final String name_ = package_ + "/" + name;

                public static final String id = "id";
                public static final String id_ = package_ + "/" + id;

                public static final String version = "version";
                public static final String version_ = package_ + "/" + version;
            }
        }
    }
}
