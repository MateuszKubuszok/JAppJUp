package com.autoupdater.client.models;

/**
 * Builder that creates BugEntry instances.
 * 
 * @see com.autoupdater.client.models.BugEntry
 */
public class BugEntryBuilder {
    private final BugEntry bugEntry;

    private BugEntryBuilder() {
        bugEntry = new BugEntry();
    }

    /**
     * Creates new BugEntryBuilder.
     * 
     * @return BugEntryBuilder
     */
    public static BugEntryBuilder builder() {
        return new BugEntryBuilder();
    }

    public BugEntryBuilder setDescription(String description) {
        bugEntry.setDescription(description);
        return this;
    }

    /**
     * Builds BugEntry.
     * 
     * @return BugEntry
     */
    public BugEntry build() {
        return bugEntry;
    }
}
