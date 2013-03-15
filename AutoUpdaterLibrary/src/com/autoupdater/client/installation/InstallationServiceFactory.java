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
package com.autoupdater.client.installation;

import java.util.SortedSet;

import com.autoupdater.client.environment.AvailabilityFilter;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.Update;

/**
 * Factory that creates InstallationAggregatedServices for a Client with
 * EnvironmentData as source of information.
 * 
 * <p>
 * Services are returned ready to run. Their states can be observed through
 * InstallationNotifier. State of specific Update can be observed through
 * UpdateNotifier.
 * </p>
 * 
 * <p>
 * It is possible to obtain set of all Updates that are installed at the moment.
 * </p>
 * 
 * @see EnvironmentData
 * @see com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService
 */
public class InstallationServiceFactory {
    private final EnvironmentData environmentData;

    /**
     * Creates factory which spawns services that installs Updates.
     * 
     * @param environmentData
     *            environment data
     */
    public InstallationServiceFactory(EnvironmentData environmentData) {
        this.environmentData = environmentData;
    }

    /**
     * Creates new InstallationAggregatedService.
     * 
     * <p>
     * Example of use:
     * 
     * <pre>
     * // Change state of each update that need to be updated to selected:
     * // update.setState(EUpdateStatus.SELECTED);
     * //
     * // It was done, this way to allow choosing Updates to install
     * // without need to filter them by programmer - set of state can be
     * // done directly via user interface.
     * 
     * AggregatedInstallationService aggregatedService = client
     *         .createAggregatedInstallationService(allUpdates);
     * 
     * aggregatedService.start();
     * aggregatedService.joinThread();
     * 
     * aggregatedService.getResult();
     * // Files will be put inside their respective instances of Update
     * </pre>
     * 
     * </p>
     * 
     * @param updates
     *            Updates to install
     * @return installation service ready to run
     */
    public AggregatedInstallationService createInstallationAggregatedService(
            SortedSet<Update> updates) {
        AggregatedInstallationService aggregatedService = new AggregatedInstallationService(
                environmentData);

        for (Update update : AvailabilityFilter.filterUpdateSelection(updates))
            aggregatedService.addUpdate(update);

        return aggregatedService;
    }
}
