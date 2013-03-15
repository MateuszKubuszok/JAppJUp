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
