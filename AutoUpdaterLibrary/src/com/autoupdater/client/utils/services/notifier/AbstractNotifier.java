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
package com.autoupdater.client.utils.services.notifier;

import java.util.HashMap;
import java.util.Map;

import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.IService;
import com.autoupdater.client.utils.services.ObservableService;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Exists as both Observer and ObservableService serving as go-between other
 * Observers and ObservableServices. Base of all Notifier objects.
 * 
 * <p>
 * It is used either to aggregate notifications from many sources into one
 * Observable object, or to change type of passed messages - it can be
 * considered Aggregator or Adapter, depending on usage.
 * </p>
 * 
 * <p>
 * It can also store additional message bound to Observed object, which can be
 * used in update(ObservableService<RecievedMessage>, RecievedMessage)
 * implementation.
 * </p>
 * 
 * @see com.autoupdater.client.utils.services.ObservableService
 * @see com.autoupdater.client.utils.services.IObserver
 * @see com.autoupdater.client.installation.notifiers.InstallationNotifier
 * @see com.autoupdater.client.installation.notifiers.UpdateNotifier
 * @see com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier
 * @see com.autoupdater.client.download.aggregated.notifiers.PackagesInfoAggregatedNotifier
 * @see com.autoupdater.client.download.aggregated.notifiers.UpdateInfoAggregatedNotifier
 * @see com.autoupdater.client.download.aggregated.notifiers.ChangelogInfoAggregatedNotifier
 * @see com.autoupdater.client.download.aggregated.notifiers.FileAggregatedNotifier
 * 
 * @param <SentMessage>
 *            type of messages obtained from Observed
 * @param <RecievedMessage>
 *            type of messages sent to Observers
 * @param <AdditionalMessage>
 *            type of additional message
 */
public abstract class AbstractNotifier<SentMessage, RecievedMessage, AdditionalMessage> extends
        ObservableService<SentMessage> implements IObserver<RecievedMessage> {
    private final Map<IService<RecievedMessage>, AdditionalMessage> additionalMessages;

    public AbstractNotifier() {
        super();
        additionalMessages = new HashMap<IService<RecievedMessage>, AdditionalMessage>();
    }

    /**
     * Returns additional message bound to service, or null if nothing was
     * bound.
     * 
     * @see #setAdditionalMessage(IService, Object)
     * 
     * @param messageSource
     *            Service
     * @return message bound to Service, or null if none set
     */
    public AdditionalMessage getAdditionalMessage(IService<RecievedMessage> messageSource) {
        return additionalMessages.get(messageSource);
    }

    /**
     * Returns first service bound to given message.
     * 
     * @param additionalMessage
     *            AdditionalMessage
     * @return Service bound to message, or null if none set
     */
    public IService<RecievedMessage> getMessageSource(final AdditionalMessage additionalMessage) {
        return Iterables.find(additionalMessages.keySet(),
                new Predicate<IService<RecievedMessage>>() {
                    @Override
                    public boolean apply(IService<RecievedMessage> message) {
                        return additionalMessages.get(message).equals(additionalMessage);
                    }
                }, null);
    }

    /**
     * Binds additional message to service.
     * 
     * @see #getAdditionalMessage(IService)
     * 
     * @param messageSource
     *            Service that might send message
     * @param additionalMessage
     *            message that should be bound to Observable
     */
    public void setAdditionalMessage(IService<RecievedMessage> messageSource,
            AdditionalMessage additionalMessage) {
        additionalMessages.put(messageSource, additionalMessage);
    }
}
