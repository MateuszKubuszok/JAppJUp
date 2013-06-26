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

    @Test
    public void testToString() {
        // given

        // when
        ClientSettings clientSettings = ClientSettingsBuilder.builder().build();

        // then
        assertThat(clientSettings.toString()).as("toString should not be null").isNotNull();
    }
}
