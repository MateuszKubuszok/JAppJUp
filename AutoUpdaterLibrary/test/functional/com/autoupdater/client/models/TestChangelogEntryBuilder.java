package com.autoupdater.client.models;

import static com.autoupdater.client.models.VersionNumber.version;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestChangelogEntryBuilder {
    @Test
    public void testBuilder() {
        // given
        String changes = "some changes";
        String versionNumber = "15.26.37.48";

        // when
        ChangelogEntry changelog = ChangelogEntryBuilder.builder().setDescription(changes)
                .setVersionNumber(versionNumber).build();

        // then
        assertThat(changelog.getChanges()).as("Builder should set changes properly").isEqualTo(
                changes);
        assertThat(changelog.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version(versionNumber));
    }

    @Test
    public void testAltenativeSetters() {
        // given
        VersionNumber version = VersionNumber.version(1, 2, 3, 4);

        // when
        ChangelogEntry changelog = ChangelogEntryBuilder.builder().setVersionNumber(version)
                .build();

        // then
        assertThat(changelog.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version);
    }
}
