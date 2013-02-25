package com.autoupdater.client.models;

import static com.autoupdater.client.models.VersionNumber.version;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class TestPackageBuilder {
    @Test
    public void testBuilder() {
        // given
        String id = "id";
        String name = "some name";
        Update update = new Update();
        String versionNumber = "12.34.56.78";
        SortedSet<ChangelogEntry> changelog = new TreeSet<ChangelogEntry>(
                Arrays.asList(new ChangelogEntry()));

        // when
        Package _package = PackageBuilder.builder().setChangelog(changelog).setID(id).setName(name)
                .setUpdate(update).setVersionNumber(versionNumber).build();

        // then
        assertThat(_package.getChangelog()).as("Builder should set name properly").isNotNull()
                .isEqualTo(changelog);
        assertThat(_package.getID()).as("Builder should set ID properly").isNotNull().isEqualTo(id);
        assertThat(_package.getName()).as("Builder should set name properly").isNotNull()
                .isEqualTo(name);
        assertThat(_package.getUpdate()).as("Builder should set update properly").isNotNull()
                .isEqualTo(update);
        assertThat(_package.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version(versionNumber));
    }

    @Test
    public void testAlternativeSetters() {
        // given
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Package _package = PackageBuilder.builder().setVersionNumber(version).build();

        // then
        assertThat(_package.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version);
    }
}
