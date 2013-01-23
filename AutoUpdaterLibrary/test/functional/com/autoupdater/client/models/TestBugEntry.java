package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestBugEntry {
    @Test
    public void testConstructor() {
        // given
        String description = "Some byg description";

        // when
        BugEntry bug = BugEntryBuilder.builder().setDescription(description).build();

        // then
        assertThat(bug.getDescription()).as("Constructor should set description properly")
                .isEqualTo(description);
    }
}
