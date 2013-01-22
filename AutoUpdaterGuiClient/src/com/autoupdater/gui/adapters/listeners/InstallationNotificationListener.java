package com.autoupdater.gui.adapters.listeners;

import com.autoupdater.client.installation.EInstallationStatus;
import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;

public class InstallationNotificationListener implements IObserver<InstallationServiceMessage> {
    private final Gui2ClientAdapter adapter;
    private final AggregatedInstallationService aggregatedService;

    public InstallationNotificationListener(Gui2ClientAdapter adapter,
            AggregatedInstallationService aggregatedService) {
        this.adapter = adapter;
        this.aggregatedService = aggregatedService;
    }

    @Override
    public void update(ObservableService<InstallationServiceMessage> observable,
            InstallationServiceMessage message) {
        if (observable == aggregatedService.getNotifier()) {
            adapter.setStatusMessage("Installation: " + aggregatedService.getState());
        }

        if (aggregatedService.getState() == EInstallationStatus.INSTALLING) {
            int numberOfUpdatesBeingInstalled = aggregatedService.getUpdates().size();
            int numberOfUpdatesMarkedAsDone = 0;

            for (Update update : aggregatedService.getUpdates())
                if (update.getStatus().isUpdateAttemptFinished())
                    numberOfUpdatesMarkedAsDone++;

            adapter.setInstallationProgress(numberOfUpdatesBeingInstalled,
                    numberOfUpdatesMarkedAsDone);
        }
    }
}
