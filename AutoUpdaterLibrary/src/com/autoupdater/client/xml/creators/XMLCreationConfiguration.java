package com.autoupdater.client.xml.creators;

import java.nio.charset.Charset;

/**
 * Contains hardcoded configuration used by XMLCreators.
 * 
 * @see com.autoupdater.client.xml.creators.ConfigurationXMLCreator
 * @see com.autoupdater.client.xml.creators.InstallationDataXMLCreator
 */
public final class XMLCreationConfiguration {
    /**
     * Contents of a heading comment in each generated file. Warns of manual
     * edition of generated files.
     */
    public static final String DO_NOT_EDIT_FILE_MANUALLY_WARNING = "This XML configuration file was automaticaly created "
            + "by AutoUpdater library, and can be generater anew at any time! "
            + "Please, use library methods to change configuration.";

    /**
     * Name of encoding used in generated documents.
     */
    public static final String XML_ENCODING_NAME = "UTF-8";

    /**
     * Charset used in generated documents.
     */
    public static final Charset XML_ENCODING = Charset.availableCharsets().get(XML_ENCODING_NAME);
}
