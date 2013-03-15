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
package com.autoupdater.client.download.aggregated.services;

import static com.autoupdater.client.download.EDownloadStatus.*;

import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.events.IAggregatedDownloadEvent;
import com.autoupdater.client.download.events.IDownloadEvent;

/**
 * Implementation of IAggregatedDownloadEvent.
 * 
 * @see com.autoupdater.client.download.events.IAggregatedDownloadEvent
 */
class AggregatedDownloadEventImpl implements IAggregatedDownloadEvent {
    private final AbstractAggregatedDownloadService<?, ?, ?, ?, ?> aggregatedDownloadService;
    private final EDownloadStatus status;
    private final String message;
    private final IDownloadEvent downloadEvent;
    private final int allDownloadsNumber;
    private final int finishedDownloadsNumber;

    /**
     * Creates event for download (triggered by subtask).
     * 
     * @param aggregatedDownloadService
     *            source of event
     * @param message
     *            message to pass
     * @param downloadEvent
     *            subtask's event that triggered this one
     */
    AggregatedDownloadEventImpl(
            AbstractAggregatedDownloadService<?, ?, ?, ?, ?> aggregatedDownloadService,
            String message, IDownloadEvent downloadEvent) {
        this.aggregatedDownloadService = aggregatedDownloadService;
        this.downloadEvent = downloadEvent;
        this.message = message;
        status = aggregatedDownloadService.getStatus();
        allDownloadsNumber = aggregatedDownloadService.getAllDownloadsNumber();
        finishedDownloadsNumber = aggregatedDownloadService.getFinishedDownloadsNumber();
    }

    /**
     * Creates event for download (not triggered by subtask).
     * 
     * @param aggregatedDownloadService
     *            source of event
     * @param message
     *            message to pass
     */
    AggregatedDownloadEventImpl(
            AbstractAggregatedDownloadService<?, ?, ?, ?, ?> aggregatedDownloadService,
            String message) {
        this(aggregatedDownloadService, message, null);
    }

    @Override
    public boolean isInProcess() {
        return status.equals(IN_PROCESS);
    }

    @Override
    public boolean isProcessed() {
        return status.equals(PROCESSED);
    }

    @Override
    public boolean isCancelled() {
        return status.equals(CANCELLED);
    }

    @Override
    public boolean isFailed() {
        return status.equals(FAILED);
    }

    @Override
    public boolean isFinished() {
        return status.isDownloadFinished();
    }

    @Override
    public int getFinishedDownloadsNumber() {
        return finishedDownloadsNumber;
    }

    @Override
    public int getAllDownloadsNumber() {
        return allDownloadsNumber;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public IDownloadEvent getParentEvent() {
        return downloadEvent;
    }

    @Override
    public EDownloadStatus getStatus() {
        return status;
    }

    @Override
    public AbstractAggregatedDownloadService<?, ?, ?, ?, ?> getSource() {
        return aggregatedDownloadService;
    }
}
