package com.autoupdater.client.xml.parsers;

import org.dom4j.Document;

public class AbstractXMLParserTester extends AbstractXMLParser<Document> {
    @Override
    Document parseDocument(Document document) {
        return document;
    }
}