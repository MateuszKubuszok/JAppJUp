package com.autoupdater.client.environment;

import static com.google.common.collect.Sets.filter;

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Models;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.client.models.Update;
import com.google.common.base.Predicate;

/**
 * Filter that completes sets of models with those available on server but not
 * installed on computer, and removing those installed on computer that cannot
 * be updated with repositories.
 */
public class AvailabilityFilter {
    private final SortedSet<Program> installedPrograms;
    private final SortedSet<Program> registeredPrograms;

    /**
     * Creates filter which process models sets.
     * 
     * @param environmentData
     *            environment data
     */
    AvailabilityFilter(EnvironmentData environmentData) {
        installedPrograms = environmentData.getInstallationsData();
        registeredPrograms = getRegisteredPrograms(environmentData.getProgramsSettings(),
                environmentData.getInstallationsData());
    }

    /**
     * Creates filter which process models sets.
     * 
     * @param installedPrograms
     *            set of programs installed on platform
     * @param registeredPrograms
     *            set of all programs registered as installed on Client's
     *            platform
     */
    AvailabilityFilter(SortedSet<Program> installedPrograms, SortedSet<Program> registeredPrograms) {
        this.installedPrograms = installedPrograms;
        this.registeredPrograms = registeredPrograms;
    }

    /**
     * Returns set of those Programs that are both installed on Client's
     * platform and available on repositories. Packages are complemented with
     * Packages not installed locally but available on server.
     * 
     * @param programsAvailableOnServers
     * @return set of complimented Programs
     */
    public SortedSet<Program> findProgramsAvailableToInstallOrInstalledWithDefinedSettings(
            SortedSet<Program> programsAvailableOnServers) {
        SortedSet<Program> availableToInstall = new TreeSet<Program>();

        for (Program programAvailableToInstall : registeredPrograms)
            if (Models.contains(programsAvailableOnServers, programAvailableToInstall,
                    Models.EComparisionType.LOCAL_TO_SERVER)) {
                Program programAvailableOnServer = Models.findEqual(programsAvailableOnServers,
                        programAvailableToInstall, Models.EComparisionType.LOCAL_TO_SERVER);

                SortedSet<Package> unavailableOnServer = getPackagesUnavailableOnServer(
                        programAvailableToInstall.getPackages(),
                        programAvailableOnServer.getPackages());

                SortedSet<Package> notInstalledButAvailable = getPackagesAvailableButNotInstalled(
                        programAvailableToInstall.getPackages(),
                        programAvailableOnServer.getPackages());

                SortedSet<Package> packagesAvailableToInstall = new TreeSet<Package>(
                        programAvailableToInstall.getPackages());
                packagesAvailableToInstall.removeAll(unavailableOnServer);
                packagesAvailableToInstall.addAll(notInstalledButAvailable);

                programAvailableToInstall.setPackages(packagesAvailableToInstall);

                availableToInstall.add(programAvailableToInstall);
            }

        return availableToInstall;
    }

    /**
     * Returns all Packages that are available on repositories.
     * 
     * @param availableOnServer
     *            set of programs with Packages available on server
     * @return set of available packages
     */
    public SortedSet<Package> findPackagesAvailableToHaveTheirUpdateChecked(
            SortedSet<Program> availableOnServer) {
        SortedSet<Package> packages = new TreeSet<Package>();

        for (Program installed : installedPrograms) {
            Program available;
            if ((available = Models.findEqual(availableOnServer, installed,
                    Models.EComparisionType.LOCAL_TO_SERVER)) != null) {
                for (Package localPackage : installed.getPackages())
                    if (Models.contains(available.getPackages(), localPackage,
                            Models.EComparisionType.LOCAL_TO_SERVER))
                        packages.add(localPackage);
            }
        }

        return packages;
    }

    /**
     * Returns set of Programs installed locally.
     * 
     * @return set of installed programs
     */
    SortedSet<Program> getRegisteredPrograms() {
        return registeredPrograms;
    }

    /**
     * Filters leaves only Updated that are selected.
     * 
     * @param updates
     *            all Updates
     * @return filtered Updates
     */
    public static SortedSet<Update> filterUpdateSelection(SortedSet<Update> updates) {
        return filter(updates, new Predicate<Update>() {
            @Override
            public boolean apply(Update update) {
                return update.getStatus().isIntendedToBeChanged();
            }
        });
    }

    /**
     * Filters leaves only Updated that are successfully installed.
     * 
     * @param updates
     *            all Updates
     * @return filtered Updates
     */
    public static SortedSet<Update> filterUpdateInstalled(SortedSet<Update> updates) {
        return filter(updates, new Predicate<Update>() {
            @Override
            public boolean apply(Update update) {
                return update.getStatus() == EUpdateStatus.INSTALLED
                        || (update.getPackage() != null && update.getPackage().getVersionNumber()
                                .equals(update.getVersionNumber()));
            }
        });
    }

    /**
     * Filters leaves only Updates that are not installed.
     * 
     * @param updates
     *            all Updates
     * @return filtered Updates
     */
    public static SortedSet<Update> filterUpdateNotInstalled(SortedSet<Update> updates) {
        return filter(updates, new Predicate<Update>() {
            @Override
            public boolean apply(Update update) {
                return update.getStatus() != EUpdateStatus.INSTALLED
                        && (update.getPackage() == null || !update.getPackage().getVersionNumber()
                                .equals(update.getVersionNumber()));
            }
        });
    }

    /**
     * Returns set of Packages unavailable on server but installed locally.
     * 
     * @param installedPackages
     *            set on locally installed packages
     * @param packagesAvailableOnServer
     *            set of packages available on server
     * @return difference of installedPackages and packagesAvailableOnServer
     */
    static SortedSet<Package> getPackagesUnavailableOnServer(SortedSet<Package> installedPackages,
            SortedSet<Package> packagesAvailableOnServer) {
        SortedSet<Package> unavailableOnServer = new TreeSet<Package>();

        for (Package testedPackage : installedPackages)
            if (!Models.contains(packagesAvailableOnServer, testedPackage,
                    Models.EComparisionType.LOCAL_TO_SERVER))
                unavailableOnServer.add(testedPackage);

        return unavailableOnServer;
    }

    /**
     * Returns set of Packages available on server but not installed locally.
     * 
     * @param installedPackages
     *            set on locally installed packages
     * @param packagesAvailableOnServer
     *            set of packages available on server
     * @return difference of packagesAvailableOnServer and installedPackages
     */
    static SortedSet<Package> getPackagesAvailableButNotInstalled(
            SortedSet<Package> installedPackages, SortedSet<Package> packagesAvailableOnServer) {
        SortedSet<Package> notInstalledButAvailable = new TreeSet<Package>();

        for (Package testedPackage : packagesAvailableOnServer)
            if (!Models.contains(installedPackages, testedPackage,
                    Models.EComparisionType.LOCAL_TO_SERVER))
                notInstalledButAvailable.add(testedPackage);

        return notInstalledButAvailable;
    }

    /**
     * Obtains list of installed Programs from ProgramsSettings.
     * 
     * @param programsSettings
     *            local ProgramsSettings
     * @param installationData
     *            local installationData
     * @return set of installed Programs
     */
    static SortedSet<Program> getRegisteredPrograms(SortedSet<ProgramSettings> programsSettings,
            SortedSet<Program> installationData) {
        SortedSet<Program> registeredPrograms = new TreeSet<Program>();

        for (ProgramSettings programSettings : programsSettings) {
            Program program = programSettings.findProgramForSettings(installationData);

            if (program == null)
                program = ProgramBuilder.builder().setName(programSettings.getProgramName())
                        .setPathToProgramDirectory(programSettings.getPathToProgramDirectory())
                        .setServerAddress(programSettings.getServerAddress()).build();

            registeredPrograms.add(program);
        }

        return registeredPrograms;
    }
}
