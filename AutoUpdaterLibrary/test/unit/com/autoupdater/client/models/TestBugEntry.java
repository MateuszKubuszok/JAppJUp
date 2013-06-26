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

import java.util.Comparator;

import org.junit.Test;

public class TestBugEntry {
    @Test
    public void testConstructor() {
        // given

        // when
        BugEntry bug = new BugEntry();

        // then
        assertThat(bug.getDescription()).as("Default description should be empty string")
                .isNotNull().isEmpty();
    }

    @Test
    public void testEquals() {
        forEqualAttributesShouldBeTrue();
        forNotEqualDescriptionsShouldBeFalse();
    }

    private void forEqualAttributesShouldBeTrue() {
        // given
        String description = "some description";
        BugEntry bug1 = new BugEntry();
        BugEntry bug2 = new BugEntry();

        // when
        bug1.setDescription(description);
        bug2.setDescription(description);

        // then
        assertThat(bug1).as("Bugs with equal attributes should be equal").isEqualTo(bug2);
        assertThat(bug2).as("Bugs with equal attributes should be equal").isEqualTo(bug1);
    }

    private void forNotEqualDescriptionsShouldBeFalse() {
        // given
        String description1 = "some description";
        String description2 = "some other description";
        BugEntry bug1 = new BugEntry();
        BugEntry bug2 = new BugEntry();

        // when
        bug1.setDescription(description1);
        bug2.setDescription(description2);

        // then
        assertThat(bug1).as("Bugs with different descriptions should not be equal").isNotEqualTo(
                bug2);
        assertThat(bug2).as("Bugs with different descriptions should not be equal").isNotEqualTo(
                bug1);
    }

    @Test
    public void testHashcode() {
        forEqualAttributesShouldHaveEqualHashcodes();
        forNotEqualsDescriptionsShouldHaveDifferentHashcodes();
    }

    private void forEqualAttributesShouldHaveEqualHashcodes() {
        // given
        String description = "some description";
        BugEntry bug1 = new BugEntry();
        BugEntry bug2 = new BugEntry();

        // when
        bug1.setDescription(description);
        bug2.setDescription(description);

        // then
        assertThat(bug1.hashCode()).as("Bugs with equal attributes should have equal hashcodes")
                .isEqualTo(bug2.hashCode());
    }

    private void forNotEqualsDescriptionsShouldHaveDifferentHashcodes() {
        // given
        String description1 = "some description";
        String description2 = "some other description";
        BugEntry bug1 = new BugEntry();
        BugEntry bug2 = new BugEntry();

        // when
        bug1.setDescription(description1);
        bug2.setDescription(description2);

        // then
        assertThat(bug1.hashCode()).as(
                "Bugs with different descriptions should have different hashcodes").isNotEqualTo(
                bug2.hashCode());
    }

    @Test
    public void testCompareTo() {
        forEqualAttributesShouldHaveCompareTosEqualTo0();
        forNotEqualDescriptionsShouldHaveCompareTosDifferentTo0();
    }

    private void forEqualAttributesShouldHaveCompareTosEqualTo0() {
        // given
        String description = "some description";
        BugEntry bug1 = new BugEntry();
        BugEntry bug2 = new BugEntry();

        // when
        bug1.setDescription(description);
        bug2.setDescription(description);

        // then
        assertThat(bug1.compareTo(bug2)).as(
                "Bugs with equal attributes should have compareTos equal to 0").isEqualTo(0);
        assertThat(bug2.compareTo(bug1)).as(
                "Bugs with equal attributes should have compareTos equal to 0").isEqualTo(0);
    }

    private void forNotEqualDescriptionsShouldHaveCompareTosDifferentTo0() {
        // given
        String description1 = "some description";
        String description2 = "some other description";
        BugEntry bug1 = new BugEntry();
        BugEntry bug2 = new BugEntry();

        // when
        bug1.setDescription(description1);
        bug2.setDescription(description2);

        // then
        assertThat(bug1.compareTo(bug2)).as(
                "Bugs with different descriptions should have compareTos of the descriptions")
                .isLessThan(0);
        assertThat(bug2.compareTo(bug1)).as(
                "Bugs with different descriptions should have compareTos of the descriptions")
                .isGreaterThan(0);
    }

    @Test
    public void testGeneralComparator() {
        forEqualAttributesShouldHaveGeneralComparatorsCompareTosEqualTo0();
        forNotEqualDescriptionsShouldHaveGeneralComparatorsCompareTosDifferentTo0();
    }

    private void forEqualAttributesShouldHaveGeneralComparatorsCompareTosEqualTo0() {
        // given
        String description = "some description";
        BugEntry bug1 = new BugEntry();
        BugEntry bug2 = new BugEntry();

        // when
        bug1.setDescription(description);
        bug2.setDescription(description);
        Comparator<BugEntry> comparator = new BugEntry.GeneralComparator();

        // then
        assertThat(comparator.compare(bug1, bug2)).as(
                "Bugs with equal attributes should have comparator's compareTos equal to 0")
                .isEqualTo(0);
        assertThat(comparator.compare(bug2, bug1)).as(
                "Bugs with equal attributes should have comparator's compareTos equal to 0")
                .isEqualTo(0);
    }

    private void forNotEqualDescriptionsShouldHaveGeneralComparatorsCompareTosDifferentTo0() {
        // given
        String description1 = "some description";
        String description2 = "some other description";
        BugEntry bug1 = new BugEntry();
        BugEntry bug2 = new BugEntry();

        // when
        bug1.setDescription(description1);
        bug2.setDescription(description2);
        Comparator<BugEntry> comparator = new BugEntry.GeneralComparator();

        // then
        assertThat(comparator.compare(bug1, bug2))
                .as("Bugs with different descriptions should have comparator's compareTos of the descriptions")
                .isLessThan(0);
        assertThat(comparator.compare(bug2, bug1))
                .as("Bugs with different descriptions should have comparator's compareTos of the descriptions")
                .isGreaterThan(0);
    }

    @Test
    public void testGetInstallationsServerPropertiesComparator() {
        // given

        // when
        Comparator<BugEntry> comparator = new BugEntry()
                .getInstallationsServerPropertiesComparator();

        // then
        assertThat(comparator).as(
                "InstallationsServerPropertiesComparator is instance of GeneralComparator")
                .isInstanceOf(BugEntry.GeneralComparator.class);
    }

    @Test
    public void testGetLocalInstallationsComparator() {
        // given

        // when
        Comparator<BugEntry> comparator = new BugEntry().getLocalInstallationsComparator();

        // then
        assertThat(comparator).as("LocalInstallationsComparator is instance of GeneralComparator")
                .isInstanceOf(BugEntry.GeneralComparator.class);
    }

    @Test
    public void testGetLocal2ServerComparator() {
        // given

        // when
        Comparator<BugEntry> comparator = new BugEntry().getLocal2ServerComparator();

        // then
        assertThat(comparator).as("Local2ServerComparator is instance of GeneralComparator")
                .isInstanceOf(BugEntry.GeneralComparator.class);
    }
}
