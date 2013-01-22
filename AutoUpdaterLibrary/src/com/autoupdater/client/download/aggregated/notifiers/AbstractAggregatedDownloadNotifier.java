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
