package com.autoupdater.client.xml.schema;

/**
 * Describes XML schema of updates information document.
 * 
 * Used by PackagesInfoXMLParser.
 * 
 * @see com.autoupdater.client.xml.parsers.PackagesInfoParser
 */
public class PackagesInfoSchema {
    public static final String programs = "programs";

    public static final class Programs {
        public static final String program = "program";
        public static final String program_ = programs + "/" + program;

        public static final class Program {
            public static final String programName = "name";
            public static final String programName_ = program_ + "/" + programName;

            public static final String _package = "package";
            public static final String _package_ = program_ + "/" + _package;

            public static final class Package {
                public static final String name = "name";
                public static final String name_ = _package_ + "/" + name;

                public static final String id = "id";
                public static final String id_ = _package_ + "/" + id;
            }
        }
    }
}
