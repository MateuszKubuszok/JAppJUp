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
