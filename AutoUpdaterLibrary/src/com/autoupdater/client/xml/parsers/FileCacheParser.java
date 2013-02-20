package com.autoupdater.client.xml.parsers;

import static net.jsdpu.logger.Logger.getLogger;

import java.util.HashMap;
import java.util.Map;

import net.jsdpu.logger.Logger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.autoupdater.client.xml.schema.FileCacheSchema;

public class FileCacheParser extends AbstractXMLParser<Map<String, String>> {
    private static final Logger logger = getLogger(FileCacheParser.class);

    @Override
    Map<String, String> parseDocument(Document document) throws ParserException {
        logger.trace("Parsing file cache's data file's document");
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
            logger.error("Cannot parse file cache's data file's document: " + e.getMessage()
                    + " (exception thrown)", e);
            throw new ParserException("Error occured while parsing file cache's data file")
                    .addSuppresed(e, ParserException.class);
        }
    }
}
