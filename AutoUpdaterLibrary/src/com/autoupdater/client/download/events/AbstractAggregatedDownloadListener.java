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
 * Abstract aggregated download listener implementation that can be used for
 * listening to only some specific events.
 * 
 * @see com.autoupdater.client.download.events.IAggregatedDownloadEvent
 * @see com.autoupdater.client.download.events.IAggregatedDownloadListener
 */
public abstract class AbstractAggregatedDownloadListener implements IAggregatedDownloadListener {
    @Override
    public void downloadQueueStarted(IAggregatedDownloadEvent event) {
    }

    @Override
    public void downloadQueueProcessed(IAggregatedDownloadEvent event) {
    }

    @Override
    public void downloadQueueCancelled(IAggregatedDownloadEvent event) {
    }

    @Override
    public void downloadQueueFailed(IAggregatedDownloadEvent event) {
    }

    @Override
    public void downloadQueueFinished(IAggregatedDownloadEvent event) {
    }
}
