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

import java.util.Comparator;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;

public class TestProgramSettings {
    @Test
    public void testConstructor() {
        // when
        ProgramSettings programSettings = ProgramSettingsBuilder.builder()
                .setProgramName(Values.ProgramSettings.programName)
                .setProgramExecutableName(Values.ProgramSettings.programExecutableName)
                .setPathToProgramDirectory(Paths.Installations.Program.programDir)
                .setPathToProgram(Paths.Installations.Program.programPath)
                .setServerAddress(Values.ProgramSettings.serverAddress)
                .setDevelopmentVersion(Values.ProgramSettings.developmentVersion).build();

        // test
        assertThat(programSettings.getProgramName())
                .as("Constructor should set program name properly").isNotNull()
                .isEqualTo(Values.ProgramSettings.programName);
        assertThat(programSettings.getProgramExecutableName())
                .as("Constructor should set executable name properly").isNotNull()
                .isEqualTo(Values.ProgramSettings.programExecutableName);
        assertThat(programSettings.getPathToProgramDirectory())
                .as("Constructor should set path to program's directry properly").isNotNull()
                .isEqualTo(Paths.Installations.Program.programDir);
        assertThat(programSettings.getPathToProgram())
                .as("Constructor should set path to program properly").isNotNull()
                .isEqualTo(Paths.Installations.Program.programPath);
        assertThat(programSettings.getServerAddress())
                .as("Constructor should set server's address properly").isNotNull()
                .isEqualTo(Values.ProgramSettings.serverAddress);
        assertThat(programSettings.isDevelopmentVersion()).as(
                "Constructor should set development version properly").isTrue();
    }

    @Test
    public void testGeneralComparator() {
        forEqualAttributesShouldHaveGeneralComparatorsCompareTosEqualTo0();
        forNotEqualDescriptionsShouldHaveGeneralComparatorsCompareTosDifferentTo0();
    }

    private void forEqualAttributesShouldHaveGeneralComparatorsCompareTosEqualTo0() {
        // given
        String programName = "some name";
        String serverAddress = "some address";
        String pathToProgramDirectory = "some path";
        ProgramSettings programSettings1 = ProgramSettingsBuilder.builder()
                .setProgramName(programName).setServerAddress(serverAddress)
                .setPathToProgramDirectory(pathToProgramDirectory).build();
        ProgramSettings programSettings2 = ProgramSettingsBuilder.builder()
                .setProgramName(programName).setServerAddress(serverAddress)
                .setPathToProgramDirectory(pathToProgramDirectory).build();

        // when
        Comparator<ProgramSettings> comparator = new ProgramSettings.GeneralComparator();

        // then
        assertThat(comparator.compare(programSettings1, programSettings2))
                .as("ProgramSettings with equal attributes should have comparator's compareTos equal to 0")
                .isEqualTo(0);
        assertThat(comparator.compare(programSettings2, programSettings1))
                .as("ProgramSettings with equal attributes should have comparator's compareTos equal to 0")
                .isEqualTo(0);
    }

    private void forNotEqualDescriptionsShouldHaveGeneralComparatorsCompareTosDifferentTo0() {
        // given
        String programName = "some name";
        String serverAddress = "some address";
        String pathToProgramDirectory = "some path";
        ProgramSettings programSettings1 = ProgramSettingsBuilder.builder()
                .setProgramName(programName).setServerAddress(serverAddress)
                .setPathToProgramDirectory(pathToProgramDirectory).build();
        ProgramSettings programSettings2 = ProgramSettingsBuilder.builder()
                .setProgramName("some other names").setServerAddress(serverAddress)
                .setPathToProgramDirectory(pathToProgramDirectory).build();

        // when
        Comparator<ProgramSettings> comparator = new ProgramSettings.GeneralComparator();

        // then
        assertThat(comparator.compare(programSettings1, programSettings2))
                .as("Bugs with different descriptions should have comparator's compareTos of the descriptions")
                .isLessThan(0);
        assertThat(comparator.compare(programSettings2, programSettings1))
                .as("Bugs with different descriptions should have comparator's compareTos of the descriptions")
                .isGreaterThan(0);
    }

    @Test
    public void testGetInstallationsServerPropertiesComparator() {
        // given

        // when
        Comparator<ProgramSettings> comparator = new ProgramSettings()
                .getInstallationsServerPropertiesComparator();

        // then
        assertThat(comparator).as(
                "InstallationsServerPropertiesComparator is instance of GeneralComparator")
                .isInstanceOf(ProgramSettings.GeneralComparator.class);
    }

    @Test
    public void testGetLocalInstallationsComparator() {
        // given

        // when
        Comparator<ProgramSettings> comparator = new ProgramSettings()
                .getLocalInstallationsComparator();

        // then
        assertThat(comparator).as("LocalInstallationsComparator is instance of GeneralComparator")
                .isInstanceOf(ProgramSettings.GeneralComparator.class);
    }

    @Test
    public void testGetLocal2ServerComparator() {
        // given

        // when
        Comparator<ProgramSettings> comparator = new ProgramSettings().getLocal2ServerComparator();

        // then
        assertThat(comparator).as("Local2ServerComparator is instance of GeneralComparator")
                .isInstanceOf(ProgramSettings.GeneralComparator.class);
    }

    @Test
    public void testToString() {
        // given

        // when
        ProgramSettings programSettings = ProgramSettingsBuilder.builder().build();

        // then
        assertThat(programSettings.toString()).as("toString should not be null").isNotNull();
    }
}
