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
package com.autoupdater.client.installation.runnable;

import static com.autoupdater.client.models.EUpdateStatus.SELECTED;
import static java.io.File.separator;
import static net.jsdpu.JavaSystemUtils.getJavaExecutablePath;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.process.executors.InvalidCommandException;

import org.junit.Test;

import com.autoupdater.client.AbstractTest;
import com.autoupdater.client.Paths;
import com.autoupdater.client.Values;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.PackageBuilder;
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.UpdateBuilder;

public class TestCommandGenerationHelper extends AbstractTest {
    @Test
    public void testGetSingleUpdateExecutionCommand() throws ProgramSettingsNotFoundException,
            InvalidCommandException {
        // given
        Update update = getUpdate();
        SortedSet<Update> updates = new TreeSet<Update>();
        updates.add(update);

        // when
        CommandGenerationHelper commandGenerationHelper = new CommandGenerationHelper(
                environmentData());
        String[] updateCommand = commandGenerationHelper.getSingleUpdateExecutionCommand(update);

        // then
        assertThat(updateCommand)
                .as("getSingleUpdateExecutionCommand() should return a correct single installation command")
                .isNotNull()
                .isEqualTo(
                        new String[] { getJavaExecutablePath(), "-jar",
                                Paths.Library.installerPath, update.getUniqueIdentifer(), "unzip",
                                update.getFile().getAbsolutePath(),
                                Paths.Installations.Program.programDir });
    }

    @Test
    public void testGetUpdateExecutionCommands() throws ProgramSettingsNotFoundException,
            InvalidCommandException {
        // given
        SortedSet<Update> updates = new TreeSet<Update>();

        Update update0 = getUpdate();
        updates.add(update0);

        Update update1 = UpdateBuilder.builder().setID("7").setPackageName("other")
                .setPackageID("4").setVersionNumber("13.6.8.9").setDevelopmentVersion(true)
                .setChanges("changes").setUpdateStrategy("copy").setOriginalName("name.zip")
                .setRelativePath("/").setCommand("").build();
        update1.setPackage(update0.getPackage());
        update1.setFile(new File(Paths.Library.testDir + File.separator + "update2.txt"));
        updates.add(update1);

        update0.setStatus(SELECTED);
        update1.setStatus(SELECTED);

        // when
        CommandGenerationHelper commandGenerationHelper = new CommandGenerationHelper(
                environmentData());
        List<String[]> updateCommand = commandGenerationHelper.getUpdateExecutionCommands(updates);

        // then
        assertThat(updateCommand)
                .as("getUpdateExecutionCommands should return one command for each update")
                .isNotNull().hasSize(2);
        assertThat(updateCommand.get(0))
                .as("getUpdateExecutionCommands should create commands properly")
                .isNotNull()
                .isEqualTo(
                        new String[] { getJavaExecutablePath(), "-jar",
                                Paths.Library.installerPath, update0.getUniqueIdentifer(), "unzip",
                                Paths.Library.testDir + separator + "update.txt",
                                Paths.Installations.Program.programDir });
        assertThat(updateCommand.get(1))
                .as("getUpdateExecutionCommands should create commands properly")
                .isNotNull()
                .isEqualTo(
                        new String[] {
                                getJavaExecutablePath(),
                                "-jar",
                                Paths.Library.installerPath,
                                update1.getUniqueIdentifer(),
                                "copy",
                                Paths.Library.testDir + File.separator + "update2.txt",
                                Paths.Installations.Program.programDir + separator
                                        + update1.getOriginalName() });
    }

    private Update getUpdate() {
        SortedSet<Package> packages = new TreeSet<Package>();
        packages.add(PackageBuilder.builder().setName(Values.Package.name).setID("1").build());

        String programName = Values.Program.name;
        String pathToDir = Paths.Installations.Program.programDir;
        String serverAddress = Values.ProgramSettings.serverAddress;
        ProgramBuilder.builder().setName(programName).setPathToProgramDirectory(pathToDir)
                .setServerAddress(serverAddress).setPackages(packages).build();

        String packageName = Values.Package.name;
        String packageId = "2";
        String versionNumber = "1.5.7.0";
        String developmentVersion = "false";
        String updateId = "2";
        String changes = "some changes";
        String strategy = "unzip";
        String originalName = "name2.zip";
        String relativePath = "/";
        String command = "";

        return UpdateBuilder.builder().setID(updateId).setPackageName(packageName)
                .setPackageID(packageId).setVersionNumber(versionNumber)
                .setDevelopmentVersion(developmentVersion).setChanges(changes)
                .setUpdateStrategy(strategy).setOriginalName(originalName)
                .setRelativePath(relativePath).setCommand(command).setPackage(packages.first())
                .setFile(new File(Paths.Library.testDir + separator + "update.txt")).build();

    }
}
