package com.autoupdater.client.models;

import static com.autoupdater.client.models.EUpdateStatus.NOT_SELECTED;
import static com.autoupdater.client.models.EUpdateStrategy.COPY;
import static com.autoupdater.client.models.VersionNumber.*;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Comparator;

import org.junit.Test;

public class TestUpdate {
    @Test
    public void testConstructor() {
        // given

        // when
        Update update = new Update();

        // then
        assertThat(update.getPackageID()).as("Update's Package ID should be empty string")
                .isNotNull().isEmpty();
        assertThat(update.getPackageName()).as("Update's Package Name should be empty string")
                .isNotNull().isEmpty();
        assertThat(update.getPackage()).as("Update's Package should be null").isNull();
        assertThat(update.getID()).as("Update's ID should be emty string").isNotNull().isEmpty();
        assertThat(update.getChanges()).as("Update's Changes should be empty string").isNotNull()
                .isEmpty();
        assertThat(update.getVersionNumber()).as("Update's Version Number should be UNVERSIONED")
                .isEqualTo(UNVERSIONED);
        assertThat(update.getUpdateStrategy()).as("Update's Strategy should be COPY").isEqualTo(
                COPY);
        assertThat(update.getOriginalName()).as("Update's Original Name should be empty string")
                .isNotNull().isEmpty();
        assertThat(update.getRelativePath()).as("Update's Relative Path should be empty string")
                .isNotNull().isEmpty();
        assertThat(update.getCommand()).as("Update's Command should be empty string").isNotNull()
                .isEmpty();
        assertThat(update.getFile()).as("Update's File should be null").isNull();
        assertThat(update.getStatus()).as("Update's Status should be NOT SELECTED").isEqualTo(
                NOT_SELECTED);
    }

    @Test
    public void testEquals() {
        forEqualUpdatesShouldBeEqual();
        forDifferentUpdatesShouldNotBeEqual();
    }

    private void forEqualUpdatesShouldBeEqual() {
        // given
        String packageName = "some name";
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Update update1 = UpdateBuilder.builder().setPackageName(packageName)
                .setVersionNumber(version).build();
        Update update2 = UpdateBuilder.builder().setPackageName(packageName)
                .setVersionNumber(version).build();

        // then
        assertThat(update1).as("For equal Updates should be equal").isEqualTo(update2);
        assertThat(update2).as("For equal Updates should be equal").isEqualTo(update1);
    }

    private void forDifferentUpdatesShouldNotBeEqual() {
        // given
        String packageName = "some name";
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Update update1 = UpdateBuilder.builder().setPackageName(packageName)
                .setVersionNumber(version).build();
        Update update2 = UpdateBuilder.builder().setPackageName("some other package")
                .setVersionNumber(version).build();

        // then
        assertThat(update1).as("For different Updates should not be equal").isNotEqualTo(update2);
        assertThat(update2).as("For different Updates should not be equal").isNotEqualTo(update1);
    }

    @Test
    public void testLocalInstallationsComparator() {
        forEqualProgramsLocalInstallationsComparatorShouldBe0();
        forDifferentProgramsLocalInstallationsComparatorShouldNotBe0();
    }

    private void forEqualProgramsLocalInstallationsComparatorShouldBe0() {
        // given
        String packageName = "some name";
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Update update1 = UpdateBuilder.builder().setPackageName(packageName)
                .setVersionNumber(version).build();
        Update update2 = UpdateBuilder.builder().setPackageName(packageName)
                .setVersionNumber(version).build();
        Comparator<Update> comparator = new Update.LocalInstallationsComparator();

        // then
        assertThat(comparator.compare(update1, update2)).as(
                "For equal Updates LocalInstallationsComparator should be 0").isEqualTo(0);
        assertThat(comparator.compare(update2, update1)).as(
                "For equal Updates LocalInstallationsComparator should be 0").isEqualTo(0);
    }

    private void forDifferentProgramsLocalInstallationsComparatorShouldNotBe0() {
        // given
        String packageName = "some name";
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Update update1 = UpdateBuilder.builder().setPackageName(packageName)
                .setVersionNumber(version).build();
        Update update2 = UpdateBuilder.builder().setPackageName("some other package")
                .setVersionNumber(version).build();
        Comparator<Update> comparator = new Update.LocalInstallationsComparator();

        // then
        assertThat(comparator.compare(update1, update2)).as(
                "For different Updates LocalInstallationsComparator should not be 0").isLessThan(0);
        assertThat(comparator.compare(update2, update1)).as(
                "For different Updates LocalInstallationsComparator should not be 0")
                .isGreaterThan(0);
    }

    @Test
    public void testLocal2ServerComparator() {
        forEqualProgramsLocal2ServerComparatorShouldBe0();
        forDifferentProgramsLocal2ServerComparatorShouldNotBe0();
    }

    private void forEqualProgramsLocal2ServerComparatorShouldBe0() {
        // given
        Program program = ProgramBuilder.builder().setName("some name")
                .setPathToProgramDirectory("/").setServerAddress("some address").build();
        Package _package = PackageBuilder.builder().setName("some package")
                .setVersionNumber(1, 2, 3, 4).setProgram(program).build();
        String packageName = "some name";
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Update update1 = UpdateBuilder.builder().setPackageName(packageName)
                .setDevelopmentVersion(true).setVersionNumber(version).setPackage(_package).build();
        Update update2 = UpdateBuilder.builder().setPackageName(packageName)
                .setDevelopmentVersion(true).setVersionNumber(version).setPackage(_package).build();
        Comparator<Update> comparator = new Update.Local2ServerComparator();

        // then
        assertThat(comparator.compare(update1, update2)).as(
                "For equal Updates Local2ServerComparator should be 0").isEqualTo(0);
        assertThat(comparator.compare(update2, update1)).as(
                "For equal Updates Local2ServerComparator should be 0").isEqualTo(0);
    }

    private void forDifferentProgramsLocal2ServerComparatorShouldNotBe0() {
        // given
        Program program = ProgramBuilder.builder().setName("some name")
                .setPathToProgramDirectory("/").setServerAddress("some address").build();
        Package _package = PackageBuilder.builder().setName("some package")
                .setVersionNumber(1, 2, 3, 4).setProgram(program).build();
        String packageName = "some name";
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Update update1 = UpdateBuilder.builder().setPackageName(packageName)
                .setDevelopmentVersion(false).setVersionNumber(version).setPackage(_package)
                .build();
        Update update2 = UpdateBuilder.builder().setPackageName(packageName)
                .setDevelopmentVersion(true).setVersionNumber(version).setPackage(_package).build();
        Comparator<Update> comparator = new Update.Local2ServerComparator();

        // then
        assertThat(comparator.compare(update1, update2)).as(
                "For different Updates Local2ServerComparator should not be 0").isLessThan(0);
        assertThat(comparator.compare(update2, update1)).as(
                "For different Updates Local2ServerComparator should not be 0").isGreaterThan(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetInstallationsServerPropertiesComparator() {
        // given

        // when
        new Update().getInstallationsServerPropertiesComparator();

        // then
        // UnsupportedOperationException
    }

    @Test
    public void testGetLocalInstallationsComparator() {
        // given

        // when
        Comparator<Update> comparator = new Update().getLocalInstallationsComparator();

        // then
        assertThat(comparator).as(
                "LocalInstallationsComparator is instance of LocalInstallationsComparator")
                .isInstanceOf(Update.LocalInstallationsComparator.class);
    }

    @Test
    public void testGetLocal2ServerComparator() {
        // given

        // when
        Comparator<Update> comparator = new Update().getLocal2ServerComparator();

        // then
        assertThat(comparator).as("Local2ServerComparator is instance of Local2ServerComparator")
                .isInstanceOf(Update.Local2ServerComparator.class);
    }
}
