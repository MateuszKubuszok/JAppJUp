package com.autoupdater.client;

import static com.autoupdater.client.environment.Mocks.environmentData;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.autoupdater.client.environment.AvailabilityFilter;
import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;

public class TestClient {
    @Test
    public void testConstructor() throws ProgramSettingsNotFoundException {
        // given
        EnvironmentData environmentData = environmentData();

        // when
        Client client = new Client(environmentData);

        // then
        assertThat(client.getProgramsSettings()).as(
                "Client should properly return Programs' Settings").isEqualTo(
                environmentData.getProgramsSettings());
        assertThat(client.getInstalledPrograms()).as(
                "Client should properly return Installed Programs").isEqualTo(
                environmentData.getInstallationsData());
    }

    @Test
    public void testGetAvailabilityFilter() throws ProgramSettingsNotFoundException {
        // given
        EnvironmentData environmentData = environmentData();
        Client client = new Client(environmentData);

        // when
        AvailabilityFilter filter = client.getAvailabilityFilter();

        // then
        assertThat(filter).as("Return proper availability filter").isNotNull();
    }

    @Test
    public void testSaveChanges() throws ClientEnvironmentException, IOException {
        // given
        EnvironmentData environmentData = environmentData();
        Client client = new Client(environmentData);

        // when
        client.saveChanges();

        // then
        // shouldn't throw exception
    }
}
