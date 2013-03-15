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
import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class TestPackageFunctional {
    @Test
    public void testSettersAndGetters() {
        // given
        Package _package = new Package();
        String id = "id";
        String name = "some name";
        Update update = new Update();
        String versionNumber = "12.34.56.78";
        SortedSet<ChangelogEntry> changelog = new TreeSet<ChangelogEntry>(
                asList(new ChangelogEntry()));

        // when
        _package.setChangelog(changelog);
        _package.setID(id);
        _package.setName(name);
        _package.setUpdate(update);
        _package.setVersionNumber(versionNumber);

        // then
        assertThat(_package.getChangelog()).as("Getter and setter should work for changelog")
                .isNotNull().isEqualTo(changelog);
        assertThat(_package.getID()).as("Getter and setter should work for ID").isNotNull()
                .isEqualTo(id);
        assertThat(_package.getName()).as("Getter and setter should work for name").isNotNull()
                .isEqualTo(name);
        assertThat(_package.getUpdate()).as("Getter and setter should work for update").isNotNull()
                .isEqualTo(update);
        assertThat(_package.getVersionNumber())
                .as("Getter and setter should work for version number").isNotNull()
                .isEqualTo(version(versionNumber));
    }

    @Test
    public void testAlternativeSetters() {
        // given
        Package _package = new Package();
        VersionNumber version = version(1, 2, 3, 4);

        // when
        _package.setVersionNumber(version);

        // then
        assertThat(_package.getVersionNumber())
                .as("Getter and setter should work for version number").isNotNull()
                .isEqualTo(version);
    }
}
