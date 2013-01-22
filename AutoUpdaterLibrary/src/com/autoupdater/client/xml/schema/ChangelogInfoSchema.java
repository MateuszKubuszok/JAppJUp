package com.autoupdater.client.xml.schema;

/**
 * Describes XML schema of updates information document.
 * 
 * Used by ChangelogInfoXMLParser.
 * 
 * @see com.autoupdater.client.xml.parsers.ChangelogInfoParser
 */
public final class ChangelogInfoSchema {
    public static final String changelogs = "changelogs";

    public static final class Changelogs {

        public static final String changelog = "changelog";
        public static final String changelog_ = changelogs + "/" + changelog;

        public static final String version = "version";
        public static final String version_ = changelogs + "/" + version;
    }
}
