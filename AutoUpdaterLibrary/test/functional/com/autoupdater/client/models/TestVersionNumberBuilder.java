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

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestVersionNumberBuilder {
    @Test
    public void testBuilder() {
        // given
        int major = 15, minor = 26, release = 37, nightly = 48;
        String versionNumberString = major + "." + minor + "." + release + "." + nightly;

        // when
        VersionNumber versionNumber = new VersionNumber(versionNumberString);

        // then
        assertThat(versionNumber.getMajor()).as("Builder should set major version number properly")
                .isEqualTo(major);
        assertThat(versionNumber.getMinor()).as("Builder should set minor version number properly")
                .isEqualTo(minor);
        assertThat(versionNumber.getRelease()).as(
                "Builder should set release version number properly").isEqualTo(release);
        assertThat(versionNumber.getNightly()).as(
                "Builder should set nightly version number properly").isEqualTo(nightly);
    }

    @Test
    public void testAlternativeSetters() {
        // given
        int major = 15, minor = 26, release = 37, nightly = 48;

        // when
        VersionNumber versionNumber = new VersionNumber(major, minor, release, nightly);

        // then
        assertThat(versionNumber.getMajor()).as("Builder should set major version number properly")
                .isEqualTo(major);
        assertThat(versionNumber.getMinor()).as("Builder should set minor version number properly")
                .isEqualTo(minor);
        assertThat(versionNumber.getRelease()).as(
                "Builder should set release version number properly").isEqualTo(release);
        assertThat(versionNumber.getNightly()).as(
                "Builder should set nightly version number properly").isEqualTo(nightly);
    }
}
