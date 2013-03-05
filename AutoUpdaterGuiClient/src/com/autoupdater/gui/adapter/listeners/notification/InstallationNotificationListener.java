package com.autoupdater.gui.adapter.listeners.notification;

import com.autoupdater.client.installation.EInstallationStatus;
import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.client.window.EInfoTarget;

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
            if (message.isInterruptedByError())
                adapter.windowOperations().reportError("Installation failed", message.getMessage(),
                        EInfoTarget.ALL);
            else
                adapter.windowOperations().reportInfo("Installation",
                        "Installation: " + aggregatedService.getState(), EInfoTarget.ALL);
        }

        if (aggregatedService.getState() == EInstallationStatus.INSTALLING_UPDATES) {
            int numberOfUpdatesBeingInstalled = aggregatedService.getUpdates().size();
            int numberOfUpdatesMarkedAsDone = 0;

            for (Update update : aggregatedService.getUpdates())
                if (update.getStatus().isUpdateAttemptFinished())
                    numberOfUpdatesMarkedAsDone++;

            adapter.windowOperations().setInstallationProgress(numberOfUpdatesBeingInstalled,
                    numberOfUpdatesMarkedAsDone);
        }
    }
}
