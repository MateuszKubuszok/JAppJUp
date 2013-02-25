package com.autoupdater.client.models;

import static com.autoupdater.client.models.VersionNumber.version;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

import com.autoupdater.client.utils.enums.Enums;

public class TestUpdateFunctional {
    @Test
    public void testSettersAndGetters() {
        // given
        Update update = new Update();
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
        update.setPackageName(packageName);
        update.setPackageID(packageId);
        update.setPackage(_package);
        update.setVersionNumber(versionNumber);
        update.setDevelopmentVersion(developmentVersion);
        update.setID(updateId);
        update.setChanges(changes);
        update.setUpdateStrategy(strategy);
        update.setOriginalName(originalName);
        update.setRelativePath(relativePath);
        update.setCommand(command);
        update.setFile(file);

        // then
        assertThat(update.getPackageName()).as("Getter and setter should work for package name")
                .isNotNull().isEqualTo(packageName);
        assertThat(update.getPackageID()).as("Getter and setter should work for package ID")
                .isNotNull().isEqualTo(packageId);
        assertThat(update.getPackage()).as("Getter and setter should work for Package").isNotNull()
                .isEqualTo(_package);
        assertThat(update.getVersionNumber())
                .as("Getter and setter should work for version number").isNotNull()
                .isEqualTo(version(versionNumber));
        assertThat(update.isDevelopmentVersion()).as(
                "Getter and setter should work for development version").isEqualTo(
                Boolean.valueOf(developmentVersion));
        assertThat(update.getID()).as("Getter and setter should work for update ID").isNotNull()
                .isEqualTo(updateId);
        assertThat(update.getUpdateStrategy())
                .as("Getter and setter should work for update strategy").isNotNull()
                .isEqualTo(Enums.parseMessage(EUpdateStrategy.class, strategy));
        assertThat(update.getChanges()).as("Getter and setter should work for changes").isNotNull()
                .isEqualTo(changes);
        assertThat(update.getOriginalName()).as("Getter and setter should work for original name")
                .isNotNull().isEqualTo(originalName);
        assertThat(update.getRelativePath()).as("Getter and setter should work for relative path")
                .isNotNull().isEqualTo("");
        assertThat(update.getCommand()).as("Getter and setter should work for command").isNotNull()
                .isEqualTo(command);
        assertThat(update.getFile()).as("Getter and setter should work for file").isNotNull()
                .isEqualTo(file);
    }

    @Test
    public void testAlternativeSetters() {
        // given
        Update update = new Update();
        VersionNumber version = version(23, 47, 78, 0);
        boolean developmentVersion = true;
        EUpdateStrategy strategy = EUpdateStrategy.EXECUTE;

        // when
        update.setVersionNumber(version);
        update.setDevelopmentVersion(developmentVersion);
        update.setUpdateStrategy(strategy);

        // then
        assertThat(update.getVersionNumber())
                .as("Getter and setter should work for version number").isNotNull()
                .isEqualTo(version);
        assertThat(update.isDevelopmentVersion()).as(
                "Getter and setter should work for development version").isEqualTo(
                developmentVersion);
        assertThat(update.getUpdateStrategy())
                .as("Getter and setter should work for update strategy").isNotNull()
                .isEqualTo(strategy);
    }
}
