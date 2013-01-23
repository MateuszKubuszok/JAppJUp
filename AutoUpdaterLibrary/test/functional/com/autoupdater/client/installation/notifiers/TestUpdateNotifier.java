package com.autoupdater.client.installation.notifiers;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;

public class TestUpdateNotifier {
    InstallationServiceMessage message;

    @Test
    public void testNotifier() {
        // given
        UpdateNotifier notifier = new UpdateNotifier();
        notifier.addObserver(new Reciever());
        Sender sender = new Sender();
        sender.addObserver(notifier);

        // when
        message = null;
        sender.hasChanged();
        sender.notifyObservers(EUpdateStatus.INSTALLED);

        // then
        assertThat(message).as(
                "UpdateNotifier should recieve EUpdateState and send InstallationServiceMessage")
                .isNotNull();
        assertThat(message.getMessage())
                .as("UpdateNotifier should recieve EUpdateState and send InstallationServiceMessage")
                .isNotNull().isEqualTo(EUpdateStatus.INSTALLED.getMessage());
    }

    private class Reciever implements IObserver<InstallationServiceMessage> {
        @Override
        public void update(ObservableService<InstallationServiceMessage> observable,
                InstallationServiceMessage recievedMessage) {
            message = recievedMessage;
        }
    }

    private class Sender extends ObservableService<EUpdateStatus> {
    }
}
