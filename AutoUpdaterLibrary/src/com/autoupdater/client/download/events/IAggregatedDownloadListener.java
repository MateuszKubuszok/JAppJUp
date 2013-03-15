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

/**
 * Listens to changes/progress in some aggregated download service.
 */
public interface IAggregatedDownloadListener {
    /**
     * Triggered when download queue has started.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueStarted(IAggregatedDownloadEvent event);

    /**
     * Triggered when download queue finished successfully.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueProcessed(IAggregatedDownloadEvent event);

    /**
     * Triggered when download queue was cancelled.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueCancelled(IAggregatedDownloadEvent event);

    /**
     * Triggered when download queue finished but some downloads failed.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueFailed(IAggregatedDownloadEvent event);

    /**
     * Triggered when download queue finished no matter with what results.
     * 
     * @param event
     *            aggregated download event
     */
    public void downloadQueueFinished(IAggregatedDownloadEvent event);
}
