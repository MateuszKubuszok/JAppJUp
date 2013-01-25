package com.autoupdater.client.xml.parsers;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.autoupdater.client.xml.schema.FileCacheSchema;

public class FileCacheParser extends AbstractXMLParser<Map<String, String>> {
    @Override
    Map<String, String> parseDocument(Document document) throws ParserException {
        try {
            Map<String, String> files = new HashMap<String, String>();

            for (Node fileNode : document.selectNodes("./" + FileCacheSchema.Files.file_)) {
                Element file = (Element) fileNode;
                String path = file.attributeValue(FileCacheSchema.Files.File.path);
                String hash = file.attributeValue(FileCacheSchema.Files.File.hash);

                files.put(path, hash);
            }

            return files;
        } catch (Exception e) {
            throw new ParserException("Error occured while parsing installation data file");
        }
    }
}
