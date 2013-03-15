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

import com.autoupdater.client.environment.settings.ProgramSettingsBuilder;

public class TestURLResolver {
    @Test
    public void testGetPackgesURL() {
        // when
        String url = getURLResolver().getPackagesInfoURL();

        // then
        assertThat(url).as("getPackagesURL() should properly generate URL").isEqualTo(
                "http://test.server.com/api/list_repo");
    }

    @Test
    public void testGetUpdateInfoURL() {
        // when
        String url = getURLResolver().getUpdateInfoURL("1");

        // then
        assertThat(url).as("getUpdateURL() should properly generate URL").isEqualTo(
                "http://test.server.com/api/list_updates/1");
    }

    @Test
    public void testGetChangelogInfoURL() {
        // when
        String url = getURLResolver().getChangelogInfoURL("1");

        // then
        assertThat(url).as("getChangelogURL() should properly generate URL").isEqualTo(
                "http://test.server.com/api/list_changes/1");
    }

    @Test
    public void testGetBugsInfoURL() {
        // when
        String url = getURLResolver().getBugsInfoURL("1");

        // then
        assertThat(url).as("getBugsURL() should properly generate URL").isEqualTo(
                "http://test.server.com/api/list_bugs/1");
    }

    @Test
    public void testGetFileURL() {
        // when
        String url = getURLResolver().getFileURL("1");

        // then
        assertThat(url).as("getFileURL() should properly generate URL").isEqualTo(
                "http://test.server.com/api/download/1");
    }

    private URLResolver getURLResolver() {
        return new URLResolver(ProgramSettingsBuilder.builder().setProgramName("Test program")
                .setProgramExecutableName("program.exe").setPathToProgramDirectory("C:\\program")
                .setPathToProgram("C:\\program\\program.exe").setServerAddress("test.server.com/")
                .setDevelopmentVersion(true).build());
    }
}
