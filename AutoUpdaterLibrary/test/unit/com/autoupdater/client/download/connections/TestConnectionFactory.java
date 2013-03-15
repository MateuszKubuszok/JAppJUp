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
