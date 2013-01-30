package com.autoupdater.client.download;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.autoupdater.client.xml.creators.FileCacheXMLCreator;
import com.autoupdater.client.xml.parsers.FileCacheParser;
import com.autoupdater.client.xml.parsers.ParserException;

/**
 * Class used for marking files as cached and checking whether file was already
 * marked as correctly downloaded.
 */
public class FileCache {
    private static final int INPUT_BUFFER_SIZE = 1024 * 1024;
    private static Map<String, String> cacheMap;

    /**
     * Static class.
     */
    private FileCache() {
    }

    /**
     * Whether file is downloaded and not damaged.
     * 
     * @param path
     *            path to file
     * @return true if file is already downloaded
     */
    public static boolean isFileDownloaded(String path) {
        if (getMap().containsKey(path)) {
            File file = new File(path);
            return file.exists() && cacheMap.get(path).equals(calculateHash(file));
        }
        return false;
    }

    /**
     * Marks file as downloaded and calculates its checksum.
     * 
     * @param path
     *            path to file
     */
    public static void setFileDownloaded(String path) {
        File file = new File(path);
        if (file.exists()) {
            String hash = calculateHash(file);
            if (hash != null) {
                cacheMap.put(path, hash);
                setMap(cacheMap);
            }
        }
    }

    /**
     * Obtains cache - if it's not loaded, loads it.
     * 
     * @return cache
     */
    private static Map<String, String> getMap() {
        if (cacheMap == null) {
            File file = new File(ConnectionConfiguration.CACHE_INFO_FILE);
            if (file.exists()) {
                try {
                    cacheMap = new FileCacheParser().parseXML(file);
                } catch (ParserException e) {
                    cacheMap = new HashMap<String, String>();
                }
            } else
                cacheMap = new HashMap<String, String>();
        }
        return cacheMap;
    }

    /**
     * Saves cache to disc.
     * 
     * @param map
     *            map to save
     */
    private static void setMap(Map<String, String> map) {
        File destination = new File(ConnectionConfiguration.CACHE_INFO_FILE);
        try {
            new FileCacheXMLCreator().createXML(destination, map);
        } catch (IOException e) {
        }
    }

    /**
     * Calculates hash of a file.
     * 
     * <p>
     * Currently MD5 its used since its only used for checking file's
     * consistency.
     * </p>
     * 
     * @param file
     *            file to be hashed
     * @return hash - md5
     */
    private static String calculateHash(File file) {
        try {
            return md5Hex(new BufferedInputStream(new FileInputStream(file), INPUT_BUFFER_SIZE));
        } catch (IOException e) {
            return null;
        }
    }
}
