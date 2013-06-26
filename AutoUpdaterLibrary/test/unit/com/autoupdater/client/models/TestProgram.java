package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Comparator;

import org.junit.Test;

public class TestProgram {
    @Test
    public void testConstructor() {
        // given

        // when
        Program program = new Program();

        // then
        assertThat(program.getName()).as("Program's name should be an empty string").isNotNull()
                .isEmpty();
        assertThat(program.getPathToProgramDirectory())
                .as("Program's path to directory should be an empty string").isNotNull().isEmpty();
        assertThat(program.getServerAddress())
                .as("Program's server address should be an empty string").isNotNull().isEmpty();
        assertThat(program.getBugs()).as("Program's Bugs should be an empty collection")
                .isNotNull().isEmpty();
        assertThat(program.getPackages()).as("Program's Packages should be an empty collection")
                .isNotNull().isEmpty();
        assertThat(program.isDevelopmentVersion()).as("Program's version type should release")
                .isFalse();
    }

    @Test
    public void testEquals() {
        forEqualPropertiesShouldBeEqual();
        forDifferentPropertiesShouldNotBeEqual();
    }

    private void forEqualPropertiesShouldBeEqual() {
        // given
        String name = "name";
        String pathToDirectory = "some/path";
        String serverAddress = "some/address";
        Program program1 = new Program();
        Program program2 = new Program();

        // when
        program1.setName(name);
        program2.setName(name);
        program1.setPathToProgramDirectory(pathToDirectory);
        program2.setPathToProgramDirectory(pathToDirectory);
        program1.setServerAddress(serverAddress);
        program2.setServerAddress(serverAddress);

        // then
        assertThat(program1).as("Programs should be equal when their properties are equal")
                .isEqualTo(program2);
        assertThat(program2).as("Programs should be equal when their properties are equal")
                .isEqualTo(program1);
    }

    private void forDifferentPropertiesShouldNotBeEqual() {
        // given
        String name = "name";
        String pathToDirectory = "some/path";
        String serverAddress = "some/address";
        Program program1 = new Program();
        Program program2 = new Program();
        Program program3 = new Program();
        Program program4 = new Program();

        // when
        program1.setName(name);
        program2.setName("other name");
        program3.setName(name);
        program4.setName(name);
        program1.setPathToProgramDirectory(pathToDirectory);
        program2.setPathToProgramDirectory(pathToDirectory);
        program3.setPathToProgramDirectory("other/path");
        program4.setPathToProgramDirectory(pathToDirectory);
        program1.setServerAddress(serverAddress);
        program2.setServerAddress(serverAddress);
        program3.setServerAddress(serverAddress);
        program4.setServerAddress("other/address");

        // then
        assertThat(program1).as("Programs should not be equal when their properties are different")
                .isNotEqualTo(program2).isNotEqualTo(program3).isNotEqualTo(program4);
        assertThat(program2).as("Programs should not be equal when their properties are different")
                .isNotEqualTo(program1).isNotEqualTo(program3).isNotEqualTo(program4);
    }

    @Test
    public void testInstallationsServerPropertiesComparator() {
        forEqualProgramsInstallationsServerPropertiesComparatorShouldBe0();
        forDifferentProgramsInstallationsServerPropertiesComparatorShouldNotBe0();
    }

    private void forEqualProgramsInstallationsServerPropertiesComparatorShouldBe0() {
        // given
        String name = "name";
        String pathToDirectory = "some/path";
        String serverAddress = "some/address";
        Program program1 = new Program();
        Program program2 = new Program();

        // when
        program1.setName(name);
        program2.setName(name);
        program1.setPathToProgramDirectory(pathToDirectory);
        program2.setPathToProgramDirectory(pathToDirectory);
        program1.setServerAddress(serverAddress);
        program2.setServerAddress(serverAddress);
        Comparator<Program> comparator = new Program.InstallationsServerPropertiesComparator();

        // then
        assertThat(comparator.compare(program1, program2)).as(
                "InstallationsServerPropertiesComparator should be 0 for equal Programs")
                .isEqualTo(0);
        assertThat(comparator.compare(program2, program1)).as(
                "InstallationsServerPropertiesComparator should be 0 for equal Programs")
                .isEqualTo(0);
    }

    private void forDifferentProgramsInstallationsServerPropertiesComparatorShouldNotBe0() {
        // given
        String name = "name";
        String pathToDirectory = "some/path";
        String serverAddress = "some/address";
        Program program1 = new Program();
        Program program2 = new Program();

        // when
        program1.setName(name);
        program2.setName("other name");
        program1.setPathToProgramDirectory(pathToDirectory);
        program2.setPathToProgramDirectory(pathToDirectory);
        program1.setServerAddress(serverAddress);
        program2.setServerAddress(serverAddress);
        Comparator<Program> comparator = new Program.InstallationsServerPropertiesComparator();

        // then
        assertThat(comparator.compare(program1, program2)).as(
                "InstallationsServerPropertiesComparator should not be 0 for different Programs")
                .isLessThan(0);
        assertThat(comparator.compare(program2, program1)).as(
                "InstallationsServerPropertiesComparator should not be 0 for different Programs")
                .isGreaterThan(0);
    }

    @Test
    public void testLocalInstallationsComparator() {
        forEqualProgramsLocalInstallationsComparatorShouldBe0();
        forDifferentProgramsLocalInstallationsComparatorShouldNotBe0();
    }

    private void forEqualProgramsLocalInstallationsComparatorShouldBe0() {
        // given
        String name = "name";
        String pathToDirectory = "some/path";
        String serverAddress = "some/address";
        Program program1 = new Program();
        Program program2 = new Program();

        // when
        program1.setName(name);
        program2.setName(name);
        program1.setPathToProgramDirectory(pathToDirectory);
        program2.setPathToProgramDirectory(pathToDirectory);
        program1.setServerAddress(serverAddress);
        program2.setServerAddress(serverAddress);
        Comparator<Program> comparator = new Program.LocalInstallationsComparator();

        // then
        assertThat(comparator.compare(program1, program2)).as(
                "LocalInstallationsComparator should be 0 for equal Programs").isEqualTo(0);
        assertThat(comparator.compare(program2, program1)).as(
                "LocalInstallationsComparator should be 0 for equal Programs").isEqualTo(0);
    }

    private void forDifferentProgramsLocalInstallationsComparatorShouldNotBe0() {
        // given
        String name = "name";
        String pathToDirectory = "some/path";
        String serverAddress = "some/address";
        Program program1 = new Program();
        Program program2 = new Program();

        // when
        program1.setName(name);
        program2.setName("other name");
        program1.setPathToProgramDirectory(pathToDirectory);
        program2.setPathToProgramDirectory(pathToDirectory);
        program1.setServerAddress(serverAddress);
        program2.setServerAddress(serverAddress);
        Comparator<Program> comparator = new Program.LocalInstallationsComparator();

        // then
        assertThat(comparator.compare(program1, program2)).as(
                "LocalInstallationsComparator should not be 0 for different Programs")
                .isLessThan(0);
        assertThat(comparator.compare(program2, program1)).as(
                "LocalInstallationsComparator should not be 0 for different Programs")
                .isGreaterThan(0);
    }

    @Test
    public void testLocal2ServerComparator() {
        forEqualProgramsLocal2ServerComparatorShouldBe0();
        forDifferentProgramsLocal2ServerComparatorShouldNotBe0();
    }

    private void forEqualProgramsLocal2ServerComparatorShouldBe0() {
        // given
        String name = "name";
        String pathToDirectory = "some/path";
        String serverAddress = "some/address";
        Program program1 = new Program();
        Program program2 = new Program();

        // when
        program1.setName(name);
        program2.setName(name);
        program1.setPathToProgramDirectory(pathToDirectory);
        program2.setPathToProgramDirectory("other path");
        program1.setServerAddress(serverAddress);
        program2.setServerAddress("other server");
        Comparator<Program> comparator = new Program.Local2ServerComparator();

        // then
        assertThat(comparator.compare(program1, program2)).as(
                "Local2ServerComparator should be 0 for equal Programs").isEqualTo(0);
        assertThat(comparator.compare(program2, program1)).as(
                "Local2ServerComparator should be 0 for equal Programs").isEqualTo(0);
    }

    private void forDifferentProgramsLocal2ServerComparatorShouldNotBe0() {
        // given
        String name = "name";
        String pathToDirectory = "some/path";
        String serverAddress = "some/address";
        Program program1 = new Program();
        Program program2 = new Program();

        // when
        program1.setName(name);
        program2.setName("other name");
        program1.setPathToProgramDirectory(pathToDirectory);
        program2.setPathToProgramDirectory(pathToDirectory);
        program1.setServerAddress(serverAddress);
        program2.setServerAddress(serverAddress);
        Comparator<Program> comparator = new Program.Local2ServerComparator();

        // then
        assertThat(comparator.compare(program1, program2)).as(
                "Local2ServerComparator should not be 0 for different Programs").isLessThan(0);
        assertThat(comparator.compare(program2, program1)).as(
                "Local2ServerComparator should not be 0 for different Programs").isGreaterThan(0);
    }
}
