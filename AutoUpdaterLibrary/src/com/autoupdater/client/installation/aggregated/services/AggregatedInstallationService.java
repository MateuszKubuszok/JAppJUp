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
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.aggregated.services.AbstractAggregatedService;
import com.autoupdater.client.utils.executions.ExecutionWithErrors;

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
        AbstractAggregatedService<InstallationService, InstallationNotifier, InstallationServiceMessage, InstallationServiceMessage, Update>
        implements ExecutionWithErrors {
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
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public void addService(InstallationService service, Update message) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public Set<InstallationService> getServices() {
        throw new UnsupportedOperationException("Method is not supported");
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
    public Throwable getThrownException() {
        return installationService.getThrownException();
    }

    @Override
    public void setThrownException(Throwable throwable) {
        installationService.setThrownException(throwable);
    }

    @Override
    public void throwExceptionIfErrorOccured() throws Throwable {
        installationService.throwExceptionIfErrorOccured();
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
