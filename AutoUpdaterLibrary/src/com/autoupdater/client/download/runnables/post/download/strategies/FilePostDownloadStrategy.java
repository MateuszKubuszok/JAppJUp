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
package com.autoupdater.client.download.runnables.post.download.strategies;

import static com.google.common.io.Files.createParentDirs;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Implementation of DownloadStorageStrategyInterface used for downloading files
 * to disk.
 * 
 * @see com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy
 */
public class FilePostDownloadStrategy implements IPostDownloadStrategy<File> {
    private final RandomAccessFile out;
    private final File file;

    /**
     * Creates FileDownloadStrategy instance.
     * 
     * @param file
     *            destination file
     * @throws IOException
     *             thrown if parent directory don't exists and cannot be created
     */
    public FilePostDownloadStrategy(File file) throws IOException {
        this.file = file;
        createParentDirs(file);
        out = new RandomAccessFile(file, "rw");
    }

    /**
     * Creates FileDownloadStrategy instance.
     * 
     * @param fileDestinationPath
     *            path to destination file
     * @throws IOException
     *             thrown if parent directory don't exists and cannot be created
     */
    public FilePostDownloadStrategy(String fileDestinationPath) throws IOException {
        file = new File(fileDestinationPath);
        createParentDirs(file);
        out = new RandomAccessFile(file, "rw");
    }

    @Override
    public void write(byte[] buffer, int readSize) throws IOException {
        out.write(buffer, 0, readSize);
    }

    @Override
    public File processDownload() {
        try {
            out.close();
        } catch (IOException e) {
        }
        return file;
    }
}