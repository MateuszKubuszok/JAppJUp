package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class TestProgramFunctional {
    @Test
    public void testSettersAndGetters() {
        // given
        Program program = new Program();
        String name = "some program";
        String pathToDirectory = "/path";
        String serverAddress = "http://address.com";
        String packageName = "some package";
        boolean developmentVersion = true;
        Package _package = PackageBuilder.builder().setName(packageName)
                .setVersionNumber("1.0.0.0").build();
        SortedSet<Package> packages = new TreeSet<Package>();
        packages.add(_package);
        String bugDescription = "some description";
        BugEntry bug = BugEntryBuilder.builder().setDescription(bugDescription).build();
        SortedSet<BugEntry> bugs = new TreeSet<BugEntry>();
        bugs.add(bug);

        // when
        program.setName(name);
        program.setPathToProgramDirectory(pathToDirectory);
        program.setServerAddress(serverAddress);
        program.setDevelopmentVersion(developmentVersion);
        program.setPackages(packages);
        program.setBugs(bugs);

        // then
        assertThat(program.getName()).as("Getter and setter should work for program name")
                .isNotNull().isEqualTo(name);
        assertThat(program.getPathToProgramDirectory())
                .as("Getter and setter should work for directory").isNotNull()
                .isEqualTo(pathToDirectory);
        assertThat(program.getServerAddress()).as("Getter and setter should work for address")
                .isNotNull().isEqualTo(serverAddress);
        assertThat(program.isDevelopmentVersion())
                .as("Getter and setter should work for development version").isNotNull()
                .isEqualTo(developmentVersion);
        assertThat(program.getPackages()).as("Getter and setter should work for packages")
                .isNotNull().isNotEmpty();
        assertThat(program.getPackages().first()).as("Getter and setter should work for packages")
                .isNotNull().isEqualTo(_package);
        assertThat(program.getBugs()).as("Builder should set bugs properly").isNotNull()
                .isNotEmpty();
        assertThat(program.getBugs().first()).as("Builder should set bugs properly").isNotNull()
                .isEqualTo(bug);
    }

    @Test
    public void testAlternativeSetters() {
        // given
        Program program = new Program();
        String developmentVersion = "true";

        // when
        program.setDevelopmentVersion(developmentVersion);

        // then
        assertThat(program.isDevelopmentVersion())
                .as("Getter and setter should work for development version").isNotNull()
                .isEqualTo(Boolean.valueOf(developmentVersion));
    }
}
