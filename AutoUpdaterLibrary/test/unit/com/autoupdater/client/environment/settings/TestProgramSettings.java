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
}
