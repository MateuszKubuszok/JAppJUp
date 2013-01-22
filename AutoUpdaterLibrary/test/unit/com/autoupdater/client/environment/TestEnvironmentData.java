package com.autoupdater.client.environment;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.autoupdater.client.AbstractTest;

public class TestEnvironmentData extends AbstractTest {
    @Test
    public void testConstructor() {
        // when
        EnvironmentData environmentData = new EnvironmentData(clientSettings(), programsSettings());

        // then
        assertThat(environmentData.getClientSettings())
                .as("Constructor should set client's settings properly").isNotNull()
                .isEqualTo(clientSettings());
        assertThat(environmentData.getProgramsSettings())
                .as("Constructor should set programs' settings properly").isNotNull()
                .isEqualTo(programsSettings());
    }
}
