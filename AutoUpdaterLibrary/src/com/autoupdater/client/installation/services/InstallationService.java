package com.autoupdater.client.installation.services;

import java.util.SortedSet;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.installation.EInstallationStatus;
import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.installation.runnable.InstallationRunnable;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.google.common.base.Objects;

/**
 * Installation service that mediates with InstallationRunnable.
 * 
 * @see com.autoupdater.client.installation.runnable.InstallationRunnable
 */
public class InstallationService extends ObservableService<InstallationServiceMessage> implements
        IObserver<InstallationServiceMessage> {
    private final InstallationRunnable installationRunnable;
    private final Thread installationThread;

    /**
     * Creates instance of installation service, that will attempt to install
     * all required Updates.
     * 
     * @param environmentData
     *            environmentData instance
     * @param updates
     *            updates to install
     */
    public InstallationService(EnvironmentData environmentData, SortedSet<Update> updates) {
        installationRunnable = new InstallationRunnable(environmentData, updates);
        installationThread = new Thread(installationRunnable);
        installationRunnable.addObserver(this);
    }

    /**
     * Starts installation.
     */
    public void start() {
        installationThread.start();
    }

    /**
     * Makes current thread wait for installation finish (successful or not).
     * 
     * @throws InterruptedException
     *             thrown if thread was cancelled (should never happen)
     */
    public void joinThread() throws InterruptedException {
        installationThread.join();
    }

    /**
     * Returns current state of installation.
     * 
     * @return current state of installation
     */
    public EInstallationStatus getState() {
        return installationRunnable.getState();
    }

    @Override
    public void update(ObservableService<InstallationServiceMessage> observable,
            InstallationServiceMessage message) {
        if (Objects.equal(observable, installationRunnable)) {
            hasChanged();
            notifyObservers(message);
        }
    }
}
