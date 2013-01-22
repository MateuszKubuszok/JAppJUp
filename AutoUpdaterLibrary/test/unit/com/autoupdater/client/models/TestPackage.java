package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestPackage {
    @Test
    public void testConstructor() {
        // given
        String name = "some name";
        String versionNumber = "12.34.56.78";

        // when
        Package _package = PackageBuilder.builder().setName(name).setVersionNumber(versionNumber)
                .build();

        // then
        assertThat(_package.getName()).as("Constructor should set name properly").isNotNull()
                .isEqualTo(name);
        assertThat(_package.getVersionNumber())
                .as("Constructor should not set version number properly when one is given")
                .isNotNull().isInstanceOf(VersionNumber.class);
    }
}
