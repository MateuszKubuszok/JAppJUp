package com.autoupdater.client.installation.runnable;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.autoupdater.client.AbstractTest;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.PackageBuilder;
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.UpdateBuilder;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.autoupdater.commons.messages.EInstallerMessage;

public class TestInstallationOutputReadHelper extends AbstractTest {
    private Update update;
    private EUpdateStatus status;

    @Test
    public void testParseResults() {
        // given
        SortedSet<Update> updates = new TreeSet<Update>();
        updates.add(getUpdate());

        String result = "[info] " + updates.first().getUniqueIdentifer() + ": "
                + EInstallerMessage.INSTALLATION_FINISHED;

        // when
        status = null;
        new InstallersOutputParser().parseInstallersOutput(updates, executionQueueReader(result));

        // then
        assertThat(update).as("parseResults() should change Update's status").isNotNull();
        assertThat(update.getStatus()).as(
                "parseResults() should change installed Update's status to INSTALLED").isEqualTo(
                EUpdateStatus.INSTALLED);
        assertThat(status).as("parseResults() should set Update's status as message").isNotNull()
                .isEqualTo(update.getStatus());
    }

    private Update getUpdate() {
        SortedSet<Package> packages = new TreeSet<Package>();
        packages.add(PackageBuilder.builder().setName("some package").setID("1").build());

        String programName = "some name";
        String pathToDir = "C:\\program";
        String serverAddress = "updateserver.com";
        ProgramBuilder.builder().setName(programName).setPathToProgramDirectory(pathToDir)
                .setServerAddress(serverAddress).setPackages(packages).build();

        String packageName = "some package";
        String packageId = "2";
        String versionNumber = "1.5.7.0";
        String developmentVersion = "false";
        String updateId = "2";
        String changes = "some changes";
        String strategy = "unzip";
        String originalName = "name.zip";
        String relativePath = "/";
        String command = "";
        Update update = UpdateBuilder.builder().setID(updateId).setPackageName(packageName)
                .setPackageID(packageId).setVersionNumber(versionNumber)
                .setDevelopmentVersion(developmentVersion).setChanges(changes)
                .setUpdateStrategy(strategy).setOriginalName(originalName)
                .setRelativePath(relativePath).setCommand(command).setPackage(packages.first())
                .setStatus(EUpdateStatus.SELECTED).setFile(new File("C:\\someupdate.zip")).build();
        update.addObserver(new UpdateObserver());

        return update;
    }

    private class UpdateObserver implements IObserver<EUpdateStatus> {
        @Override
        public void update(ObservableService<EUpdateStatus> observable, EUpdateStatus message) {
            update = (Update) observable;
            status = message;
        }
    }
}
