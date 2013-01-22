package com.autoupdater.client.xml.parsers;

import com.autoupdater.client.AutoUpdaterClientException;

/**
 * Exception thrown when error occurs while parsing document.
 * 
 * <p>
 * Extends AutoUpdaterClientException.
 * </p>
 * 
 * @see com.autoupdater.client.AutoUpdaterClientException
 * @see com.autoupdater.client.xml.parsers.AbstractXMLParser
 */
public class ParserException extends AutoUpdaterClientException {
    /**
     * Creates instance of ParserException.
     * 
     * @param message
     *            message to be passed
     */
    ParserException(String message) {
        super(message);
    }
}
