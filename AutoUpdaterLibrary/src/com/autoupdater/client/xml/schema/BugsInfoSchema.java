package com.autoupdater.client.xml.schema;

/**
 * Describes XML schema of bugs information document.
 * 
 * Used by BugsInfoXMLParser.
 * 
 * @see com.autoupdater.client.xml.parsers.BugsInfoParser
 */
public final class BugsInfoSchema {
    public static final String bugs = "bugs";

    public static final class Bugs {

        public static final String bug = "bug";
        public static final String bug_ = bugs + "/" + bug;
    }
}
