package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestBugEntryBuilder {
    @Test
    public void testBuilder() {
        // given
        String description = "Some bug description";

        // when
        BugEntry bug = BugEntryBuilder.builder().setDescription(description).build();

        // then
        assertThat(bug.getDescription()).as("Builder should set description properly").isEqualTo(
                description);
    }
}
