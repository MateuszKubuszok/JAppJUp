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
package com.autoupdater.client.download;

import static com.autoupdater.client.download.ConnectionConfiguration.DOWNLOAD_DIRECTORY;
import static com.autoupdater.client.environment.AvailabilityFilter.filterUpdateSelection;
import static java.io.File.separator;
import static net.jsdpu.logger.Logger.getLogger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.download.connections.ConnectionFactory;
import com.autoupdater.client.download.services.BugsInfoDownloadService;
import com.autoupdater.client.download.services.ChangelogInfoDownloadService;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.download.services.PackagesInfoDownloadService;
import com.autoupdater.client.download.services.UpdateInfoDownloadService;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Models;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;

/**
 * Factory that creates AggregatedDownloadServices for a Client with
 * EnvironmentData as source of information.
 * 
 * <p>
 * Services are returned ready to run. Their states can be observed through
 * AggregatedNotifiers. When aggregated service state change to PROCESSED, all
 * of its services is finished - either as PROCESSED, FAILED or CANCELLED.
 * </p>
 * 
 * <p>
 * It is possible to try to obtain aggregated result. If all services finished
 * successfully it will be returned. Otherwise DownloadResultException will be
 * thrown.
 * </p>
 * 
 * <p>
 * If some services failed and some finished successfully, it is still possible
 * to manually iterate over the services and obtain those results.
 * </p>
 * 
 * @see EnvironmentData
 * @see com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService
 * @see com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService
 * @see com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService
 * @see com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService
 */
public class DownloadServiceFactory {
    private static final Logger logger = getLogger(DownloadServiceFactory.class);

    private final EnvironmentData environmentData;
    private final ConnectionFactory connectionFactory;

    /**
     * Creates factory which spawns services that download several
     * documents/files at once.
     * 
     * @param environmentData
     *            environment data
     */
    public DownloadServiceFactory(EnvironmentData environmentData) {
        this(environmentData, new ConnectionFactory(environmentData.getClientSettings()));
    }

    /**
     * Creates factory which spawns services that download several
     * documents/files at once.
     * 
     * @param environmentData
     *            environment data
     * @param connectionFactory
     *            factory that creates connections for services
     */
    public DownloadServiceFactory(EnvironmentData environmentData,
            ConnectionFactory connectionFactory) {
        logger.debug("Initiated DownloadServiceFactory");
        this.environmentData = environmentData;
        this.connectionFactory = connectionFactory;
    }

    /**
     * Creates aggregated service used for downloading all packages available on
     * servers.
     * 
     * <p>
     * Example of use:
     * 
     * <pre>
     * PackagesInfoAggregatedDownloadService aggregatedService = client
     *         .createPackagesInfoAggregatedDownloadService();
     * 
     * aggregatedService.start();
     * aggregatedService.joinThread();
     * 
     * SortedSet&lt;Program&gt; availableOnServer = aggregatedService.getResult();
     * </pre>
     * 
     * </p>
     * 
     * @return aggregates service ready to run
     * @throws IOException
     *             thrown when IO error occurs while creating connection
     */
    public PackagesInfoAggregatedDownloadService createPackagesInfoAggregatedDownloadService()
            throws IOException {
        logger.debug("Creates PackagesInfoAggregatedDownloadService");
        PackagesInfoAggregatedDownloadService aggregatedService = new PackagesInfoAggregatedDownloadService();

        for (ProgramSettings programSettings : environmentData.getProgramsSettingsForEachServer()) {
            HttpURLConnection connection = connectionFactory.getPerProgramConnectionFactory(
                    programSettings).createPackagesInfoConnection();
            PackagesInfoDownloadService service = new PackagesInfoDownloadService(connection);

            aggregatedService.addService(service, programSettings);
        }

        aggregatedService.setInstalledPrograms(environmentData.getInstallationsData());

        return aggregatedService;
    }

    /**
     * Creates aggregated service used for downloading all update information
     * for selected packages.
     * 
     * <p>
     * Example of use:
     * 
     * <pre>
     * UpdateInfoAggregatedDownloadService aggregatedService = client
     *         .createUpdateInfoAggregatedDownloadService(selectedPackages);
     * 
     * aggregatedService.start();
     * aggregatedService.joinThread();
     * 
     * aggregatedService.getResult();
     * // Updates will be put inside their respective instances of Package
     * </pre>
     * 
     * </p>
     * 
     * @param selectedPackages
     *            packages for which update information should be downloaded
     * @return aggregates service ready to run
     * @throws IOException
     *             thrown when connection cannot be created
     * @throws ProgramSettingsNotFoundException
     *             thrown when ProgramSettings of some program cannot be found
     */
    public UpdateInfoAggregatedDownloadService createUpdateInfoAggregatedDownloadService(
            SortedSet<Package> selectedPackages) throws IOException,
            ProgramSettingsNotFoundException {
        logger.debug("Creates UpdateInfoAggregatedDownloadService");
        UpdateInfoAggregatedDownloadService aggregatedService = new UpdateInfoAggregatedDownloadService();

        for (Package _package : selectedPackages) {
            ProgramSettings programSettings = environmentData
                    .findProgramSettingsForPackage(_package);

            HttpURLConnection connection = connectionFactory.getPerProgramConnectionFactory(
                    programSettings).createUpdateInfoConnection(_package.getID());
            UpdateInfoDownloadService service = new UpdateInfoDownloadService(connection);

            aggregatedService.addService(service, _package);
        }

        return aggregatedService;
    }

    /**
     * Creates aggregated service used for downloading all changelogs for
     * selected packages.
     * 
     * <p>
     * Example of use:
     * 
     * <pre>
     * ChangelogInfoAggregatedDownloadService aggregatedService = client
     *         .createChangelogInfoAggregatedDownloadService(selectedPackages);
     * 
     * aggregatedService.start();
     * aggregatedService.joinThread();
     * 
     * aggregatedService.getResult();
     * // Changelogs will be put inside their respective instances of Package
     * </pre>
     * 
     * </p>
     * 
     * @param selectedPackages
     *            packages for which changelogs should be downloaded
     * @return aggregates service ready to run
     * @throws IOException
     *             thrown when connection cannot be created
     * @throws ProgramSettingsNotFoundException
     *             thrown when ProgramSettings of some program cannot be found
     */
    public ChangelogInfoAggregatedDownloadService createChangelogInfoAggregatedDownloadService(
            SortedSet<Package> selectedPackages) throws ProgramSettingsNotFoundException,
            IOException {
        logger.debug("Creates ChangelogInfoAggregatedDownloadService");
        ChangelogInfoAggregatedDownloadService aggregatedService = new ChangelogInfoAggregatedDownloadService();

        for (Package _package : selectedPackages) {
            ProgramSettings programSettings = environmentData
                    .findProgramSettingsForPackage(_package);

            HttpURLConnection connection = connectionFactory.getPerProgramConnectionFactory(
                    programSettings).createChangelogInfoConnection(_package.getID());
            ChangelogInfoDownloadService service = new ChangelogInfoDownloadService(connection);

            aggregatedService.addService(service, _package);
        }

        return aggregatedService;
    }

    /**
     * Creates aggregated service used for downloading all bugs for selected
     * programs.
     * 
     * <p>
     * Example of use:
     * 
     * <pre>
     * BugsInfoAggregatedDownloadService aggregatedService = client
     *         .createBugsInfoAggregatedDownloadService(selectedPrograms);
     * 
     * aggregatedService.start();
     * aggregatedService.joinThread();
     * 
     * aggregatedService.getResult();
     * // Bugs will be put inside their respective instances of Program
     * </pre>
     * 
     * </p>
     * 
     * @param selectedPrograms
     *            packages for which bugs sets should be downloaded
     * @return aggregates service ready to run
     * @throws IOException
     *             thrown when connection cannot be created
     * @throws ProgramSettingsNotFoundException
     *             thrown when ProgramSettings of some program cannot be found
     */
    public BugsInfoAggregatedDownloadService createBugsInfoAggregatedDownloadService(
            SortedSet<Program> selectedPrograms) throws ProgramSettingsNotFoundException,
            IOException {
        logger.debug("Creates BugsInfoAggregatedDownloadService");
        BugsInfoAggregatedDownloadService aggregatedService = new BugsInfoAggregatedDownloadService();

        for (Program program : selectedPrograms) {
            ProgramSettings programSettings = environmentData
                    .findProgramSettingsForProgram(program);

            HttpURLConnection connection = connectionFactory.getPerProgramConnectionFactory(
                    programSettings).createBugsInfoConnection(program.getName());
            BugsInfoDownloadService service = new BugsInfoDownloadService(connection);

            aggregatedService.addService(service, program);
        }

        return aggregatedService;
    }

    /**
     * Creates aggregated service used for downloading all update files for
     * selected updates.
     * 
     * <p>
     * Example of use:
     * 
     * <pre>
     * FileAggregatedDownloadService aggregatedService = client
     *         .createFileAggregatedDownloadService(selectedPrograms);
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
     * @param requestedUpdates
     *            updates for which files should be downloaded
     * @return aggregates service ready to run
     * @throws IOException
     *             thrown when connection cannot be created
     * @throws ProgramSettingsNotFoundException
     *             thrown when ProgramSettings of some program cannot be found
     */
    public FileAggregatedDownloadService createFileAggregatedDownloadService(
            SortedSet<Update> requestedUpdates) throws ProgramSettingsNotFoundException,
            IOException {
        logger.debug("Creates FileAggregatedDownloadService");
        FileAggregatedDownloadService aggregatedService = new FileAggregatedDownloadService();

        SortedSet<Update> downloadedUpdates = (SortedSet<Update>) Models.addAll(
                new TreeSet<Update>(), filterUpdateSelection(requestedUpdates),
                Models.EComparisionType.LOCAL_TO_SERVER);

        for (Update update : downloadedUpdates) {
            ProgramSettings programSettings = environmentData.findProgramSettingsForUpdate(update);

            String downloadDestinationPath = DOWNLOAD_DIRECTORY + separator
                    + programSettings.getServerAddress().hashCode() + separator + update.getID();

            HttpURLConnection connection = connectionFactory.getPerProgramConnectionFactory(
                    programSettings).createFileConnection(update.getID());
            FileDownloadService service = new FileDownloadService(connection,
                    downloadDestinationPath);

            aggregatedService.addService(service, update);
        }
        aggregatedService.setAllUpdates(requestedUpdates);

        return aggregatedService;
    }
}
