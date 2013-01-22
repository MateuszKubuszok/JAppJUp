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
