package com.autoupdater.client.models;

import static com.autoupdater.client.models.VersionNumber.UNVERSIONED;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Comparator;

import org.junit.Test;

public class TestVersionNumber {
    @Test
    public void testConstructor() {
        // given
        int major = 127, minor = 0, release = 0, nightly = 1;
        String stringVersion = major + "." + minor + "." + release + "." + nightly;

        // when
        VersionNumber versionFromInts = new VersionNumber(major, minor, release, nightly);
        VersionNumber versionFromString = new VersionNumber(stringVersion);

        // then
        assertThat(versionFromInts.getMajor()).as(
                "VersionNumber integer constructor should set major number correctly").isEqualTo(
                major);
        assertThat(versionFromInts.getMinor()).as(
                "VersionNumber integer constructor should set major number correctly").isEqualTo(
                minor);
        assertThat(versionFromInts.getRelease()).as(
                "VersionNumber integer constructor should set major number correctly").isEqualTo(
                release);
        assertThat(versionFromInts.getNightly()).as(
                "VersionNumber integer constructor should set major number correctly").isEqualTo(
                nightly);
        assertThat(versionFromString.getMajor()).as(
                "VersionNumber string constructor should set major number correctly").isEqualTo(
                major);
        assertThat(versionFromString.getMinor()).as(
                "VersionNumber string constructor should set major number correctly").isEqualTo(
                minor);
        assertThat(versionFromString.getRelease()).as(
                "VersionNumber string constructor should set major number correctly").isEqualTo(
                release);
        assertThat(versionFromString.getNightly()).as(
                "VersionNumber string constructor should set major number correctly").isEqualTo(
                nightly);
    }

    @Test
    public void testEquals() {
        forEqualNumbersShouldBeEqual();
        forDifferentNumbersShouldNotBeEqual();
    }

    private void forEqualNumbersShouldBeEqual() {
        // given

        // when
        VersionNumber versionNumber1 = new VersionNumber(127, 0, 0, 1);
        VersionNumber versionNumber2 = new VersionNumber(127, 0, 0, 1);

        // then
        assertThat(versionNumber1).as("VersionNumber with the same number should be equal")
                .isEqualTo(versionNumber2);
    }

    private void forDifferentNumbersShouldNotBeEqual() {
        // given

        // when
        VersionNumber versionNumber1 = new VersionNumber(127, 0, 0, 1);
        VersionNumber versionNumber2 = new VersionNumber(127, 0, 0, 2);
        VersionNumber versionNumber3 = new VersionNumber(127, 0, 1, 1);
        VersionNumber versionNumber4 = new VersionNumber(127, 1, 0, 1);
        VersionNumber versionNumber5 = new VersionNumber(128, 0, 0, 1);

        // then
        assertThat(versionNumber1)
                .as("VersionNumber with the different number should not be equal")
                .isNotEqualTo(versionNumber2).isNotEqualTo(versionNumber3)
                .isNotEqualTo(versionNumber4).isNotEqualTo(versionNumber5);
    }

    @Test
    public void testCompareTo() {
        forEqualNumbersShouldBe0();
        forDifferentNumbersShouldNotBe0();
    }

    private void forEqualNumbersShouldBe0() {
        // given
        VersionNumber versionNumber1 = new VersionNumber(1, 2, 3, 4);
        VersionNumber versionNumber2 = new VersionNumber(1, 2, 3, 4);

        // when
        int comparison = versionNumber1.compareTo(versionNumber2);

        // then
        assertThat(comparison).as("compareTo should be 0 for equal VersionNumbers").isEqualTo(0);
    }

    private void forDifferentNumbersShouldNotBe0() {
        // given
        VersionNumber versionNumber1 = new VersionNumber(1, 2, 3, 4);
        VersionNumber versionNumber2 = new VersionNumber(2, 3, 4, 5);

        // when
        int comparison = versionNumber1.compareTo(versionNumber2);

        // then
        assertThat(comparison).as("compareTo should not be 0 for differenr VersionNumbers")
                .isLessThan(0);
    }

    @Test
    public void testGeneralComparator() {
        forEqualVersionNumbersShouldBe0();
        forDifferentVersionNumbersShouldNotBe0();
    }

    private void forEqualVersionNumbersShouldBe0() {
        // given

        // when
        VersionNumber version1 = VersionNumber.version(1, 2, 3, 4);
        VersionNumber version2 = VersionNumber.version(1, 2, 3, 4);
        Comparator<VersionNumber> comparator = new VersionNumber.GeneralComparator();

        // then
        assertThat(comparator.compare(version1, version2)).as(
                "For equal Version Numbers should be 0").isEqualTo(0);
        assertThat(comparator.compare(version2, version1)).as(
                "For equal Version Numbers should be 0").isEqualTo(0);
    }

    private void forDifferentVersionNumbersShouldNotBe0() {
        // given

        // when
        VersionNumber version1 = VersionNumber.version(1, 2, 3, 4);
        VersionNumber version2 = VersionNumber.version(1, 2, 3, 5);
        Comparator<VersionNumber> comparator = new VersionNumber.GeneralComparator();

        // then
        assertThat(comparator.compare(version1, version2)).as(
                "For different Version Numbers should not be 0").isLessThan(0);
        assertThat(comparator.compare(version2, version1)).as(
                "For different Version Numbers should not be 0").isGreaterThan(0);
    }

    @Test
    public void testGetInstallationsServerPropertiesComparator() {
        // given

        // when
        Comparator<VersionNumber> comparator = UNVERSIONED
                .getInstallationsServerPropertiesComparator();

        // then
        assertThat(comparator).as(
                "InstallationsServerPropertiesComparator is instance of GeneralComparator")
                .isInstanceOf(VersionNumber.GeneralComparator.class);
    }

    @Test
    public void testGetLocalInstallationsComparator() {
        // given

        // when
        Comparator<VersionNumber> comparator = UNVERSIONED.getLocalInstallationsComparator();

        // then
        assertThat(comparator).as("LocalInstallationsComparator is instance of GeneralComparator")
                .isInstanceOf(VersionNumber.GeneralComparator.class);
    }

    @Test
    public void testGetLocal2ServerComparator() {
        // given

        // when
        Comparator<VersionNumber> comparator = UNVERSIONED.getLocal2ServerComparator();

        // then
        assertThat(comparator).as("Local2ServerComparator is instance of GeneralComparator")
                .isInstanceOf(VersionNumber.GeneralComparator.class);
    }

    @Test
    public void testToString() {
        // given

        // when
        VersionNumber version = new VersionNumber(1, 2, 3, 4);

        // then
        assertThat(version.toString()).as("toString is not null");
    }
}
