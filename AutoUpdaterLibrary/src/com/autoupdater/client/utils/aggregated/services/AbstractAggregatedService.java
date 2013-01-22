package com.autoupdater.client.utils.aggregated.services;

import java.util.HashSet;
import java.util.Set;

import com.autoupdater.client.utils.services.IService;
import com.autoupdater.client.utils.services.notifier.AbstractNotifier;

/**
 * Superclass of all AggregatedServices - ensures that they have notifier, and
 * handles settings additional messages.
 * 
 * @param <Service>
 *            type of Service that will be aggregated - must be subclass of
 *            ServiceInterface&lt;RecievedMessage&gt;
 * @param <Notifier>
 *            type of Notifier returned by getNotifer() - must be subclass of
 *            AbstractNotifier&lt;SentMessage, RecievedMessage,
 *            AdditionalMessage&gt;
 * @param <SentMessage>
 *            type of messages sent to Observers
 * @param <RecievedMessage>
 *            type of messages received from Observed
 * @param <AdditionalMessage>
 *            type of additional messages
 */
public abstract class AbstractAggregatedService<Service extends IService<RecievedMessage>, Notifier extends AbstractNotifier<SentMessage, RecievedMessage, AdditionalMessage>, SentMessage, RecievedMessage, AdditionalMessage> {
    private final Set<Service> services;
    private Notifier notifier;

    public AbstractAggregatedService() {
        services = new HashSet<Service>();
    }

    /**
     * Adds service to set.
     * 
     * @param service
     *            added Service
     */
    public void addService(Service service) {
        if (service != null) {
            services.add(service);
            service.addObserver(getNotifier());
        }
    }

    /**
     * Adds service to set and binds message to it.
     * 
     * @param service
     *            added Service
     * @param additionalMessage
     *            bound additional message
     */
    public void addService(Service service, AdditionalMessage additionalMessage) {
        if (service != null) {
            services.add(service);
            service.addObserver(getNotifier());
            setAdditionalMessage(service, additionalMessage);
        }
    }

    /**
     * Returns all services that were passed into AggregatedService.
     * 
     * @return set of services
     */
    public Set<Service> getServices() {
        return services;
    }

    /**
     * Returns notifier for this AggregatedService.
     * 
     * <p>
     * For each Aggregated service there is only one instance of Notifier.
     * </p>
     * 
     * @return instance of notifier for Service
     */
    public Notifier getNotifier() {
        if (notifier == null)
            notifier = createNotifier();
        return notifier;
    }

    /**
     * Obtains additional message from Notifier.
     * 
     * @see AbstractNotifier#getAdditionalMessage(IService)
     * 
     * @param service
     *            Service for which message should be returned
     * @return additional message bound to service, or null if none set
     */
    public AdditionalMessage getAdditionalMessage(Service service) {
        return getNotifier().getAdditionalMessage(service);
    }

    /**
     * Sets additional message for Notifier.
     * 
     * @see AbstractNotifier#setAdditionalMessage(IService, Object)
     * 
     * @param service
     *            Service for which message should be bound
     * @param additionalMessage
     *            message to be bound
     */
    public void setAdditionalMessage(Service service, AdditionalMessage additionalMessage) {
        getNotifier().setAdditionalMessage(service, additionalMessage);
    }

    /**
     * Returns Service by AdditionalMessage bound to it.
     * 
     * @param additionalMessage
     *            AdditionalMessage
     * @return Service bound to additionalMessage
     */
    @SuppressWarnings("unchecked")
    public Service getService(AdditionalMessage additionalMessage) {
        return (Service) getNotifier().getMessageSource(additionalMessage);
    }

    /**
     * Returns new notifier instance, that will be used in service.
     * 
     * @return new Notifier instance
     */
    protected abstract Notifier createNotifier();
}
