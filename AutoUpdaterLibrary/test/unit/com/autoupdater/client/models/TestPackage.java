package com.autoupdater.client.models;

import static com.autoupdater.client.models.VersionNumber.*;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestPackage {
    @Test
    public void testConstructor() {
        // given

        // when
        Package _package = new Package();

        // then
        assertThat(_package.getName()).as("Package's name is an empty string").isNotNull()
                .isEmpty();
        assertThat(_package.getID()).as("Package's ID is an empty string").isNotNull().isEmpty();
        assertThat(_package.getProgram()).as("Package's Program is null").isNull();
        assertThat(_package.getUpdates()).as("Package's Updates is an empty collection")
                .isNotNull().isEmpty();
        assertThat(_package.getChangelog()).as("Package's Changelog is empty collection")
                .isNotNull().isEmpty();
        assertThat(_package.getVersionNumber()).as("Package's Updates is an empty collection")
                .isEqualTo(UNVERSIONED);
    }

    @Test
    public void testEquals() {
        forEqualPackagesShoudlBeEqual();
        forDifferentPackagesShouldNotBeEqual();
    }

    private void forEqualPackagesShoudlBeEqual() {
        // given
        Program program = ProgramBuilder.builder().setName("some name")
                .setPathToProgramDirectory("some/path").setServerAddress("some address").build();
        String name = "some name";
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Package package1 = PackageBuilder.builder().setName(name).setVersionNumber(version)
                .setProgram(program).build();
        Package package2 = PackageBuilder.builder().setName(name).setVersionNumber(version)
                .setProgram(program).build();

        // then
        assertThat(package1).as("Packages with equal properites should be equal").isEqualTo(
                package2);
    }

    private void forDifferentPackagesShouldNotBeEqual() {
        // given
        Program program = ProgramBuilder.builder().setName("some name")
                .setPathToProgramDirectory("some/path").setServerAddress("some address").build();
        String name = "some name";
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Package package1 = PackageBuilder.builder().setName("some other name")
                .setVersionNumber(version).setProgram(program).build();
        Package package2 = PackageBuilder.builder().setName(name).setVersionNumber(version)
                .setProgram(program).build();

        // then
        assertThat(package1).as("Packages with equal properites should be equal").isNotEqualTo(
                package2);
    }
}
