package com.autoupdater.client.xml.parsers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.autoupdater.client.download.ConnectionConfiguration;

/**
 * Superclass of all XMLParsers.
 * 
 * @see com.autoupdater.client.xml.parsers.ConfigurationParser
 * @see com.autoupdater.client.xml.parsers.InstallationDataParser
 * @see com.autoupdater.client.xml.parsers.PackagesInfoParser
 * @see com.autoupdater.client.xml.parsers.UpdateInfoParser
 * @see com.autoupdater.client.xml.parsers.ChangelogInfoParser
 * 
 * @param <Result>
 *            type of result returned by parser
 */
public abstract class AbstractXMLParser<Result> {
    private final SAXReader xmlParser;

    public AbstractXMLParser() {
        xmlParser = new SAXReader();
    }

    /**
     * Parses document from file and returns result.
     * 
     * @see #parseDocument(Document)
     * 
     * @param file
     *            source file
     * @return returns result
     * @throws ParserException
     *             thrown when error occurs while parsing document
     */
    public Result parseXML(File file) throws ParserException {
        try {
            if (!file.canRead() || !file.exists())
                throw new ParserException("Cannot parse document: Cannot open file");
            return parseDocument(getXMLParser().read(file));
        } catch (DocumentException e) {
            throw new ParserException("Cannot parse document: " + e.getMessage());
        }
    }

    /**
     * Parses document from input stream and returns result.
     * 
     * @see #parseDocument(Document)
     * 
     * @param in
     *            source input stream
     * @return returns result
     * @throws ParserException
     *             thrown when error occurs while parsing document
     */
    public Result parseXML(InputStream in) throws ParserException {
        try {
            return parseDocument(getXMLParser().read(in));
        } catch (DocumentException e) {
            throw new ParserException("Cannot parse document: " + e.getMessage());
        }
    }

    /**
     * Parses document from string and returns result.
     * 
     * @see #parseDocument(Document)
     * 
     * @param xmlAsString
     *            source string
     * @return returns result
     * @throws ParserException
     *             thrown when error occurs while parsing document
     */
    public Result parseXML(String xmlAsString) throws ParserException {
        try {
            return parseDocument(getXMLParser().read(
                    new ByteArrayInputStream(xmlAsString.trim().getBytes(
                            ConnectionConfiguration.XML_ENCODING))));
        } catch (DocumentException e) {
            throw new ParserException("Cannot parse document: " + e.getMessage());
        }
    }

    /**
     * Parses passed Document and returns result.
     * 
     * @see #parseXML(File)
     * @see #parseXML(InputStream)
     * @see #parseXML(String)
     * 
     * @param document
     *            parsed document
     * @return returns result
     * @throws ParserException
     *             thrown when error occurs while parsing document
     */
    abstract Result parseDocument(Document document) throws ParserException;

    /**
     * Returns content of an element.
     * 
     * @param element
     *            element which content needs to be obtained
     * @return content
     */
    protected String getContent(Element element) {
        StringBuilder builder = new StringBuilder();
        for (Node child : element)
            builder.append(child.asXML());
        return builder.toString();
    }

    /**
     * Returns reader's instance.
     * 
     * @return reader's instance.
     */
    protected SAXReader getXMLParser() {
        return xmlParser;
    }
}
