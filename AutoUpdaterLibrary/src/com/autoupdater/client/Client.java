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
package com.autoupdater.client;

import static com.autoupdater.client.download.ConnectionConfiguration.DOWNLOAD_DIRECTORY;
import static com.autoupdater.commons.installer.configuration.InstallerConfiguration.BACKUP_DIRECTORY;
import static net.jsdpu.logger.Logger.getLogger;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;

import net.jsdpu.logger.Logger;

import com.autoupdater.client.download.DownloadServiceFactory;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.environment.AvailabilityFilter;
import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.EnvironmentDataManager;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.installation.InstallationServiceFactory;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;

/**
 * Class used for creating services that will obtain data from server and
 * install updates.
 * 
 * <p>
 * Basic usage of each service is described in their respective factory method.
 * </p>
 * 
 * <p>
 * Observation of each service's results is possible via its respective
 * Notifier.
 * </p>
 * 
 * <p>
 * Current state of each Updates installation can be done by observation of said
 * Update.
 * </p>
 * 
 * @see com.autoupdater.client.download.DownloadServiceFactory
 * @see com.autoupdater.client.installation.InstallationServiceFactory
 * @see com.autoupdater.client.utils.services.IObserver
 * @see com.autoupdater.client.utils.services.notifier.AbstractNotifier
 */
public class Client {
    private static final Logger logger = getLogger(Client.class);

    private final EnvironmentData environmentData;
    private final DownloadServiceFactory connectionServiceManager;
    private final InstallationServiceFactory installationServiceManager;

    /**
     * Initiates instance of Client with default EnvironmentData.
     * 
     * <p>
     * To be precise, it initiates Client with EnvironmentData obtained from
     * EnvironmentDataManager initiated with default EnvironmentContext.
     * </p>
     * 
     * @see com.autoupdater.client.environment.EnvironmentData
     * @see com.autoupdater.client.environment.EnvironmentDataManager
     * @see com.autoupdater.client.environment.EnvironmentContext
     * 
     * @throws ClientEnvironmentException
     *             thrown, if no ClientSettings were defined in default location
     * @throws IOException
     *             thrown if file with settings/installation data cannot be read
     */
    public Client() throws ClientEnvironmentException, IOException {
        this(new EnvironmentDataManager().getEnvironmentData());
    }

    /**
     * Initiates Client with the given EnvironmentData.
     * 
     * <p>
     * EnvironmentData should be obtained via use of EnvironmentDataManager.
     * </p>
     * 
     * @see com.autoupdater.client.environment.EnvironmentData
     * @see com.autoupdater.client.environment.EnvironmentDataManager
     * 
     * @param environmentData
     *            EnvironmentData that should be used in this Client instance
     */
    public Client(EnvironmentData environmentData) {
        logger.info("Initiates Client");
        this.environmentData = environmentData;
        connectionServiceManager = new DownloadServiceFactory(environmentData);
        installationServiceManager = new InstallationServiceFactory(environmentData);
    }

    /**
     * Saves changes in ProgramsSettings.
     * 
     * @see com.autoupdater.client.environment.settings.ProgramSettings
     * @see com.autoupdater.client.environment.EnvironmentDataManager
     * 
     * @throws IOException
     *             thrown if some settings couldn't be saved
     * @throws ClientEnvironmentException
     *             thrown if EnvironmentDataManager wasn't set in
     *             EvironmentsData constructor, yet setting were tried to be
     *             saved (happens when EnvironmentData isn't initiated by
     *             EnvironmentDataManager)
     */
    public void saveChanges() throws ClientEnvironmentException, IOException {
        environmentData.save();
    }

    /**
     * Returns instance of the AvailabilityFilter.
     * 
     * <p>
     * It can be used to filter results obtained from repository querying.
     * </p>
     * 
     * @return AvailabilityFilter
     */
    public AvailabilityFilter getAvailabilityFilter() {
        return environmentData.getAvailabilityFilter();
    }

    /**
     * Returns the set of all currently defined ProgramSettings.
     * 
     * @see com.autoupdater.client.environment.settings.ProgramSettings
     * 
     * @return SortedSet of ProgramSettings
     */
    public SortedSet<ProgramSettings> getProgramsSettings() {
        return environmentData.getProgramsSettings();
    }

    /**
     * Returns the set of all currently installed programs.
     * 
     * @see com.autoupdater.client.models.Program
     * 
     * @return SortedSet of Programs.
     */
    public SortedSet<Program> getInstalledPrograms() {
        return environmentData.getInstallationsData();
    }

    /**
     * Returns instance of PackagesInfoAggregatedDownloadService that can be
     * used for obtaining set of programs available on repositories.
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
     * @return service prepared to use
     * @throws IOException
     *             thrown when IO error occurs while creating connection
     */
    public PackagesInfoAggregatedDownloadService createPackagesInfoAggregatedDownloadService()
            throws IOException {
        return connectionServiceManager.createPackagesInfoAggregatedDownloadService();
    }

    /**
     * Returns instance of UpdateInfoAggregatedDownloadService that can be used
     * for obtaining set of updates available for chosen packages.
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
     *            Packages for which Updates should be obtained
     * @return service prepared to use
     * @throws IOException
     *             thrown when IO error occurs while creating connection
     * @throws ProgramSettingsNotFoundException
     *             thrown when ProgramSettings of some program cannot be found
     */
    public UpdateInfoAggregatedDownloadService createUpdateInfoAggregatedDownloadService(
            SortedSet<Package> selectedPackages) throws ProgramSettingsNotFoundException,
            IOException {
        return connectionServiceManager.createUpdateInfoAggregatedDownloadService(selectedPackages);
    }

    /**
     * Returns instance of ChangelogInfoAggregatedDownloadService that can be
     * used for obtaining changelogs for chosen packages.
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
     *            Packages for which changelogs should be obtained
     * @return service prepared to use
     * @throws IOException
     *             thrown when IO error occurs while creating connection
     * @throws ProgramSettingsNotFoundException
     *             thrown when ProgramSettings of some program cannot be found
     */
    public ChangelogInfoAggregatedDownloadService createChangelogInfoAggregatedDownloadService(
            SortedSet<Package> selectedPackages) throws ProgramSettingsNotFoundException,
            IOException {
        return connectionServiceManager
                .createChangelogInfoAggregatedDownloadService(selectedPackages);
    }

    /**
     * Returns instance of BugsInfoAggregatedDownloadService that can be used
     * for obtaining known bugs for chosen programs.
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
     *            Programs for which Bugs should be obtained
     * @return service prepared to use
     * @throws IOException
     *             thrown when IO error occurs while creating connection
     * @throws ProgramSettingsNotFoundException
     *             thrown when ProgramSettings of some program cannot be found
     */
    public BugsInfoAggregatedDownloadService createBugsInfoAggregatedDownloadService(
            SortedSet<Program> selectedPrograms) throws ProgramSettingsNotFoundException,
            IOException {
        return connectionServiceManager.createBugsInfoAggregatedDownloadService(selectedPrograms);
    }

    /**
     * Returns instance of BugsInfoAggregatedDownloadService that can be used
     * for obtaining known bugs for chosen programs.
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
     *            Updates for which Files should be obtained
     * @return service prepared to use
     * @throws IOException
     *             thrown when IO error occurs while creating connection
     * @throws ProgramSettingsNotFoundException
     *             thrown when ProgramSettings of some program cannot be found
     */
    public FileAggregatedDownloadService createFileAggregatedDownloadService(
            SortedSet<Update> requestedUpdates) throws ProgramSettingsNotFoundException,
            IOException {
        return connectionServiceManager.createFileAggregatedDownloadService(requestedUpdates);
    }

    /**
     * Returns instance of AggregatedInstallationService that can be used for
     * installing Updates.
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
     * @param updatesToInstall
     *            Updates that should be installed
     * @return service prepared to use
     */
    public AggregatedInstallationService createInstallationAggregatedService(
            SortedSet<Update> updatesToInstall) {
        return installationServiceManager.createInstallationAggregatedService(updatesToInstall);
    }

    /**
     * Cleans up backup directory.
     */
    public void cleanBackup() {
        logger.info("Cleans up backup directory");
        delete(new File(BACKUP_DIRECTORY));
        logger.info("Backup directory cleaned");
    }

    /**
     * Cleans up download directory.
     */
    public void cleanDownloaded() {
        logger.info("Cleans up downloads directory");
        delete(new File(DOWNLOAD_DIRECTORY));
        logger.info("Downloads directory cleaned");
    }

    /**
     * Cleans up all temporally files.
     * 
     * @see com.autoupdater.client.Client#cleanBackup()
     * @see com.autoupdater.client.Client#cleanDownloaded()
     */
    public void cleanTemp() {
        cleanBackup();
        cleanDownloaded();
    }

    /**
     * Deletes file or deletes directory recursively.
     * 
     * @param file
     *            file/directory to delete
     */
    private void delete(File file) {
        if (file == null || !file.exists())
            return;
        if (file.isDirectory())
            for (File child : file.listFiles())
                delete(child);
        if (file.isDirectory() || file.isFile())
            file.delete();
    }
}
