package com.autoupdater.client.environment.settings;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;

public class TestClientSettings {
    @Test
    public void testConstructorWithoutProxy() {
        // when
        ClientSettings clientSettings = ClientSettingsBuilder.builder()
                .setClientName(Values.ClientSettings.clientName)
                .setClientExecutableName(Values.ClientSettings.clientExecutableName)
                .setPathToClient(Paths.Library.clientPath)
                .setPathToClientDirectory(Paths.Library.clientDir)
                .setPathToInstaller(Paths.Library.installerPath).build();

        // then
        assertThat(clientSettings.getClientName()).as("Constructor should set client's name")
                .isNotNull().isEqualTo(Values.ClientSettings.clientName);
        assertThat(clientSettings.getClientExecutableName())
                .as("Constructor should set client's executable name").isNotNull()
                .isEqualTo(Values.ClientSettings.clientExecutableName);
        assertThat(clientSettings.getPathToClientDirectory())
                .as("Constructor should set client's directory properly").isNotNull()
                .isEqualTo(Paths.Library.clientDir);
        assertThat(clientSettings.getPathToClient())
                .as("Constructor should set client's path properly").isNotNull()
                .isEqualTo(Paths.Library.clientPath);
        assertThat(clientSettings.getPathToInstaller())
                .as("Constructor should set installer's path properly").isNotNull()
                .isEqualTo(Paths.Library.installerPath);
    }

    @Test
    public void testConstructorWithProxy() {
        // when
        ClientSettings clientSettings = ClientSettingsBuilder.builder()
                .setClientName(Values.ClientSettings.clientName)
                .setClientExecutableName(Values.ClientSettings.clientExecutableName)
                .setPathToClient(Paths.Library.clientPath)
                .setPathToClientDirectory(Paths.Library.clientDir)
                .setPathToInstaller(Paths.Library.installerPath)
                .setProxyAddress(Values.ClientSettings.proxyAddress)
                .setProxyPort(Values.ClientSettings.proxyPort).build();

        // then
        assertThat(clientSettings.getClientName()).as("Constructor should set client's name")
                .isNotNull().isEqualTo(Values.ClientSettings.clientName);
        assertThat(clientSettings.getClientExecutableName())
                .as("Constructor should set client's executable name").isNotNull()
                .isEqualTo(Values.ClientSettings.clientExecutableName);
        assertThat(clientSettings.getPathToClientDirectory())
                .as("Constructor should set client's directory properly").isNotNull()
                .isEqualTo(Paths.Library.clientDir);
        assertThat(clientSettings.getPathToClient())
                .as("Constructor should set client's path properly").isNotNull()
                .isEqualTo(Paths.Library.clientPath);
        assertThat(clientSettings.getPathToInstaller())
                .as("Constructor should set installer's path properly").isNotNull()
                .isEqualTo(Paths.Library.installerPath);
        assertThat(clientSettings.getProxyAddress())
                .as("Constructor should not set proxy address properly").isNotNull()
                .isEqualTo(Values.ClientSettings.proxyAddress);
        assertThat(clientSettings.getProxyPort()).as("Constructor should set proxy port properly")
                .isNotNull().isEqualTo(Values.ClientSettings.proxyPort);
    }
}
