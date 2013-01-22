package com.autoupdater.server.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Service responsible for managing uploaded files.
 */
@Repository
public interface FileService {
    /**
     * Saves file to disc.
     * 
     * @param storagePath
     *            path relative to storage directory
     * @param content
     *            saved content
     * @throws IOExcpetion
     *             if exception occurred while trying to save file
     */
    public void saveFile(String storagePath, byte[] content) throws IOException;

    /**
     * Saves uploaded file to disc.
     * 
     * @param multipartFile
     *            uploaded file
     * @return storagePath under which file was placed, null if file couldn't be
     *         saved
     * @throws IOExcpetion
     *             if exception occurred while trying to save file
     */
    public String saveMultipartFile(CommonsMultipartFile multipartFile) throws IOException;

    /**
     * Reads file from disc and return its content as input stream.
     * 
     * @param storagePath
     *            path relative to storage directory.
     * @return input stream with files content
     * @throws FileNotFoundException
     *             if file wasn't found
     */
    public InputStream loadFile(String storagePath) throws FileNotFoundException;
    
    /**
     * Removes file from disc.
     * 
     * @param storagePath
     *            path relative to storage directory.
     * 
     * @param storagePath
     */
    public void removeFile(String storagePath);
}
