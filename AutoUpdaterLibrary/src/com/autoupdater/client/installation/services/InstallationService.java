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
package com.autoupdater.client.installation.services;

import java.util.SortedSet;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.installation.EInstallationStatus;
import com.autoupdater.client.installation.InstallationServiceMessage;
import com.autoupdater.client.installation.runnable.InstallationRunnable;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.executions.ExecutionWithErrors;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.google.common.base.Objects;

/**
 * Installation service that mediates with InstallationRunnable.
 * 
 * @see com.autoupdater.client.installation.runnable.InstallationRunnable
 */
public class InstallationService extends ObservableService<InstallationServiceMessage> implements
        IObserver<InstallationServiceMessage>, ExecutionWithErrors {
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

    @Override
    public Throwable getThrownException() {
        return installationRunnable.getThrownException();
    }

    @Override
    public void setThrownException(Throwable throwable) {
        installationRunnable.setThrownException(throwable);
    }

    @Override
    public void throwExceptionIfErrorOccured() throws Throwable {
        installationRunnable.throwExceptionIfErrorOccured();
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
