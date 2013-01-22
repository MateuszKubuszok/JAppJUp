package com.autoupdater.client.download.connections;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.Test;

import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ClientSettingsBuilder;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.environment.settings.ProgramSettingsBuilder;

public class TestPerProgramConnectionFactory {
    @Test
    public void testCreatePackagesInfoConnection() throws IOException {
        // given
        PerProgramConnectionFactory factory = getFactory();

        // then
        HttpURLConnection connection = factory.createPackagesInfoConnection();
        assertThat(connection.getURL().getHost()).as(
                "getPackagesInfoConnection() should set host properly").isEqualTo("127.0.0.1");
        assertThat(connection.getURL().getFile()).as(
                "getPackagesInfoConnection() should set path properly").isEqualTo("/api/list_repo");
    }

    @Test
    public void testCreateUpdateInfoConnection() throws IOException {
        // given
        PerProgramConnectionFactory factory = getFactory();

        // then
        HttpURLConnection connection = factory.createUpdateInfoConnection("1");
        assertThat(connection.getURL().getHost()).as(
                "getPackagesInfoConnection() should set host properly").isEqualTo("127.0.0.1");
        assertThat(connection.getURL().getFile()).as(
                "getPackagesInfoConnection() should set path properly").isEqualTo(
                "/api/list_updates/1");
    }

    @Test
    public void testCreateChangelogInfoConnection() throws IOException {
        // given
        PerProgramConnectionFactory factory = getFactory();

        // then
        HttpURLConnection connection = factory.createChangelogInfoConnection("1");
        assertThat(connection.getURL().getHost()).as(
                "getPackagesInfoConnection() should set host properly").isEqualTo("127.0.0.1");
        assertThat(connection.getURL().getFile()).as(
                "getPackagesInfoConnection() should set path properly").isEqualTo(
                "/api/list_changes/1");
    }

    @Test
    public void testCreateBugsInfoConnection() throws IOException {
        // given
        PerProgramConnectionFactory factory = getFactory();

        // then
        HttpURLConnection connection = factory.createBugsInfoConnection("1");
        assertThat(connection.getURL().getHost()).as(
                "getPackagesInfoConnection() should set host properly").isEqualTo("127.0.0.1");
        assertThat(connection.getURL().getFile()).as(
                "getBugsInfoConnection() should set path properly").isEqualTo("/api/list_bugs/1");
    }

    @Test
    public void testCreateFileConnection() throws IOException {
        // given
        PerProgramConnectionFactory factory = getFactory();

        // then
        HttpURLConnection connection = factory.createFileConnection("1");
        assertThat(connection.getURL().getHost()).as(
                "getPackagesInfoConnection() should set host properly").isEqualTo("127.0.0.1");
        assertThat(connection.getURL().getFile()).as(
                "getPackagesInfoConnection() should set path properly")
                .isEqualTo("/api/download/1");
    }

    private PerProgramConnectionFactory getFactory() {
        ClientSettings clientConfiguration = ClientSettingsBuilder.builder().build();
        ProgramSettings programConfiguration = ProgramSettingsBuilder.builder()
                .setProgramName("Test program").setProgramExecutableName("program.exe")
                .setPathToProgramDirectory("C:\\program")
                .setPathToProgram("C:\\program\\program.exe").setServerAddress("127.0.0.1")
                .setDevelopmentVersion(true).build();
        return new PerProgramConnectionFactory(clientConfiguration, programConfiguration);
    }
}
