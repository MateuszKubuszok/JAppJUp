package com.autoupdater.client.download;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.mockito.Mockito;

import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.EUpdateStrategy;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;

public class TestDownloadServiceFactory {
    private EnvironmentData environmentData;
    private ProgramSettings programSettings;
    private Program program;
    private Package _package;
    private SortedSet<Program> selectedPrograms;
    private SortedSet<Package> selectedPackages;
    private SortedSet<Update> selectedUpdates;

    @Test
    public void testCreatePackagesInfoAggregatedDownloadService() throws IOException,
            ProgramSettingsNotFoundException {
        // given
        DownloadServiceFactory connectionServiceManager = new DownloadServiceFactory(
                getEnvironmentData());

        // when
        PackagesInfoAggregatedDownloadService aggregatedService = connectionServiceManager
                .createPackagesInfoAggregatedDownloadService();

        // then
        assertThat(aggregatedService.getServices())
                .as("getPackagesInfoAggregatedDownloadService() should create one service for each server")
                .isNotNull().hasSize(1);
    }

    @Test
    public void testCreateUpdateInfoAggregatedDownloadService()
            throws ProgramSettingsNotFoundException, ClientEnvironmentException, IOException {
        // given
        DownloadServiceFactory connectionServiceManager = new DownloadServiceFactory(
                getEnvironmentData());
        SortedSet<Package> selectedPackages = getSelectedPackages();

        // when
        UpdateInfoAggregatedDownloadService aggregatedService = connectionServiceManager
                .createUpdateInfoAggregatedDownloadService(selectedPackages);

        // then
        assertThat(aggregatedService.getServices())
                .as("getUpdateInfoAggregatedDownloadService() should create one service for each selected program")
                .isNotNull().hasSize(1);
    }

    @Test
    public void testCreateChangelogInfoAggregatedDownloadService()
            throws ProgramSettingsNotFoundException, IOException {
        // given
        DownloadServiceFactory connectionServiceManager = new DownloadServiceFactory(
                getEnvironmentData());
        SortedSet<Package> selectedPackages = getSelectedPackages();

        // when
        ChangelogInfoAggregatedDownloadService aggregatedService = connectionServiceManager
                .createChangelogInfoAggregatedDownloadService(selectedPackages);

        // then
        assertThat(aggregatedService.getServices())
                .as("getChangelogInfoAggregatedDownloadService() should create one service for each selected program")
                .isNotNull().hasSize(1);
    }

    @Test
    public void testCreateBugsInfoAggregatedDownloadService()
            throws ProgramSettingsNotFoundException, IOException {
        // given
        DownloadServiceFactory connectionServiceManager = new DownloadServiceFactory(
                getEnvironmentData());
        SortedSet<Program> selectedPrograms = getSelectedPrograms();

        // when
        BugsInfoAggregatedDownloadService aggregatedService = connectionServiceManager
                .createBugsInfoAggregatedDownloadService(selectedPrograms);

        // then
        assertThat(aggregatedService.getServices())
                .as("getBugsInfoAggregatedDownloadService() should create one service for each selected program")
                .isNotNull().hasSize(1);
    }

    @Test
    public void testCreateFileAggregatedDownloadService() throws ProgramSettingsNotFoundException,
            ClientEnvironmentException, IOException {
        // given
        DownloadServiceFactory connectionServiceManager = new DownloadServiceFactory(
                getEnvironmentData());
        SortedSet<Update> selectedUpdates = getSelectedUpdates();

        // when
        FileAggregatedDownloadService aggregatedService = connectionServiceManager
                .createFileAggregatedDownloadService(selectedUpdates);

        // then
        assertThat(aggregatedService.getServices())
                .as("getChangelogInfoAggregatedDownloadService() should create one service for each selected program")
                .isNotNull().hasSize(1);
    }

    private EnvironmentData getEnvironmentData() throws ProgramSettingsNotFoundException {
        if (environmentData == null) {
            Program program = getProgram();
            Package _package = getPackage();
            Update update = getSelectedUpdates().first();

            ClientSettings clientSettings = Mockito.mock(ClientSettings.class);
            Mockito.when(clientSettings.getProxyAddress()).thenReturn(null);

            ProgramSettings programSettings = getProgramSettings();
            SortedSet<ProgramSettings> programsSettings = new TreeSet<ProgramSettings>();
            programsSettings.add(programSettings);

            environmentData = Mockito.mock(EnvironmentData.class);
            Mockito.when(environmentData.getClientSettings()).thenReturn(clientSettings);
            Mockito.when(environmentData.getProgramsSettings()).thenReturn(programsSettings);
            Mockito.when(environmentData.getProgramsSettingsForEachServer()).thenReturn(
                    programsSettings);
            Mockito.when(environmentData.findProgramSettingsForProgram(program)).thenReturn(
                    programSettings);
            Mockito.when(environmentData.findProgramSettingsForPackage(_package)).thenReturn(
                    programSettings);
            Mockito.when(environmentData.findProgramSettingsForUpdate(update)).thenReturn(
                    programSettings);
        }

        return environmentData;
    }

    private ProgramSettings getProgramSettings() {
        if (programSettings == null) {
            programSettings = Mockito.mock(ProgramSettings.class);
            Mockito.when(programSettings.getProgramName()).thenReturn("some program");
            Mockito.when(programSettings.getPathToProgramDirectory()).thenReturn("C:\\program");
            Mockito.when(programSettings.getServerAddress()).thenReturn("http://127.0.0.1");
        }

        return programSettings;
    }

    private Program getProgram() {
        if (program == null) {
            program = Mockito.mock(Program.class);
            Mockito.when(program.getName()).thenReturn("some program");
            Mockito.when(program.getPathToProgramDirectory()).thenReturn("C:\\program");
            Mockito.when(program.getServerAddress()).thenReturn("http://127.0.0.1");
        }

        return program;
    }

    private Package getPackage() {
        if (_package == null) {
            Program program = getProgram();
            _package = Mockito.mock(Package.class);
            Mockito.when(_package.getProgram()).thenReturn(program);
            Mockito.when(_package.getID()).thenReturn("1");
        }

        return _package;
    }

    private SortedSet<Program> getSelectedPrograms() {
        if (selectedPrograms == null) {
            selectedPrograms = new TreeSet<Program>();
            selectedPrograms.add(getPackage().getProgram());
        }

        return selectedPrograms;
    }

    private SortedSet<Package> getSelectedPackages() {
        if (selectedPackages == null) {
            selectedPackages = new TreeSet<Package>();
            selectedPackages.add(getPackage());
        }

        return selectedPackages;
    }

    private SortedSet<Update> getSelectedUpdates() {
        if (selectedUpdates == null) {
            Package _package = getPackage();

            Update update = Mockito.mock(Update.class);
            Mockito.when(update.getPackage()).thenReturn(_package);
            Mockito.when(update.getPackageID()).thenReturn("1");
            Mockito.when(update.getStatus()).thenReturn(EUpdateStatus.SELECTED);
            Mockito.when(update.getUpdateStrategy()).thenReturn(EUpdateStrategy.COPY);
            Mockito.when(update.getID()).thenReturn("1");

            selectedUpdates = new TreeSet<Update>();
            selectedUpdates.add(update);
        }

        return selectedUpdates;
    }
}
