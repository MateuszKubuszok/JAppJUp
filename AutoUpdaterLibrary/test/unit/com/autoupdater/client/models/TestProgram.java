package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class TestProgram {
    @Test
    public void testConstructor() {
        // given
        String name = "some program";
        String packageName = "some package";

        // when
        SortedSet<Package> packages = new TreeSet<Package>();
        packages.add(PackageBuilder.builder().setName(packageName).setVersionNumber("1.0.0.0")
                .build());

        Program program = ProgramBuilder.builder().setName(name).setPackages(packages).build();
        List<Package> ppackages = new ArrayList<Package>(program.getPackages());

        // then
        assertThat(program.getName()).as("Constructor should set program name properly")
                .isNotNull().isEqualTo(name);
        assertThat(program.getPackages()).as("Constructor should set packages properly")
                .isNotNull().isNotEmpty();
        assertThat(ppackages.get(0).getName()).as("Constructor should set package name properly")
                .isNotNull().isEqualTo(packageName);
        assertThat(ppackages.get(0).getVersionNumber())
                .as("Constructor should set version number properly").isNotNull()
                .isEqualTo(new VersionNumber(1, 0, 0, 0));
    }
}
