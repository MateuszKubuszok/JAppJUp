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
     * @throws IOException
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
     * @throws IOException
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
     *            path relative to storage directory
     */
    public void removeFile(String storagePath);
}
