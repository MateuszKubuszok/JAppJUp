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

/**
 * Interface common to all services used in the library.
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
 *            type of message that will be sent to Observers
 */
public interface IService<Message> {
    /**
     * Adds observer to set of the notified.
     * 
     * <p>
     * Since observer is added, it will be notified about all changes of
     * service.
     * </p>
     * 
     * @param observer
     *            added observer
     */
    public void addObserver(IObserver<Message> observer);

    /**
     * Removes observer from set of the notified.
     * 
     * <p>
     * Since observer is removed, it won't be notified about any changes of
     * service.
     * </p>
     * 
     * @param observer
     *            removed observer
     */
    public void removeObserver(IObserver<Message> observer);

    /**
     * Mark object as changes - it won't sent a notification about change
     * without settings that marker.
     * 
     * @see #notifyObservers(Object)
     */
    public void hasChanged();

    /**
     * Sends message to all Observers.
     * 
     * <p>
     * Has to be marked as changed first. After sending message it should remove
     * marker.
     * </p>
     * 
     * @see #hasChanged()
     * 
     * @param message
     *            sent message
     */
    public void notifyObservers(Message message);
}
