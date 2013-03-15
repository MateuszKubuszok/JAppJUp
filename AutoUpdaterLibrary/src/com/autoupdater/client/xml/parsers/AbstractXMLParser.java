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
package com.autoupdater.client.xml.parsers;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.jsdpu.logger.Logger;

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
    private static final Logger logger = getLogger(AbstractXMLParser.class);

    private final SAXReader xmlParser;

    /**
     * Creates new parser instance.
     */
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
            logger.debug("Parse file: " + file.getCanonicalPath());
            if (!file.canRead() || !file.exists()) {
                logger.error("Cannot parse document: Cannot open file (exception thrown)");
                throw new ParserException("Cannot parse document: Cannot open file");
            }
            return parseDocument(getXMLParser().read(file));
        } catch (DocumentException | IOException e) {
            logger.error("Cannot parse document: " + e.getMessage() + " (exception thrown)", e);
            throw new ParserException("Cannot parse document: " + e.getMessage()).addSuppresed(e,
                    ParserException.class);
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
            logger.debug("Parse input stream: " + in.getClass().getName());
            return parseDocument(getXMLParser().read(in));
        } catch (DocumentException e) {
            logger.error("Cannot parse document: " + e.getMessage() + " (exception thrown)", e);
            throw new ParserException("Cannot parse document: " + e.getMessage()).addSuppresed(e,
                    ParserException.class);
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
            logger.debug("Parse string: " + xmlAsString);
            return parseDocument(getXMLParser().read(
                    new ByteArrayInputStream(xmlAsString.trim().getBytes(
                            ConnectionConfiguration.XML_ENCODING))));
        } catch (DocumentException e) {
            logger.error("Cannot parse document: " + e.getMessage() + " (exception thrown)", e);
            throw new ParserException("Cannot parse document: " + e.getMessage()).addSuppresed(e,
                    ParserException.class);
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
