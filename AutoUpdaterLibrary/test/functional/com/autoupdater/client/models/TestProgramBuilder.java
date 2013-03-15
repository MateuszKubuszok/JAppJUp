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
package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class TestProgramBuilder {
    @Test
    public void testBuilder() {
        // given
        String name = "some program";
        String pathToDirectory = "/path";
        String serverAddress = "http://address.com";
        boolean developmentVersion = true;
        String packageName = "some package";
        Package _package = PackageBuilder.builder().setName(packageName)
                .setVersionNumber("1.0.0.0").build();
        SortedSet<Package> packages = new TreeSet<Package>();
        packages.add(_package);
        String bugDescription = "some description";
        BugEntry bug = BugEntryBuilder.builder().setDescription(bugDescription).build();
        SortedSet<BugEntry> bugs = new TreeSet<BugEntry>();
        bugs.add(bug);

        // when
        Program program = ProgramBuilder.builder().setName(name)
                .setPathToProgramDirectory(pathToDirectory).setServerAddress(serverAddress)
                .setDevelopmentVersion(developmentVersion).setPackages(packages).setBugs(bugs)
                .build();

        // then
        assertThat(program.getName()).as("Builder should set program name properly").isNotNull()
                .isEqualTo(name);
        assertThat(program.getPathToProgramDirectory())
                .as("Builder should set program directory properly").isNotNull()
                .isEqualTo(pathToDirectory);
        assertThat(program.getServerAddress()).as("Builder should set server address properly")
                .isNotNull().isEqualTo(serverAddress);
        assertThat(program.isDevelopmentVersion())
                .as("Builder should set development version properly").isNotNull()
                .isEqualTo(developmentVersion);
        assertThat(program.getPackages()).as("Builder should set packages properly").isNotNull()
                .isNotEmpty();
        assertThat(program.getPackages().first()).as("Builder should set packages properly")
                .isNotNull().isEqualTo(_package);
        assertThat(program.getBugs()).as("Builder should set bugs properly").isNotNull()
                .isNotEmpty();
        assertThat(program.getBugs().first()).as("Builder should set bugs properly").isNotNull()
                .isEqualTo(bug);
    }

    @Test
    public void testAlternativeSetters() {
        // given
        String developmentVersion = "true";

        // when
        Program program = ProgramBuilder.builder().setDevelopmentVersion(developmentVersion)
                .build();

        // then
        assertThat(program.isDevelopmentVersion())
                .as("Builder should set development version properly").isNotNull()
                .isEqualTo(Boolean.valueOf(developmentVersion));
    }
}
