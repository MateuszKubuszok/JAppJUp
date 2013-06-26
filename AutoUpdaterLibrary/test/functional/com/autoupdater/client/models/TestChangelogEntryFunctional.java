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
        VersionNumber version = version(1, 2, 3, 4);

        // when
        changelog.setVersionNumber(version);

        // then
        assertThat(changelog.getVersionNumber())
                .as("Getter and setter should work for version number").isNotNull()
                .isEqualTo(version);
    }
}
