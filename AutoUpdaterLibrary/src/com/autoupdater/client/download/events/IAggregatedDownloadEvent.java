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
import com.autoupdater.client.download.aggregated.services.AbstractAggregatedDownloadService;

/**
 * Contains information about download queue status.
 * 
 * <p>
 * Sent by classes responsible for downloading content in queues.
 * </p>
 */
public interface IAggregatedDownloadEvent {
    /**
     * Whether download queue started execution.
     * 
     * @return true if downloads started
     */
    public boolean isInProcess();

    /**
     * Whether download queue finished with success.
     * 
     * @return true if all downloads finished successfully
     */
    public boolean isProcessed();

    /**
     * Whether download queue was cancelled.
     * 
     * @return true if queue was cancelled
     */
    public boolean isCancelled();

    /**
     * Whether downloads finished and some of them failed.
     * 
     * @return true if queue was executed and some downloads failed
     */
    public boolean isFailed();

    /**
     * Whether download queue was finished no matter with what results.
     * 
     * @return true if queue execution finished
     */
    public boolean isFinished();

    /**
     * Returns number of all executed downloads.
     * 
     * @return all finished downloads number
     */
    public int getFinishedDownloadsNumber();

    /**
     * Returns number of all downloads.
     * 
     * @return all downloads number
     */
    public int getAllDownloadsNumber();

    /**
     * Returns message bound to event.
     * 
     * @return message for event
     */
    public String getMessage();

    /**
     * Get parental event from DownloadService that triggered update in
     * AggregatedDownloadService.
     * 
     * @return parental download event if there is any
     */
    public IDownloadEvent getParentEvent();

    /**
     * Returns current status of download queue.
     * 
     * @return download queue status
     */
    public EDownloadStatus getStatus();

    /**
     * Returns service being the source of event.
     * 
     * @return service being source of event
     */
    public AbstractAggregatedDownloadService<?, ?, ?, ?, ?> getSource();
}
