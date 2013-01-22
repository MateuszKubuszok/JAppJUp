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
