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

public class TestChangelogEntryBuilder {
    @Test
    public void testBuilder() {
        // given
        String changes = "some changes";
        String versionNumber = "15.26.37.48";

        // when
        ChangelogEntry changelog = ChangelogEntryBuilder.builder().setDescription(changes)
                .setVersionNumber(versionNumber).build();

        // then
        assertThat(changelog.getChanges()).as("Builder should set changes properly").isEqualTo(
                changes);
        assertThat(changelog.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version(versionNumber));
    }

    @Test
    public void testAltenativeSetters() {
        // given
        VersionNumber version = VersionNumber.version(1, 2, 3, 4);

        // when
        ChangelogEntry changelog = ChangelogEntryBuilder.builder().setVersionNumber(version)
                .build();

        // then
        assertThat(changelog.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version);
    }
}
