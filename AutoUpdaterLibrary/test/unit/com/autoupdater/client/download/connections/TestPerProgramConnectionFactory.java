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

        // when
        HttpURLConnection connection = factory.createPackagesInfoConnection();

        // then
        assertThat(connection.getURL().getHost()).as(
                "getPackagesInfoConnection() should set host properly").isEqualTo("127.0.0.1");
        assertThat(connection.getURL().getFile()).as(
                "getPackagesInfoConnection() should set path properly").isEqualTo("/api/list_repo");
    }

    @Test
    public void testCreateUpdateInfoConnection() throws IOException {
        // given
        PerProgramConnectionFactory factory = getFactory();

        // when
        HttpURLConnection connection = factory.createUpdateInfoConnection("1");

        // then
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

        // when
        HttpURLConnection connection = factory.createChangelogInfoConnection("1");

        // then
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

        // when
        HttpURLConnection connection = factory.createBugsInfoConnection("1");

        // then
        assertThat(connection.getURL().getHost()).as(
                "getPackagesInfoConnection() should set host properly").isEqualTo("127.0.0.1");
        assertThat(connection.getURL().getFile()).as(
                "getBugsInfoConnection() should set path properly").isEqualTo("/api/list_bugs/1");
    }

    @Test
    public void testCreateFileConnection() throws IOException {
        // given
        PerProgramConnectionFactory factory = getFactory();

        // when
        HttpURLConnection connection = factory.createFileConnection("1");

        // then
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
