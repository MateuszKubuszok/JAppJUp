package com.autoupdater.client.xml.creators;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.jsdpu.logger.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.autoupdater.client.xml.schema.FileCacheSchema;
import com.google.common.io.Files;

public class FileCacheXMLCreator {
    private static final Logger logger = getLogger(FileCacheXMLCreator.class);

    /**
     * Creates XML document with file cache data and stores it info file.
     * 
     * @param destination
     *            destination file
     * @param fileCache
     *            file cache which needs to be saved
     * @throws IOException
     *             thrown when error occurs during storing data to file
     */
    public void createXML(File destination, Map<String, String> fileCache) throws IOException {
        logger.debug("Save file cache data at: " + destination.getCanonicalPath());
        Document fileCacheXML = DocumentHelper.createDocument();
        fileCacheXML.addComment(XMLCreationConfiguration.DO_NOT_EDIT_FILE_MANUALLY_WARNING);
        Element files = fileCacheXML.addElement(FileCacheSchema.files);
        addFiles(files, fileCache);
        Files.write(fileCacheXML.asXML(), destination, XMLCreationConfiguration.XML_ENCODING);
        logger.trace("Saved file cache data at: " + destination.getCanonicalPath());
    }

    /**
     * Creates node for each file.
     * 
     * @param files
     *            parent node
     * @param fileCache
     *            map with paths and hashes
     */
    private void addFiles(Element files, Map<String, String> fileCache) {
        for (String path : fileCache.keySet()) {
            String hash = fileCache.get(path);
            addFile(files, path, hash);
        }
    }

    /**
     * Creates node for a file.
     * 
     * @param files
     *            parent node
     * @param path
     *            path to file
     * @param hash
     *            file's hash
     */
    private void addFile(Element files, String path, String hash) {
        Element file = files.addElement(FileCacheSchema.Files.file);
        file.addAttribute(FileCacheSchema.Files.File.path, path);
        file.addAttribute(FileCacheSchema.Files.File.hash, hash);
    }
}
