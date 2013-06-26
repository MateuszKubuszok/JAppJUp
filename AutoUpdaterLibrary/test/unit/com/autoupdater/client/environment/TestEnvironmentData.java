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

import java.util.SortedSet;

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
}
