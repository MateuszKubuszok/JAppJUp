package com.autoupdater.client.environment;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestEnvironmentContext {
    @Test
    public void testConstructor() {
        // when
        EnvironmentContext context = new EnvironmentContext();

        // then
        assertThat(context.getDefaultClientName())
                .as("Constructor should set client name properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_CLIENT_NAME);
        assertThat(context.getTemporaryDirectory())
                .as("Constructor should set temporary directory properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_TEMPORARY_DIRECTORY);
        assertThat(context.getLocalAppData())
                .as("Constructor should set local application data directory properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_LOCAL_APPLICATION_DATA);
        assertThat(context.getSettingsXMLPath())
                .as("Constructor should set settings XML file path properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_SETTINGS_XML_PATH);
        assertThat(context.getInstallationDataXMLPath())
                .as("Constructor should set installation data XML file path properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_INSTALLATION_DATA_XML_PATH);

        assertThat(context.getDefaultPathToClientDirectory())
                .as("Constructor should set default client directory properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_CLIENT_DIRECTORY_PATH);
        assertThat(context.getDefaultPathToClient())
                .as("Constructor should set default client executable properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_CLIENT_EXECUTABLE_PATH);
        assertThat(context.getDefaultPathToInstaller())
                .as("Constructor should set default installer path properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_INSTALLER_PATH);
        assertThat(context.getDefaultProxyAddress())
                .as("Constructor should set default proxy address properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_PROXY_ADDRESS);
        assertThat(context.getDefaultProxyPort())
                .as("Constructor should set default proxy port properly").isNotNull()
                .isEqualTo(EnvironmentDefaultConfiguration.DEFAULT_PROXY_PORT);
    }
}
