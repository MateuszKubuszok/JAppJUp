package com.autoupdater.client.models;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestUpdate {
    @Test
    public void testContructor() {
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
        String command = "";

        // when
        Update update = UpdateBuilder.builder().setID(updateId).setPackageName(packageName)
                .setPackageID(packageId).setVersionNumber(versionNumber).setChanges(changes)
                .setDevelopmentVersion(developmentVersion).setUpdateStrategy(strategy)
                .setOriginalName(originalName).setRelativePath(relativePath).setCommand(command)
                .build();

        // then
        assertThat(update.getPackageName()).as("Constructor should set package name properly")
                .isNotNull().isEqualTo(packageName);
        assertThat(update.getPackageID()).as("Constructor should set package ID properly")
                .isNotNull().isEqualTo(packageId);
        assertThat(update.getVersionNumber()).as("Constructor should set version number properly")
                .isNotNull().isInstanceOf(VersionNumber.class);
        assertThat(update.isDevelopmentVersion()).as(
                "Constructor should set development version properly").isTrue();
        assertThat(update.getID()).as("Constructor should set update ID properly").isNotNull()
                .isEqualTo(updateId);
        assertThat(update.getUpdateStrategy())
                .as("Constructor should set update strategy properly").isNotNull()
                .isEqualTo(EUpdateStrategy.UNZIP);
        assertThat(update.getChanges()).as("Constructor should set changes properly").isNotNull()
                .isEqualTo(changes);
        assertThat(update.getOriginalName()).as("Constructor should set original name properly")
                .isNotNull().isEqualTo(originalName);
        assertThat(update.getRelativePath()).as("Constructor should set relative path properly")
                .isNotNull().isEqualTo("");
        assertThat(update.getCommand()).as("Constructor should set command properly").isNotNull()
                .isEqualTo(command);
    }
}
