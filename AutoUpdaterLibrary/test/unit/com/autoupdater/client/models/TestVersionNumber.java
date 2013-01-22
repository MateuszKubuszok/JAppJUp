package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestVersionNumber {
    @Test
    public void testConstructor() {
        // given
        String versionNumberString = "15.26.37.48";

        // when
        VersionNumber versionNumber = new VersionNumber(versionNumberString);

        // then
        assertThat(versionNumber.getMajor()).as(
                "Contructor should set major version number properly").isEqualTo(15);
        assertThat(versionNumber.getMinor()).as(
                "Contructor should set minor version number properly").isEqualTo(26);
        assertThat(versionNumber.getRelease()).as(
                "Contructor should set release version number properly").isEqualTo(37);
        assertThat(versionNumber.getNightly()).as(
                "Contructor should set nightly version number properly").isEqualTo(48);
    }
}
