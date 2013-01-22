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
