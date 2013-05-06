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

import static com.autoupdater.client.models.VersionNumber.version;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class TestPackageBuilder {
    @Test
    public void testBuilder() {
        // given
        String id = "id";
        String name = "some name";
        Update update = new Update();
        SortedSet<Update> updates = new TreeSet<Update>();
        updates.add(update);
        String versionNumber = "12.34.56.78";
        SortedSet<ChangelogEntry> changelog = new TreeSet<ChangelogEntry>(
                Arrays.asList(new ChangelogEntry()));

        // when
        Package _package = PackageBuilder.builder().setChangelog(changelog).setID(id).setName(name)
                .setUpdates(updates).setVersionNumber(versionNumber).build();

        // then
        assertThat(_package.getChangelog()).as("Builder should set name properly").isNotNull()
                .isEqualTo(changelog);
        assertThat(_package.getID()).as("Builder should set ID properly").isNotNull().isEqualTo(id);
        assertThat(_package.getName()).as("Builder should set name properly").isNotNull()
                .isEqualTo(name);
        assertThat(_package.getUpdates()).as("Builder should set update properly").isNotNull()
                .isEqualTo(updates);
        assertThat(_package.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version(versionNumber));
    }

    @Test
    public void testAlternativeSetters() {
        // given
        VersionNumber version = version(1, 2, 3, 4);

        // when
        Package _package = PackageBuilder.builder().setVersionNumber(version).build();

        // then
        assertThat(_package.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version);
    }
}
