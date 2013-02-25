package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestVersionNumberBuilder {
    @Test
    public void testBuilder() {
        // given
        int major = 15, minor = 26, release = 37, nightly = 48;
        String versionNumberString = major + "." + minor + "." + release + "." + nightly;

        // when
        VersionNumber versionNumber = new VersionNumber(versionNumberString);

        // then
        assertThat(versionNumber.getMajor()).as("Builder should set major version number properly")
                .isEqualTo(major);
        assertThat(versionNumber.getMinor()).as("Builder should set minor version number properly")
                .isEqualTo(minor);
        assertThat(versionNumber.getRelease()).as(
                "Builder should set release version number properly").isEqualTo(release);
        assertThat(versionNumber.getNightly()).as(
                "Builder should set nightly version number properly").isEqualTo(nightly);
    }

    @Test
    public void testAlternativeSetters() {
        // given
        int major = 15, minor = 26, release = 37, nightly = 48;

        // when
        VersionNumber versionNumber = new VersionNumber(major, minor, release, nightly);

        // then
        assertThat(versionNumber.getMajor()).as("Builder should set major version number properly")
                .isEqualTo(major);
        assertThat(versionNumber.getMinor()).as("Builder should set minor version number properly")
                .isEqualTo(minor);
        assertThat(versionNumber.getRelease()).as(
                "Builder should set release version number properly").isEqualTo(release);
        assertThat(versionNumber.getNightly()).as(
                "Builder should set nightly version number properly").isEqualTo(nightly);
    }
}
