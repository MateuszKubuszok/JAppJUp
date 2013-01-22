package com.autoupdater.client.installation.aggregated.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.installation.EInstallationStatus;
import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.installation.notifiers.InstallationNotifier;
import com.autoupdater.client.installation.notifiers.UpdateNotifier;
import com.autoupdater.client.installation.services.InstallationService;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.aggregated.services.AbstractAggregatedService;

/**
 * Wrapper that makes InstallationService compatible with convention of other
 * AggregatedServices.
 * 
 * <p>
 * Result is aggregated as Set of Updates with set installation status.
 * </p>
 * 
 * @see com.autoupdater.client.installation.services.InstallationService
 * @see com.autoupdater.client.installation.notifiers.InstallationNotifier
 * @see com.autoupdater.client.installation.notifiers.UpdateNotifier
 */
public class AggregatedInstallationService
        extends
        AbstractAggregatedService<InstallationService, InstallationNotifier, InstallationServiceMessage, InstallationServiceMessage, Update> {
    private final SortedSet<Update> updates;
    private final Map<Update, UpdateNotifier> updateNotifiers;
    private final InstallationService installationService;
    private InstallationNotifier notifier;

    /**
     * Creates instance of installation service.
     * 
     * @param environmentData
     *            invironmentData instance
     */
    public AggregatedInstallationService(EnvironmentData environmentData) {
        updates = new TreeSet<Update>();
        updateNotifiers = new HashMap<Update, UpdateNotifier>();
        installationService = new InstallationService(environmentData, updates);
        installationService.addObserver(getNotifier());
        notifier = null;
    }

    /**
     * Starts installation.
     */
    public void start() {
        installationService.start();
    }

    /**
     * Adds update to list of installed Updates.
     * 
     * @param update
     *            update to install
     */
    public void addUpdate(Update update) {
        if (update != null) {
            updates.add(update);
            UpdateNotifier updateNotifier = new UpdateNotifier();
            update.addObserver(updateNotifier);
            updateNotifier.addObserver(getNotifier());
            updateNotifiers.put(update, updateNotifier);
        }
    }

    public SortedSet<Update> getUpdates() {
        return updates;
    }

    @Override
    public void addService(InstallationService service) {
        throw new RuntimeException();
    }

    @Override
    public void addService(InstallationService service, Update message) {
        throw new RuntimeException();
    }

    @Override
    public Set<InstallationService> getServices() {
        throw new RuntimeException();
    }

    @Override
    public InstallationNotifier getNotifier() {
        return notifier != null ? notifier : (notifier = createNotifier());
    }

    /**
     * Returns notifier for specified Update.
     * 
     * @param update
     *            Update for which notifier should be obtained
     * @return Update's notifier
     */
    public UpdateNotifier getUpdateNotifier(Update update) {
        return updateNotifiers.get(update);
    }

    /**
     * Returns set of Updates that are installed successfully.
     * 
     * @return set of Updates.
     */
    public SortedSet<Update> getResult() {
        for (Update update : updates)
            if (update != null && update.getStatus() == EUpdateStatus.INSTALLED
                    && update.getPackage() != null)
                update.getPackage().setVersionNumber(update.getVersionNumber());
        return updates;
    }

    /**
     * Returns current installation's status.
     * 
     * @return installation's status
     */
    public EInstallationStatus getState() {
        return installationService.getState();
    }

    @Override
    protected InstallationNotifier createNotifier() {
        InstallationNotifier notifier = new InstallationNotifier();
        installationService.addObserver(notifier);
        return notifier;
    }

    /**
     * Makes current thread wait for finishing of installation.
     */
    public void joinThread() {
        try {
            installationService.joinThread();
        } catch (InterruptedException e) {
        }
    }
}
