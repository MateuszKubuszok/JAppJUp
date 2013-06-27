package com.autoupdater.client;

import static com.autoupdater.client.download.ConnectionConfiguration.DOWNLOAD_DIRECTORY;
import static com.autoupdater.client.environment.MockEnvironment.environmentData;
import static com.autoupdater.client.models.EUpdateStatus.*;
import static com.autoupdater.commons.installer.configuration.InstallerConfiguration.BACKUP_DIRECTORY;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;

import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.environment.AvailabilityFilter;
import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.installation.aggregated.services.AggregatedInstallationService;
import com.autoupdater.client.models.MockModels;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;

public class TestClient {
    private EnvironmentData environmentData;
    private Client client;

    @Before
    public void setUp() {
        try {
            environmentData = environmentData();
            client = new Client(environmentData);
        } catch (ProgramSettingsNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() {
        // given

        // when

        // then
        assertThat(client.getProgramsSettings()).as(
                "Client should properly return Programs' Settings").isEqualTo(
                environmentData.getProgramsSettings());
        assertThat(client.getInstalledPrograms()).as(
                "Client should properly return Installed Programs").isEqualTo(
                environmentData.getInstallationsData());
    }

    @Test
    public void testGetAvailabilityFilter() {
        // given

        // when
        AvailabilityFilter filter = client.getAvailabilityFilter();

        // then
        assertThat(filter).as("Return proper availability filter").isNotNull();
    }

    @Test
    public void testSaveChanges() throws ClientEnvironmentException, IOException {
        // given

        // when
        client.saveChanges();

        // then
        // shouldn't throw exception
    }

    @Test
    public void testCreatePackagesInfoAggregatedDownloadService() throws IOException {
        // given

        // when
        PackagesInfoAggregatedDownloadService aggregatedService = client
                .createPackagesInfoAggregatedDownloadService();

        // then
        assertThat(aggregatedService).as("Should create Aggregated Service").isNotNull();
        assertThat(aggregatedService.getServices()).as("Should create Service for each server")
                .isNotNull().hasSameSizeAs(environmentData.getProgramsSettingsForEachServer());
    }

    @Test
    public void testCreateUpdateInfoAggregatedDownloadService() throws IOException,
            ProgramSettingsNotFoundException {
        // given
        SortedSet<Package> packages = MockModels.packages();

        // when
        UpdateInfoAggregatedDownloadService aggregatedService = client
                .createUpdateInfoAggregatedDownloadService(packages);

        // then
        assertThat(aggregatedService).as("Should create Aggregated Service").isNotNull();
        assertThat(aggregatedService.getServices()).as("Should create Service for each package")
                .isNotNull().hasSameSizeAs(packages);
    }

    @Test
    public void testCreateChangelogInfoAggregatedDownloadService() throws IOException,
            ProgramSettingsNotFoundException {
        // given
        SortedSet<Package> packages = MockModels.packages();

        // when
        ChangelogInfoAggregatedDownloadService aggregatedService = client
                .createChangelogInfoAggregatedDownloadService(packages);

        // then
        assertThat(aggregatedService).as("Should create Aggregated Service").isNotNull();
        assertThat(aggregatedService.getServices()).as("Should create Service for each package")
                .isNotNull().hasSameSizeAs(packages);
    }

    @Test
    public void testCreateBugsInfoAggregatedDownloadService() throws IOException,
            ProgramSettingsNotFoundException {
        // given
        SortedSet<Program> programs = MockModels.programs();

        // when
        BugsInfoAggregatedDownloadService aggregatedService = client
                .createBugsInfoAggregatedDownloadService(programs);

        // then
        assertThat(aggregatedService).as("Should create Aggregated Service").isNotNull();
        assertThat(aggregatedService.getServices()).as("Should create Service for each program")
                .isNotNull().hasSameSizeAs(programs);
    }

    @Test
    public void testCreateFileAggregatedDownloadService() throws IOException,
            ProgramSettingsNotFoundException {
        // given
        SortedSet<Update> updates = MockModels.updates();
        for (Update update : updates)
            update.setStatus(SELECTED);

        // when
        FileAggregatedDownloadService aggregatedService = client
                .createFileAggregatedDownloadService(updates);

        // then
        assertThat(aggregatedService).as("Should create Aggregated Service").isNotNull();
        assertThat(aggregatedService.getServices()).as("Should create Service for each update")
                .isNotNull().hasSameSizeAs(updates);
    }

    @Test
    public void testCreateInstallationAggregatedService() {
        // given
        SortedSet<Update> updates = MockModels.updates();
        for (Update update : updates) {
            update.setStatus(DOWNLOADED);
            update.setFile(new File("mock"));
        }

        // when
        AggregatedInstallationService aggregatedService = client
                .createInstallationAggregatedService(updates);

        // then
        assertThat(aggregatedService).as("Should create Aggregated Service").isNotNull();
    }

    @Test
    public void testCleanTemp() {
        // given

        // when
        client.cleanTemp();

        // then
        assertThat(new File(DOWNLOAD_DIRECTORY)).as("Should remove download directory")
                .doesNotExist();
        assertThat(new File(BACKUP_DIRECTORY)).as("Should remove backup directory").doesNotExist();
    }
}
