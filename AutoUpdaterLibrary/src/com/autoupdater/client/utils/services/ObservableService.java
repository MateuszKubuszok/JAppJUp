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
