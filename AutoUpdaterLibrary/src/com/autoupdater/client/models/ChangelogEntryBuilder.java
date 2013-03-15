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
