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
package com.autoupdater.client.download.aggregated.notifiers;

import com.autoupdater.client.download.DownloadListener;
import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.client.utils.services.notifier.AbstractNotifier;

/**
 * Notifies observers about changes in all observed services.
 * 
 * <p>
 * Receives and sends DownloadServiceMessages.
 * </p>
 * 
 * @see com.autoupdater.client.utils.services.notifier.AbstractNotifier
 * @see com.autoupdater.client.download.DownloadListener
 * @see com.autoupdater.client.download.DownloadServiceMessage
 * 
 * @param <AdditionalMessage>
 *            type of additional message that will be bound to service (and will
 *            be possible to obtain in a results calculation)
 */
public abstract class AbstractAggregatedDownloadNotifier<AdditionalMessage> extends
        AbstractNotifier<DownloadServiceMessage, DownloadServiceMessage, AdditionalMessage>
        implements DownloadListener {
    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        hasChanged();
        notifyObservers(message);
    }
}
