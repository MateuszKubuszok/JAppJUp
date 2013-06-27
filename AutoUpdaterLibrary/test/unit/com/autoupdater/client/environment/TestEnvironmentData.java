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

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;

import net.jsdpu.EOperatingSystem;

import org.junit.Test;

import com.autoupdater.client.AbstractTest;
import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;

public class TestEnvironmentData extends AbstractTest {
    @Test
    public void testConstructor() {
        // given
        ClientSettings clientSettings = clientSettings();
        SortedSet<ProgramSettings> programsSettings = programsSettings();

        // when
        EnvironmentData environmentData = new EnvironmentData(clientSettings, programsSettings);

        // then
        assertThat(environmentData.getClientSettings())
                .as("Constructor should set client's settings properly").isNotNull()
                .isEqualTo(clientSettings);
        assertThat(environmentData.getProgramsSettings())
                .as("Constructor should set programs' settings properly").isNotNull()
                .isEqualTo(programsSettings);
    }

    @Test(expected = ClientEnvironmentException.class)
    public void testSave() throws ClientEnvironmentException, IOException {
        forSetEnvironmentDataManagerShouldSave();
        forNotSetEnvironmentDataManagerShouldNotSave();
    }

    private void forSetEnvironmentDataManagerShouldSave() throws ClientEnvironmentException,
            IOException {
        // given
        EnvironmentContext context = new EnvironmentContext();
        File settingsFile = new File(context.getTemporaryDirectory() + "settings.xml");
        File installationDataFile = new File(context.getTemporaryDirectory()
                + "installationData.xml");
        context.setSettingsXMLPath(settingsFile.getPath());
        context.setInstallationDataXMLPath(installationDataFile.getPath());
        EnvironmentDataManager edm = new EnvironmentDataManager(context);

        // when
        edm.createDefaultSettings().save();
        EnvironmentData environmentData = edm.getEnvironmentData();

        // then
        assertThat(settingsFile).as("save() should create settings file").exists();
        assertThat(installationDataFile).as("save() should create installation data file").exists();
        assertThat(environmentData).as("save should persist data").isNotNull();

        // clean
        settingsFile.delete();
        installationDataFile.delete();
    }

    private void forNotSetEnvironmentDataManagerShouldNotSave() throws ClientEnvironmentException,
            IOException {
        // given
        ClientSettings clientSettings = clientSettings();
        SortedSet<ProgramSettings> programsSettings = programsSettings();

        // when
        EnvironmentData environmentData = new EnvironmentData(clientSettings, programsSettings);
        environmentData.save();

        // then
        // exception
    }

    @Test
    public void testGetSystem() {
        // given
        ClientSettings clientSettings = clientSettings();
        SortedSet<ProgramSettings> programsSettings = programsSettings();

        // when
        EnvironmentData environmentData = new EnvironmentData(clientSettings, programsSettings);

        // when
        assertThat(environmentData.getSystem()).as(
                "getSystem() should return current system handler").isEqualTo(
                EOperatingSystem.currentOperatingSystem());
    }

    @Test
    public void testToString() {
        // given
        ClientSettings clientSettings = clientSettings();
        SortedSet<ProgramSettings> programsSettings = programsSettings();

        // when
        EnvironmentData environmentData = new EnvironmentData(clientSettings, programsSettings);

        // then
        assertThat(environmentData.toString()).as("toString should not be null").isNotNull();
    }
}
