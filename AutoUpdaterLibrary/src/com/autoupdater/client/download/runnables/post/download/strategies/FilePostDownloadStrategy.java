package com.autoupdater.client.download.runnables.post.download.strategies;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.google.common.io.Files;

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
        Files.createParentDirs(file);
        out = new RandomAccessFile(file, "rws");
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
        Files.createParentDirs(file);
        out = new RandomAccessFile(file, "rw");
    }

    @Override
    public void write(byte[] buffer, int readSize) throws IOException {
        out.write(buffer, 0, readSize);
    }

    @Override
    public File processDownload() {
        return file;
    }
}