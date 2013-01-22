package com.autoupdater.client.utils.services;

/**
 * Interface describing Services' Observable - replaces Java's default
 * Observable
 * 
 * <p>
 * Makes use of generics to specify type of received messages.
 * </p>
 * 
 * @param <Message>
 *            type of messages sent by Observable
 */
public interface IObserver<Message> {
    /**
     * Receives message sent by ObservableService.
     * 
     * @param observable
     *            observed service
     * @param message
     *            message describing change
     */
    public void update(ObservableService<Message> observable, Message message);
}
