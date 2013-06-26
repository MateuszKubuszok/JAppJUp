package com.autoupdater.client.models;

import static com.autoupdater.client.models.VersionNumber.*;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Comparator;

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
        assertThat(package2).as("Packages with equal properites should be equal").isEqualTo(
                package1);
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
        assertThat(package2).as("Packages with equal properites should be equal").isNotEqualTo(
                package1);
    }

    @Test
    public void testCreationHelperComparator() {
        forEqualPackagesCreationHelperComparatorShouldBe0();
        forDifferentPackagesCreationHelperComparatorNotShouldBe0();
    }

    private void forEqualPackagesCreationHelperComparatorShouldBe0() {
        // given
        String name = "some name";

        // when
        Package package1 = PackageBuilder.builder().setName(name).build();
        Package package2 = PackageBuilder.builder().setName(name).build();
        Comparator<Package> comparator = new Package.CreationHelperComparator();

        // then
        assertThat(comparator.compare(package1, package2)).as(
                "For equal Packages CreationHelperComparator should be 0").isEqualTo(0);
        assertThat(comparator.compare(package2, package1)).as(
                "For equal Packages CreationHelperComparator should be 0").isEqualTo(0);
    }

    private void forDifferentPackagesCreationHelperComparatorNotShouldBe0() {
        // given
        String name = "some name";

        // when
        Package package1 = PackageBuilder.builder().setName(name).build();
        Package package2 = PackageBuilder.builder().setName("some other name").build();
        Comparator<Package> comparator = new Package.CreationHelperComparator();

        // then
        assertThat(comparator.compare(package1, package2)).as(
                "For equal Packages CreationHelperComparator should not be equal to 0").isLessThan(
                0);
        assertThat(comparator.compare(package2, package1)).as(
                "For equal Packages CreationHelperComparator should not be equal to 0")
                .isGreaterThan(0);
    }

    @Test
    public void testInnerConsistencyComparator() {
        forEqualPackagesInnerConsistencyComparatorShouldBe0();
        forDifferentPackagesInnerConsistencyComparatorNotShouldBe0();
    }

    private void forEqualPackagesInnerConsistencyComparatorShouldBe0() {
        // given
        Program program = ProgramBuilder.builder().setName("some name")
                .setPathToProgramDirectory("some/path").setServerAddress("some address").build();
        String name = "some name";

        // when
        Package package1 = PackageBuilder.builder().setName(name).setProgram(program).build();
        Package package2 = PackageBuilder.builder().setName(name).setProgram(program).build();
        Comparator<Package> comparator = new Package.InnerConsistencyComparator();

        // then
        assertThat(comparator.compare(package1, package2)).as(
                "For equal Packages InnerConsistencyComparator should be 0").isEqualTo(0);
        assertThat(comparator.compare(package2, package1)).as(
                "For equal Packages InnerConsistencyComparator should be 0").isEqualTo(0);
    }

    private void forDifferentPackagesInnerConsistencyComparatorNotShouldBe0() {
        // given
        Program program = ProgramBuilder.builder().setName("some name")
                .setPathToProgramDirectory("some/path").setServerAddress("some address").build();
        String name = "some name";

        // when
        Package package1 = PackageBuilder.builder().setName(name).setProgram(program).build();
        Package package2 = PackageBuilder.builder().setName("some other name").setProgram(program)
                .build();
        Comparator<Package> comparator = new Package.InnerConsistencyComparator();

        // then
        assertThat(comparator.compare(package1, package2)).as(
                "For equal Packages CreationHelperComparator should not be equal to 0").isLessThan(
                0);
        assertThat(comparator.compare(package2, package1)).as(
                "For equal Packages CreationHelperComparator should not be equal to 0")
                .isGreaterThan(0);
    }

    @Test
    public void testOuterMatchingComparator() {
        forEqualPackagesOuterMatchingComparatorShouldBe0();
        forDifferentPackagesOuterMatchingComparatorNotShouldBe0();
    }

    private void forEqualPackagesOuterMatchingComparatorShouldBe0() {
        // given
        String name = "some name";

        // when
        Package package1 = PackageBuilder.builder().setName(name).build();
        Package package2 = PackageBuilder.builder().setName(name).build();
        Comparator<Package> comparator = new Package.OuterMatchingComparator();

        // then
        assertThat(comparator.compare(package1, package2)).as(
                "For equal Packages OuterMatchingComparator should be 0").isEqualTo(0);
        assertThat(comparator.compare(package2, package1)).as(
                "For equal Packages OuterMatchingComparator should be 0").isEqualTo(0);
    }

    private void forDifferentPackagesOuterMatchingComparatorNotShouldBe0() {
        // given
        String name = "some name";

        // when
        Package package1 = PackageBuilder.builder().setName(name).build();
        Package package2 = PackageBuilder.builder().setName("some other name").build();
        Comparator<Package> comparator = new Package.OuterMatchingComparator();

        // then
        assertThat(comparator.compare(package1, package2)).as(
                "For equal Packages OuterMatchingComparator should not be equal to 0")
                .isLessThan(0);
        assertThat(comparator.compare(package2, package1)).as(
                "For equal Packages OuterMatchingComparator should not be equal to 0")
                .isGreaterThan(0);
    }
}
