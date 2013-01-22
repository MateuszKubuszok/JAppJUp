package com.autoupdater.client.installation.notifiers;

import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.client.utils.services.notifier.AbstractNotifier;

/**
 * Notifies about specific update's change.
 * 
 * @see com.autoupdater.client.installation.services.InstallationService
 * @see com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService
 */
public class UpdateNotifier extends
        AbstractNotifier<InstallationServiceMessage, EUpdateStatus, Update> {
    @Override
    public void update(ObservableService<EUpdateStatus> observable, EUpdateStatus message) {
        Update update = getAdditionalMessage(observable);
        hasChanged();
        notifyObservers(new InstallationServiceMessage((update != null ? update + ": " : "")
                + message));
    }
}
