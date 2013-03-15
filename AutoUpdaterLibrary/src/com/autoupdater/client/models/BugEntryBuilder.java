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
