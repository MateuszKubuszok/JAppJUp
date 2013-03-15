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
package com.autoupdater.client.download;

/**
 * Describes current download status.
 */
public enum EDownloadStatus {
    /**
     * Status of newly created service that didn't connect to server yet.
     */
    HASNT_STARTED("Download hasn't started yet", false),

    /**
     * Status of service that already connected to server, but didn't start the
     * actual download.
     */
    CONNECTED("Connected to server", false),

    /**
     * Status of service that currently download data from server.
     */
    IN_PROCESS("Download in process", false),

    /**
     * Status of service that completed download but didn't processed (e.g.
     * parsed) it yet.
     */
    COMPLETED("Download completed", false),

    /**
     * Status of service that finished all tasks and has the result of execution
     * available to obtain.
     */
    PROCESSED("Download processed", true),

    /**
     * Status of service that failed due to some kind of error.
     */
    FAILED("Download failed", true),

    /**
     * Status of service, that was cancelled by user.
     */
    CANCELLED("Download cancelled", true);

    private final String message;
    private final boolean downloadFinished;

    private EDownloadStatus(String message, boolean downloadFinished) {
        this.message = message;
        this.downloadFinished = downloadFinished;
    }

    /**
     * Returns message describing download status.
     * 
     * @return message describing download status
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns information whether or not Service/AggregatedService finished
     * download.
     * 
     * @return true if download is finished (no matter result), false if
     *         download is still in progress
     */
    public boolean isDownloadFinished() {
        return downloadFinished;
    }

    @Override
    public String toString() {
        return message;
    }
}
