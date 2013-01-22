package com.autoupdater.client.environment;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.environment.settings.ProgramSettingsBuilder;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.PackageBuilder;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.ProgramBuilder;

public class TestAvailabilityFilter {
    @Test
    public void testAvailabilityFilter() {
        // given
        SortedSet<ProgramSettings> programsSettings = getProgramsSettings();
        SortedSet<Program> installationData = getInstallationData();
        EnvironmentData environmentData = new EnvironmentData(null, programsSettings);
        environmentData.setInstallationsData(installationData);

        // when
        SortedSet<Program> registeredPrograms = new AvailabilityFilter(environmentData)
                .getRegisteredPrograms();

        // then
        assertThat(registeredPrograms)
                .as("Constructor should properly obtain registered programs")
                .isNotNull()
                .hasSize(1)
                .isEqualTo(
                        AvailabilityFilter
                                .getRegisteredPrograms(programsSettings, installationData));
    }

    @Test
    public void testGetPackagesUnavailableOnServer() {
        // given
        SortedSet<Package> installedPackages = getInstalledPackages();
        SortedSet<Package> availablePackages = getAvailablePackages();

        // when
        SortedSet<Package> unavailableOnServer = AvailabilityFilter.getPackagesUnavailableOnServer(
                installedPackages, availablePackages);

        // then
        assertThat(unavailableOnServer)
                .as("getPackagesUnavailableOnServer(SortedSet<Package>,SortedSet<Package>) should leave only packages installed but not available on server")
                .isNotNull().hasSize(2).contains(installedPackages.first());
    }

    @Test
    public void testGetPackagesAvailableButNotInstalled() {
        // given
        SortedSet<Package> installedPackages = getInstalledPackages();
        SortedSet<Package> availablePackages = getAvailablePackages();

        // when
        SortedSet<Package> availableButNotInstalled = AvailabilityFilter
                .getPackagesAvailableButNotInstalled(installedPackages, availablePackages);

        // then
        assertThat(availableButNotInstalled)
                .as("getPackagesAvailableButNotInstalled(SortedSet<Package>,SortedSet<Package>) should leave only packages available on server but not installed")
                .isNotNull().hasSize(3).contains(availablePackages.last());
    }

    @Test
    public void testGetRegisteredPrograms() {
        // given
        SortedSet<ProgramSettings> programsSettings = getProgramsSettings();
        SortedSet<Program> installationData = getInstallationData();

        // when
        SortedSet<Program> registeredPrograms = AvailabilityFilter.getRegisteredPrograms(
                programsSettings, installationData);

        // then
        assertThat(registeredPrograms)
                .as("getRegisteredPrograms(SortedSet<ProgramSettings>,SortedSet<Program>) should return 1 Program for each ProgramSettings")
                .isNotNull().hasSize(1).contains(installationData.last());
    }

    @Test
    public void testFindAvailableToInstallForExistingPrograms() {
        // given
        SortedSet<ProgramSettings> programsSettings = getProgramsSettings();
        SortedSet<Program> installationData = getInstallationData();
        EnvironmentData environmentData = new EnvironmentData(null, programsSettings);
        environmentData.setInstallationsData(installationData);

        // when
        SortedSet<Program> availableToInstall = new AvailabilityFilter(environmentData)
                .findProgramsAvailableToInstallOrInstalledWithDefinedSettings(getProgramsAvailableOnServers());

        // then
        assertThat(availableToInstall)
                .as("availableToInstall(SortedSet<Program>) should remove program installed but not available on servers, and add programs available on servers but not installed")
                .isNotNull().hasSize(1);
    }

    private SortedSet<Package> getInstalledPackages() {
        SortedSet<Package> installedPackages = new TreeSet<Package>();

        installedPackages.add(PackageBuilder.builder().setName("Package 1").setID("1").build());
        installedPackages.add(PackageBuilder.builder().setName("Package 2").setID("2").build());
        installedPackages.add(PackageBuilder.builder().setName("Package 3").setID("3").build());
        installedPackages.add(PackageBuilder.builder().setName("Package 4").setID("4").build());
        installedPackages.add(PackageBuilder.builder().setName("Package 5").setID("5").build());

        return installedPackages;
    }

    private SortedSet<Package> getAvailablePackages() {
        SortedSet<Package> availablePackages = new TreeSet<Package>();

        availablePackages.add(PackageBuilder.builder().setName("Package 3").setID("3").build());
        availablePackages.add(PackageBuilder.builder().setName("Package 4").setID("4").build());
        availablePackages.add(PackageBuilder.builder().setName("Package 5").setID("5").build());
        availablePackages.add(PackageBuilder.builder().setName("Package 6").setID("6").build());
        availablePackages.add(PackageBuilder.builder().setName("Package 7").setID("7").build());
        availablePackages.add(PackageBuilder.builder().setName("Package 8").setID("8").build());

        return availablePackages;
    }

    private SortedSet<ProgramSettings> getProgramsSettings() {
        SortedSet<ProgramSettings> programsSettings = new TreeSet<ProgramSettings>();

        programsSettings.add(ProgramSettingsBuilder.builder()
                .setProgramName(Values.ProgramSettings.programName)
                .setProgramExecutableName(Values.ProgramSettings.programExecutableName)
                .setPathToProgramDirectory(Paths.Installations.Program.programDir)
                .setPathToProgram(Paths.Installations.Program.programPath)
                .setServerAddress(Values.ProgramSettings.serverAddress)
                .setDevelopmentVersion(Values.ProgramSettings.developmentVersion).build());

        return programsSettings;
    }

    private SortedSet<Program> getInstallationData() {
        SortedSet<Program> installationData = new TreeSet<Program>();

        installationData.add(ProgramBuilder.builder().setName(Values.Program.name)
                .setPathToProgramDirectory(Paths.Installations.Program.programDir)
                .setServerAddress(Values.Program.serverAddress).setPackages(getInstalledPackages())
                .build());
        installationData.add(ProgramBuilder.builder().setName("Another")
                .setPathToProgramDirectory("C:\\another").setServerAddress("another.com")
                .setPackages(new TreeSet<Package>()).build());

        return installationData;
    }

    private SortedSet<Program> getProgramsAvailableOnServers() {
        SortedSet<Program> availableOnServer = new TreeSet<Program>();

        availableOnServer.addAll(getInstallationData());

        return availableOnServer;
    }
}
