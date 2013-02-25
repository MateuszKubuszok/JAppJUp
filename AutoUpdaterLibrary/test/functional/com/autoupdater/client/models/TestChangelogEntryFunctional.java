package com.autoupdater.client.models;

import static com.autoupdater.client.models.VersionNumber.version;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestChangelogEntryFunctional {
    @Test
    public void testSettersAndGetters() {
        // given
        ChangelogEntry changelog = new ChangelogEntry();
        String changes = "some changes";
        String versionNumber = "15.26.37.48";

        // when
        changelog.setChanges(changes);
        changelog.setVersionNumber(versionNumber);

        // then
        assertThat(changelog.getChanges()).as("Getter and setter should work for changes")
                .isEqualTo(changes);
        assertThat(changelog.getVersionNumber())
                .as("Getter and setter should work for version number").isNotNull()
                .isEqualTo(version(versionNumber));
    }

    @Test
    public void testAlternativeSetters() {
        // given
        ChangelogEntry changelog = new ChangelogEntry();
        VersionNumber version = VersionNumber.version(1, 2, 3, 4);

        // when
        changelog.setVersionNumber(version);

        // then
        assertThat(changelog.getVersionNumber())
                .as("Getter and setter should work for version number").isNotNull()
                .isEqualTo(version);
    }
}
