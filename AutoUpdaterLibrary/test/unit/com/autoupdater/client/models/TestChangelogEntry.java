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

import static com.autoupdater.client.models.VersionNumber.*;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Comparator;

import org.junit.Test;

public class TestChangelogEntry {
    @Test
    public void testConstructor() {
        // given

        // when
        ChangelogEntry changelog = new ChangelogEntry();

        // then
        assertThat(changelog.getChanges()).as("Default changes should be empty string").isNotNull()
                .isEmpty();
        assertThat(changelog.getVersionNumber()).as("Default version number should be 0.0.0.0")
                .isNotNull().isEqualTo(UNVERSIONED);
    }

    @Test
    public void testEquals() {
        forEqualAttributesShouldBeTrue();
        forDifferentChangesShouldBeFalse();
        forDifferentVersionNumberShouldBeFalse();
    }

    private void forEqualAttributesShouldBeTrue() {
        // given
        String changes = "some changes";
        VersionNumber version = version(1, 2, 3, 4);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes);
        changelog2.setChanges(changes);
        changelog1.setVersionNumber(version);
        changelog2.setVersionNumber(version);

        // then
        assertThat(changelog1).as("Changelogs with equal attributes should be equal").isEqualTo(
                changelog2);
        assertThat(changelog2).as("Changelogs with equal attributes should be equal").isEqualTo(
                changelog1);
    }

    private void forDifferentChangesShouldBeFalse() {
        // given
        String changes1 = "some changes";
        String changes2 = "some other changes";
        VersionNumber version = version(1, 2, 3, 4);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes1);
        changelog2.setChanges(changes2);
        changelog1.setVersionNumber(version);
        changelog2.setVersionNumber(version);

        // then
        assertThat(changelog1).as("Changelogs with different changes should not be equal")
                .isNotEqualTo(changelog2);
        assertThat(changelog2).as("Changelogs with different changes should not be equal")
                .isNotEqualTo(changelog1);
    }

    private void forDifferentVersionNumberShouldBeFalse() {
        // given
        String changes = "some changes";
        VersionNumber version1 = version(1, 2, 3, 4);
        VersionNumber version2 = version(5, 6, 7, 8);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes);
        changelog2.setChanges(changes);
        changelog1.setVersionNumber(version1);
        changelog2.setVersionNumber(version2);

        // then
        assertThat(changelog1).as("Changelogs with different version numbers should not be equal")
                .isNotEqualTo(changelog2);
        assertThat(changelog2).as("Changelogs with different version numbers should not be equal")
                .isNotEqualTo(changelog1);
    }

    @Test
    public void testHashcode() {
        forEqualAttributesShouldHaveEqualHashcodes();
        forDifferentChangesShouldHaveDifferentHashcodes();
        forDifferentVersionNumberShouldHaveDifferentHashcodes();
    }

    private void forEqualAttributesShouldHaveEqualHashcodes() {
        // given
        String changes = "some changes";
        VersionNumber version = version(1, 2, 3, 4);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes);
        changelog2.setChanges(changes);
        changelog1.setVersionNumber(version);
        changelog2.setVersionNumber(version);

        // then
        assertThat(changelog1.hashCode()).as(
                "Changelogs with equal attributes should have equal hashcodes").isEqualTo(
                changelog2.hashCode());
    }

    private void forDifferentChangesShouldHaveDifferentHashcodes() {
        // given
        String changes1 = "some changes";
        String changes2 = "some other changes";
        VersionNumber version = version(1, 2, 3, 4);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes1);
        changelog2.setChanges(changes2);
        changelog1.setVersionNumber(version);
        changelog2.setVersionNumber(version);

        // then
        assertThat(changelog1.hashCode()).as(
                "Changelogs with different changes should have different hashcodes").isNotEqualTo(
                changelog2.hashCode());
    }

    private void forDifferentVersionNumberShouldHaveDifferentHashcodes() {
        // given
        String changes = "some changes";
        VersionNumber version1 = version(1, 2, 3, 4);
        VersionNumber version2 = version(5, 6, 7, 8);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes);
        changelog2.setChanges(changes);
        changelog1.setVersionNumber(version1);
        changelog2.setVersionNumber(version2);

        // then
        assertThat(changelog1.hashCode()).as(
                "Changelogs with different version numbers should have different hashcodes")
                .isNotEqualTo(changelog2.hashCode());
    }

    @Test
    public void testCompareTo() {
        forEqualAttributesShouldHaveCompareTosEqualTo0();
        forDifferentChangesShouldHaveCompareTosDifferentTo0();
        forDifferentVersionNumberShouldHaveCompareTosDifferentTo0();
    }

    private void forEqualAttributesShouldHaveCompareTosEqualTo0() {
        // given
        String changes = "some changes";
        VersionNumber version = version(1, 2, 3, 4);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes);
        changelog2.setChanges(changes);
        changelog1.setVersionNumber(version);
        changelog2.setVersionNumber(version);

        // then
        assertThat(changelog1.compareTo(changelog2)).as(
                "Changelogs with equal attributes should have compareTos equal to 0").isEqualTo(0);
        assertThat(changelog2.compareTo(changelog1)).as(
                "Changelogs with equal attributes should have compareTos equal to 0").isEqualTo(0);
    }

    private void forDifferentChangesShouldHaveCompareTosDifferentTo0() {
        // given
        String changes1 = "some changes";
        String changes2 = "some other changes";
        VersionNumber version = version(1, 2, 3, 4);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes1);
        changelog2.setChanges(changes2);
        changelog1.setVersionNumber(version);
        changelog2.setVersionNumber(version);

        // then
        assertThat(changelog1.compareTo(changelog2)).as(
                "Changelogs with different changes should have compareTos different to 0")
                .isLessThan(0);
        assertThat(changelog2.compareTo(changelog1)).as(
                "Changelogs with different changes should have compareTos different to 0")
                .isGreaterThan(0);
    }

    private void forDifferentVersionNumberShouldHaveCompareTosDifferentTo0() {
        // given
        String changes = "some changes";
        VersionNumber version1 = version(1, 2, 3, 4);
        VersionNumber version2 = version(5, 6, 7, 8);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes);
        changelog2.setChanges(changes);
        changelog1.setVersionNumber(version1);
        changelog2.setVersionNumber(version2);

        // then
        assertThat(changelog1.compareTo(changelog2)).as(
                "Changelogs with different version numbers should have compareTos different to 0")
                .isLessThan(0);
        assertThat(changelog2.compareTo(changelog1)).as(
                "Changelogs with different version numbers should have compareTos different to 0")
                .isGreaterThan(0);
    }

    @Test
    public void testGeneralComparator() {
        forEqualAttributesShouldHaveGeneralComparatorsCompareTosEqualTo0();
        forDifferentChangesShouldHaveGeneralComparatorsCompareTosDifferentTo0();
        forDifferentVersionNumberShouldHaveGeneralComparatorsCompareTosDifferentTo0();
    }

    private void forEqualAttributesShouldHaveGeneralComparatorsCompareTosEqualTo0() {
        // given
        String changes = "some changes";
        VersionNumber version = version(1, 2, 3, 4);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes);
        changelog2.setChanges(changes);
        changelog1.setVersionNumber(version);
        changelog2.setVersionNumber(version);
        Comparator<ChangelogEntry> comparator = new ChangelogEntry.GeneralComparator();

        // then
        assertThat(comparator.compare(changelog1, changelog2)).as(
                "Changelogs with equal attributes should have comparator's compareTos equal to 0")
                .isEqualTo(0);
        assertThat(comparator.compare(changelog2, changelog1)).as(
                "Changelogs with equal attributes should have comparator's compareTos equal to 0")
                .isEqualTo(0);
    }

    private void forDifferentChangesShouldHaveGeneralComparatorsCompareTosDifferentTo0() {
        // given
        String changes1 = "some changes";
        String changes2 = "some other changes";
        VersionNumber version = version(1, 2, 3, 4);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes1);
        changelog2.setChanges(changes2);
        changelog1.setVersionNumber(version);
        changelog2.setVersionNumber(version);
        Comparator<ChangelogEntry> comparator = new ChangelogEntry.GeneralComparator();

        // then
        assertThat(comparator.compare(changelog1, changelog2))
                .as("Changelogs with different changes should have comparator's compareTos different to 0")
                .isLessThan(0);
        assertThat(comparator.compare(changelog2, changelog1))
                .as("Changelogs with different changes should have comparator's compareTos different to 0")
                .isGreaterThan(0);
    }

    private void forDifferentVersionNumberShouldHaveGeneralComparatorsCompareTosDifferentTo0() {
        // given
        String changes = "some changes";
        VersionNumber version1 = version(1, 2, 3, 4);
        VersionNumber version2 = version(5, 6, 7, 8);
        ChangelogEntry changelog1 = new ChangelogEntry();
        ChangelogEntry changelog2 = new ChangelogEntry();

        // when
        changelog1.setChanges(changes);
        changelog2.setChanges(changes);
        changelog1.setVersionNumber(version1);
        changelog2.setVersionNumber(version2);
        Comparator<ChangelogEntry> comparator = new ChangelogEntry.GeneralComparator();

        // then
        assertThat(comparator.compare(changelog1, changelog2))
                .as("Changelogs with different version numbers should have comparator's compareTos different to 0")
                .isLessThan(0);
        assertThat(comparator.compare(changelog2, changelog1))
                .as("Changelogs with different version numbers should have comparator's compareTos different to 0")
                .isGreaterThan(0);
    }

    @Test
    public void testGetInstallationsServerPropertiesComparator() {
        // given

        // when
        Comparator<ChangelogEntry> comparator = new ChangelogEntry()
                .getInstallationsServerPropertiesComparator();

        // then
        assertThat(comparator).as(
                "InstallationsServerPropertiesComparator is instance of GeneralComparator")
                .isInstanceOf(ChangelogEntry.GeneralComparator.class);
    }

    @Test
    public void testGetLocalInstallationsComparator() {
        // given

        // when
        Comparator<ChangelogEntry> comparator = new ChangelogEntry()
                .getLocalInstallationsComparator();

        // then
        assertThat(comparator).as("LocalInstallationsComparator is instance of GeneralComparator")
                .isInstanceOf(ChangelogEntry.GeneralComparator.class);
    }

    @Test
    public void testGetLocal2ServerComparator() {
        // given

        // when
        Comparator<ChangelogEntry> comparator = new ChangelogEntry().getLocal2ServerComparator();

        // then
        assertThat(comparator).as("Local2ServerComparator is instance of GeneralComparator")
                .isInstanceOf(ChangelogEntry.GeneralComparator.class);
    }
}
