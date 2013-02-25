package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestVersionNumberFunctional {
    @Test
    public void testConstructor() {
        // given
        int major = 15, minor = 26, release = 37, nightly = 48;
        String versionNumberString = major + "." + minor + "." + release + "." + nightly;

        // when
        VersionNumber versionNumber = new VersionNumber(versionNumberString);

        // then
        assertThat(versionNumber.getMajor()).as(
                "Getter and setter should work for major version number").isEqualTo(major);
        assertThat(versionNumber.getMinor()).as(
                "Getter and setter should work for minor version number").isEqualTo(minor);
        assertThat(versionNumber.getRelease()).as(
                "Getter and setter should work for release version number").isEqualTo(release);
        assertThat(versionNumber.getNightly()).as(
                "Getter and setter should work for nightly version number").isEqualTo(nightly);
    }

    @Test
    public void testAlternativeConstructor() {
        // given
        int major = 15, minor = 26, release = 37, nightly = 48;

        // when
        VersionNumber versionNumber = new VersionNumber(major, minor, release, nightly);

        // then
        assertThat(versionNumber.getMajor()).as(
                "Getter and setter should work for major version number").isEqualTo(major);
        assertThat(versionNumber.getMinor()).as(
                "Getter and setter should work for minor version number").isEqualTo(minor);
        assertThat(versionNumber.getRelease()).as(
                "Getter and setter should work for release version number").isEqualTo(release);
        assertThat(versionNumber.getNightly()).as(
                "Getter and setter should work for nightly version number").isEqualTo(nightly);
    }
}
