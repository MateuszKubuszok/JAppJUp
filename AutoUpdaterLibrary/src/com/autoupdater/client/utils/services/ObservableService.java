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
package com.autoupdater.client.utils.services;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of ServiceInterface, used as superclass for Services.
 * 
 * <p>
 * Replaces default Java's Observable - makes use of generics to specify type of
 * passed messages.
 * </p>
 * 
 * @see com.autoupdater.client.utils.services.IService
 * @see com.autoupdater.client.installation.services.InstallationService
 * @see com.autoupdater.client.download.services.AbstractDownloadService
 * @see com.autoupdater.client.download.services.PackagesInfoDownloadService
 * @see com.autoupdater.client.download.services.UpdateInfoDownloadService
 * @see com.autoupdater.client.download.services.ChangelogInfoDownloadService
 * @see com.autoupdater.client.download.services.FileDownloadService
 * @see com.autoupdater.client.download.services.FileDownloadService
 * 
 * @param <Message>
 *            type of messages sent by Observable
 */
public class ObservableService<Message> implements IService<Message> {
    private boolean hasChanged;
    private final Set<IObserver<Message>> observers;

    public ObservableService() {
        hasChanged = false;
        observers = new HashSet<IObserver<Message>>();
    }

    @Override
    public void addObserver(IObserver<Message> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver<Message> observer) {
        observers.remove(observer);
    }

    @Override
    public synchronized void hasChanged() {
        hasChanged = true;
    }

    @Override
    public synchronized void notifyObservers(Message message) {
        if (hasChanged) {
            for (IObserver<Message> observer : observers)
                observer.update(this, message);
            hasChanged = false;
        }
    }
}
