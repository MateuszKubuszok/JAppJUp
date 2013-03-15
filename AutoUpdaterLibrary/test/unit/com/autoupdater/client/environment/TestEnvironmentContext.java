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
