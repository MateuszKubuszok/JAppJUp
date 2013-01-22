package com.autoupdater.client.models;

/**
 * Builder that creates ChangelogEntry instances.
 * 
 * @see com.autoupdater.client.models.ChangelogEntry
 */
public class ChangelogEntryBuilder {
    private final ChangelogEntry changelogEntry;

    private ChangelogEntryBuilder() {
        changelogEntry = new ChangelogEntry();
    }

    /**
     * Creates new ChangelogEntryBuilder.
     * 
     * @return ChangelogEntryBuilder
     */
    public static ChangelogEntryBuilder builder() {
        return new ChangelogEntryBuilder();
    }

    public ChangelogEntryBuilder setDescription(String changes) {
        changelogEntry.setChanges(changes);
        return this;
    }

    public ChangelogEntryBuilder setVersionNumber(String versionNumber) {
        changelogEntry.setVersionNumber(versionNumber);
        return this;
    }

    public ChangelogEntryBuilder setVersionNumber(VersionNumber versionNumber) {
        changelogEntry.setVersionNumber(versionNumber);
        return this;
    }

    /**
     * Builds ChangelogEntry.
     * 
     * @return ChangelogEntry
     */
    public ChangelogEntry build() {
        return changelogEntry;
    }
}
