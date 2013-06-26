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

import static com.autoupdater.client.models.EUpdateStrategy.EXECUTE;
import static com.autoupdater.client.models.VersionNumber.version;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

import com.autoupdater.client.utils.enums.Enums;

public class TestUpdateBuilder {
    @Test
    public void testBuilder() {
        // given
        String packageName = "some package name";
        String packageId = "1";
        String versionNumber = "23.45.78.00";
        String developmentVersion = "true";
        String updateId = "2";
        String changes = "some changes";
        String strategy = "unzip";
        String originalName = "name.zip";
        String relativePath = "/";
        String command = "some command";
        Package _package = PackageBuilder.builder().setName(packageName)
                .setVersionNumber("1.0.0.0").build();
        File file = new File("/");

        // when
        Update update = UpdateBuilder.builder().setID(updateId).setPackageName(packageName)
                .setPackageID(packageId).setPackage(_package).setVersionNumber(versionNumber)
                .setChanges(changes).setDevelopmentVersion(developmentVersion)
                .setUpdateStrategy(strategy).setOriginalName(originalName)
                .setRelativePath(relativePath).setCommand(command).setFile(file).build();

        // then
        assertThat(update.getPackageName()).as("Builder should set package name properly")
                .isNotNull().isEqualTo(packageName);
        assertThat(update.getPackageID()).as("Builder should set package ID properly").isNotNull()
                .isEqualTo(packageId);
        assertThat(update.getPackage()).as("Builder should set Package properly").isNotNull()
                .isEqualTo(_package);
        assertThat(update.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version(versionNumber));
        assertThat(update.isDevelopmentVersion()).as(
                "Builder should set development version properly").isEqualTo(
                Boolean.valueOf(developmentVersion));
        assertThat(update.getID()).as("Builder should set update ID properly").isNotNull()
                .isEqualTo(updateId);
        assertThat(update.getUpdateStrategy()).as("Builder should set update strategy properly")
                .isNotNull().isEqualTo(Enums.parseMessage(EUpdateStrategy.class, strategy));
        assertThat(update.getChanges()).as("Builder should set changes properly").isNotNull()
                .isEqualTo(changes);
        assertThat(update.getOriginalName()).as("Builder should set original name properly")
                .isNotNull().isEqualTo(originalName);
        assertThat(update.getRelativePath()).as("Builder should set relative path properly")
                .isNotNull().isEqualTo("");
        assertThat(update.getCommand()).as("Builder should set command properly").isNotNull()
                .isEqualTo(command);
        assertThat(update.getFile()).as("Builder should set file properly").isNotNull()
                .isEqualTo(file);
    }

    @Test
    public void testAlternativeSetters() {
        // given
        VersionNumber version = VersionNumber.version(23, 47, 78, 0);
        boolean developmentVersion = true;
        EUpdateStrategy strategy = EXECUTE;

        // when
        Update update = UpdateBuilder.builder().setVersionNumber(version)
                .setDevelopmentVersion(developmentVersion).setUpdateStrategy(strategy).build();
        // then
        assertThat(update.getVersionNumber()).as("Builder should set version number properly")
                .isNotNull().isEqualTo(version);
        assertThat(update.isDevelopmentVersion()).as(
                "Builder should set development version properly").isEqualTo(developmentVersion);
        assertThat(update.getUpdateStrategy()).as("Builder should set update strategy properly")
                .isNotNull().isEqualTo(strategy);
    }
}
