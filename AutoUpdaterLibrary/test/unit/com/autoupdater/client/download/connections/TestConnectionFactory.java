package com.autoupdater.client.download.connections;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.autoupdater.client.environment.settings.ClientSettingsBuilder;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.environment.settings.ProgramSettingsBuilder;

public class TestConnectionFactory {
    @Test
    public void testGetPerProgramConnectionFactory() {
        // given
        ConnectionFactory factory = getFactory();

        // when
        ProgramSettings programConfiguration = ProgramSettingsBuilder.builder()
                .setProgramName("Test program").setProgramExecutableName("program.exe")
                .setPathToProgramDirectory("C:\\program")
                .setPathToProgram("C:\\program\\program.exe").setServerAddress("test.server.com/")
                .setDevelopmentVersion(true).build();
        PerProgramConnectionFactory perProgramFactory = factory
                .getPerProgramConnectionFactory(programConfiguration);

        // then
        assertThat(perProgramFactory)
                .as("getPerProgramConnection() should return PerProgramConnectionFactory instance")
                .isNotNull().isInstanceOf(PerProgramConnectionFactory.class);
    }

    private ConnectionFactory getFactory() {
        return new ConnectionFactory(ClientSettingsBuilder.builder().build());
    }
}
