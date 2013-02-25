package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestBugEntryFunctional {
    @Test
    public void testSettersAndGetters() {
        // given
        BugEntry bug = new BugEntry();
        String description = "Some bug description";

        // when
        bug.setDescription(description);

        // then
        assertThat(bug.getDescription()).as("Getter and setter should work for description")
                .isNotNull().isEqualTo(description);
    }
}
