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
package com.autoupdater.client.download.events;

import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.services.AbstractDownloadService;

/**
 * Contains information about download status progress.
 * 
 * <p>
 * Sent by classes responsible for downloading content.
 * </p>
 */
public interface IDownloadEvent {
    /**
     * Whether download is in progress.
     * 
     * @return true if download in progress
     */
    public boolean isInProcess();

    /**
     * Whether download was completed successfully.
     * 
     * @return true if download completed successfully
     */
    public boolean isCompleted();

    /**
     * Whether download was completed and processed successfully.
     * 
     * @return true if download processed successfully
     */
    public boolean isProcessed();

    /**
     * Whether download was cancelled.
     * 
     * @return true if download was cancelled
     */
    public boolean isCancelled();

    /**
     * Whether download has failed.
     * 
     * @return true if download has failed
     */
    public boolean isFailed();

    /**
     * Whether download was finished - no matter with what result.
     * 
     * @return true if download was finished one way or another
     */
    public boolean isFinished();

    /**
     * Return message bound to the event.
     * 
     * @return message
     */
    public String getMessage();

    /**
     * Return current progress of download.
     * 
     * @return current download's progress as a number from 0.0 to 1.0
     */
    public Double getProgress();

    /**
     * Return source of event.
     * 
     * @return source of event
     */
    public AbstractDownloadService<?> getSource();

    /**
     * Return current download status.
     * 
     * @return current status
     */
    public EDownloadStatus getStatus();
}
