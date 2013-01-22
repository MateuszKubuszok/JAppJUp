package com.autoupdater.gui.mocks;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ClientSettingsBuilder;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.environment.settings.ProgramSettingsBuilder;
import com.autoupdater.client.models.BugEntry;
import com.autoupdater.client.models.BugEntryBuilder;
import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.models.ChangelogEntryBuilder;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.PackageBuilder;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.ProgramBuilder;

public class MockModels {
    private static SortedSet<Program> installedPrograms;

    private static EnvironmentData environmentData;

    private static ClientSettings clientSettings;

    private static SortedSet<ProgramSettings> programsSettings;

    public static EnvironmentData getEnvironmentData() {
        return (environmentData != null) ? environmentData
                : (environmentData = new EnvironmentData(getClientSettings(), getProgramsSettings()));
    }

    public static ClientSettings getClientSettings() {
        return (clientSettings != null) ? clientSettings : (clientSettings = ClientSettingsBuilder
                .builder().setClientName(Values.ClientSettings.clientName)
                .setClientExecutableName(Values.ClientSettings.clientExecutableName)
                .setPathToClient(Paths.Library.clientPath)
                .setPathToClientDirectory(Paths.Library.clientDir)
                .setPathToInstaller(Paths.Library.installerPath).build());
    }

    public static SortedSet<ProgramSettings> getProgramsSettings() {
        if (programsSettings != null)
            return programsSettings;

        programsSettings = new TreeSet<ProgramSettings>();

        programsSettings.add(ProgramSettingsBuilder.builder()
                .setProgramName(Values.ProgramSettings.programName)
                .setProgramExecutableName(Values.ProgramSettings.programExecutableName)
                .setPathToProgramDirectory(Paths.Installations.Program.programDir)
                .setPathToProgram(Paths.Installations.Program.programPath)
                .setServerAddress(Values.ProgramSettings.serverAddress)
                .setDevelopmentVersion(Values.ProgramSettings.developmentVersion).build());

        return programsSettings;
    }

    public static SortedSet<Program> getInstalledPrograms() {
        if (installedPrograms != null)
            return installedPrograms;

        installedPrograms = new TreeSet<Program>();

        SortedSet<Package> packages = new TreeSet<Package>();

        packages.add(PackageBuilder
                .builder()
                .setName("Package 1.1")
                .setID("1")
                .setVersionNumber("1.0.0.0")
                .setChangelog(
                        new TreeSet<ChangelogEntry>(Arrays.asList(ChangelogEntryBuilder.builder()
                                .setDescription("Initial public release")
                                .setVersionNumber("1.0.0.0").build()))).build());
        packages.add(PackageBuilder
                .builder()
                .setName("Package 1.2")
                .setID("2")
                .setVersionNumber("1.1.0.0")
                .setChangelog(
                        new TreeSet<ChangelogEntry>(Arrays.asList(ChangelogEntryBuilder.builder()
                                .setDescription("Initial public release")
                                .setVersionNumber("1.1.0.0").build()))).build());

        installedPrograms.add(ProgramBuilder
                .builder()
                .setName("Program 1")
                .setPathToProgramDirectory("C:\\Program1")
                .setServerAddress("http://repo.program1.com")
                .setPackages(packages)
                .setBugs(
                        new TreeSet<BugEntry>(Arrays.asList(BugEntryBuilder.builder()
                                .setDescription("Some known bug").build()))).build());

        packages.add(PackageBuilder
                .builder()
                .setName("Package 2.1")
                .setID("3")
                .setVersionNumber("1.0.1.0")
                .setChangelog(
                        new TreeSet<ChangelogEntry>(Arrays.asList(ChangelogEntryBuilder.builder()
                                .setDescription("Initial public release")
                                .setVersionNumber("1.0.1.0").build()))).build());
        packages.add(PackageBuilder
                .builder()
                .setName("Package 2.2")
                .setID("4")
                .setVersionNumber("1.0.0.1")
                .setChangelog(
                        new TreeSet<ChangelogEntry>(Arrays.asList(ChangelogEntryBuilder.builder()
                                .setDescription("Initial public release")
                                .setVersionNumber("1.0.0.1").build()))).build());

        installedPrograms.add(ProgramBuilder
                .builder()
                .setName("Program 2")
                .setPathToProgramDirectory("C:\\Program2")
                .setServerAddress("http://repo.program2.com")
                .setPackages(packages)
                .setBugs(
                        new TreeSet<BugEntry>(Arrays.asList(BugEntryBuilder.builder()
                                .setDescription("Some other known bug").build()))).build());

        return installedPrograms;
    }

    public static Program getInstalledProgram() {
        return getInstalledPrograms().first();
    }

    public static SortedSet<Package> getInstalledPackages() {
        return getInstalledProgram().getPackages();
    }

    public static Package getInstalledPackage() {
        return getInstalledPackages().first();
    }
}
