package com.autoupdater.client.installation.notifiers;

import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.client.utils.services.notifier.AbstractNotifier;

/**
 * Notifies about changes in installation progress.
 * 
 * @see com.autoupdater.client.installation.services.InstallationService
 * @see com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService
 */
public class InstallationNotifier
        extends
        AbstractNotifier<InstallationServiceMessage, InstallationServiceMessage, Update> {
    /**
     * Notifies about installation changes.
     */
    @Override
    public void update(
            ObservableService<InstallationServiceMessage> observable,
            InstallationServiceMessage message) {
        hasChanged();
        notifyObservers(message);
    }
}
