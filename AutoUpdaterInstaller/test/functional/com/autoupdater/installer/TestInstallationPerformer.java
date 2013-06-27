package com.autoupdater.installer;

import static com.autoupdater.commons.error.codes.EErrorCode.SUCCESS;
import static com.google.common.io.Files.*;
import static java.io.File.separator;
import static java.nio.charset.Charset.defaultCharset;
import static net.jsdpu.EOperatingSystem.currentOperatingSystem;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

import com.autoupdater.commons.error.codes.EErrorCode;
import com.google.common.io.Files;

public class TestInstallationPerformer {
    @Test
    public void testCopy() throws IOException {
        // given
        String testContent = "some content";

        String sourcePath = Paths.Library.testDir + separator + "testCopyInstall.txt";
        File source = new File(sourcePath);
        createParentDirs(source);
        source.deleteOnExit();
        write(testContent, source, defaultCharset());

        String destinationPath = Paths.Library.testDir + separator + "testProcess" + separator
                + "testCopyInstall.txt";
        File destination = new File(destinationPath);
        destination.deleteOnExit();

        String[] args = { "1", "copy", sourcePath, destinationPath };

        // when
        new InstallationPerformer().install(args);
        File destinationTest = new File(destinationPath);
        destinationTest.deleteOnExit();

        // then
        assertThat(destinationTest)
                .as("process(File,File) should copy file to a selected location").exists();
        assertThat(readFirstLine(destinationTest, defaultCharset())).as(
                "process(File,File) should copy file's content").isEqualTo(testContent);
    }

    @Test
    public void testExecute() throws IOException {
        // given
        String testCommand = currentOperatingSystem().getTestCommand();

        String sourcePath = Paths.Library.testDir + separator + "testExecuteInstall.txt";
        File source = new File(sourcePath);
        createParentDirs(source);
        source.createNewFile();
        source.deleteOnExit();

        String[] args = { "2", "execute", sourcePath, testCommand };

        // when
        EErrorCode result = new InstallationPerformer().install(args);

        // then
        assertThat(result).as("process(File,File) should execute command properly").isEqualTo(
                SUCCESS);
    }

    @Test
    public void testUnzip() throws IOException {
        // given
        String testContent = "some test content";

        String sourcePath = Paths.Library.testDir + separator + "testZipInstall.zip";
        File source = new File(sourcePath);
        createParentDirs(source);
        source.createNewFile();
        source.deleteOnExit();

        ZipOutputStream zus = new ZipOutputStream(new FileOutputStream(source));
        zus.putNextEntry(new ZipEntry("testZipInstall.txt"));
        zus.write(testContent.getBytes());
        zus.closeEntry();
        zus.close();

        String destinationDirectoryPath = Paths.Library.testDir + separator + "testProcess";
        File destinationDirectory = new File(destinationDirectoryPath);
        destinationDirectory.deleteOnExit();

        String[] args = { "3", "unzip", sourcePath, destinationDirectoryPath };

        // when
        new InstallationPerformer().install(args);
        File destination = new File(destinationDirectoryPath + separator + "testZipInstall.txt");

        // then
        assertThat(destination).as("process(File,File) should unzip file to a selected location")
                .exists();
        assertThat(Files.toString(destination, defaultCharset())).as(
                "process(File,File) should contains file's extracted content").isEqualTo(
                testContent);
    }
}
