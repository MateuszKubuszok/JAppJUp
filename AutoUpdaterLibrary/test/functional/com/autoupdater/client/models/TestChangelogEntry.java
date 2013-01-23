package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestChangelogEntry {
    @Test
    public void testConstructor() {
        // given
        String changes = "some changes";
        String versionNumber = "15.26.37.48";

        // when
        ChangelogEntry changelog = ChangelogEntryBuilder.builder().setDescription(changes)
                .setVersionNumber(versionNumber).build();

        // then
        assertThat(changelog.getChanges()).as("Constructor should set changes properly").isEqualTo(
                changes);
        assertThat(changelog.getVersionNumber())
                .as("Constructor should set version number properly").isNotNull()
                .isInstanceOf(VersionNumber.class);
    }
}
