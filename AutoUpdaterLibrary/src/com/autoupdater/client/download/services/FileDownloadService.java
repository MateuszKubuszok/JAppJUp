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
package com.autoupdater.client.download.services;

import java.io.File;
import java.net.HttpURLConnection;

import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.download.runnables.FileDownloadRunnable;

/**
 * Service, that download file from given connection, and returns result as a
 * File.
 */
public class FileDownloadService extends AbstractDownloadService<File> {
    /**
     * Creates instance of FileDownloadService.
     * 
     * @param connection
     *            connection used to obtain data
     * @param fileDestinationPath
     *            path to file where result should be stored
     */
    public FileDownloadService(HttpURLConnection connection, String fileDestinationPath) {
        super(connection, fileDestinationPath);
    }

    @Override
    protected AbstractDownloadRunnable<File> getRunnable() {
        return new FileDownloadRunnable(getConnection(), getFileDestinationPath());
    }
}
