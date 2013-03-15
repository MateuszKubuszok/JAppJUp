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
package com.autoupdater.client.download.runnables;

import static com.autoupdater.client.download.FileCache.*;
import static net.jsdpu.logger.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import net.jsdpu.logger.Logger;

import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.runnables.post.download.strategies.FilePostDownloadStrategy;
import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;

/**
 * Implementation downloading files.
 * 
 * <p>
 * Use FileDownloadStrategy.
 * </p>
 * 
 * <p>
 * Used by FileDownloadService.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.AbstractDownloadRunnable
 * @see com.autoupdater.client.download.runnables.post.download.strategies.FilePostDownloadStrategy
 * @see com.autoupdater.client.download.services.FileDownloadService
 */
public class FileDownloadRunnable extends AbstractDownloadRunnable<File> {
    private static final Logger logger = getLogger(FileDownloadRunnable.class);

    /**
     * Creates PackagesInfoDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     * @param fileDestinationPath
     *            path to destination file
     */
    public FileDownloadRunnable(HttpURLConnection connection, String fileDestinationPath) {
        super(connection, fileDestinationPath);
    }

    @Override
    public void run() {
        if (isFileDownloaded(getFileDestinationPath())) {
            result = new File(getFileDestinationPath());
            try {
                reportChange("File retrived from cache", EDownloadStatus.PROCESSED);
                logger.debug("Retrived file '" + result.getPath() + "' from cache");
            } catch (InterruptedException e) {
                reportCancelled();
            }
        } else {
            super.run();
            if (getStatus() == EDownloadStatus.PROCESSED)
                setFileDownloaded(getFileDestinationPath());
        }
    }

    @Override
    protected IPostDownloadStrategy<File> getPostDownloadStrategy() throws IOException {
        return new FilePostDownloadStrategy(getFileDestinationPath());
    }
}
