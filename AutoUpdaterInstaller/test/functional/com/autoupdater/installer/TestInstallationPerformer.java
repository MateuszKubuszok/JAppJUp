package com.autoupdater.installer;

import static org.fest.assertions.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.jsdpu.EOperatingSystem;

import org.junit.Test;

import com.autoupdater.commons.error.codes.EErrorCode;
import com.google.common.io.Files;

public class TestInstallationPerformer {
    @Test
    public void testCopy() {
        try {
            // given
            String testContent = "some content";

            String sourcePath = Paths.Library.testDir + File.separator + "testCopyInstall.txt";
            File source = new File(sourcePath);
            Files.createParentDirs(source);
            source.deleteOnExit();
            Files.write(testContent, source, Charset.defaultCharset());

            String destinationPath = Paths.Library.testDir + File.separator + "testProcess"
                    + File.separator + "testCopyInstall.txt";
            File destination = new File(destinationPath);
            destination.deleteOnExit();

            String[] args = { "1", "copy", sourcePath, destinationPath };

            // when
            new InstallationPerformer().install(args);
            File destinationTest = new File(destinationPath);
            destinationTest.deleteOnExit();

            // then
            assertThat(destinationTest).as(
                    "process(File,File) should copy file to a selected location").exists();
            assertThat(Files.readFirstLine(destinationTest, Charset.defaultCharset())).as(
                    "process(File,File) should copy file's content").isEqualTo(testContent);
        } catch (IOException e) {
            fail("process(File,File) should not throw exception while working on accessible files");
        }
    }

    @Test
    public void testExecute() {
        try {
            // given
            String testCommand = EOperatingSystem.current().getTestCommand();

            String sourcePath = Paths.Library.testDir + File.separator + "testExecuteInstall.txt";
            File source = new File(sourcePath);
            Files.createParentDirs(source);
            source.createNewFile();
            source.deleteOnExit();

            String[] args = { "2", "execute", sourcePath, testCommand };

            // when
            EErrorCode result = new InstallationPerformer().install(args);

            // then
            assertThat(result).as("process(File,File) should execute command properly").isEqualTo(
                    EErrorCode.SUCCESS);
        } catch (IOException e) {
            fail("process(File,File) should not throw exception while working on accessible files");
        }
    }

    @Test
    public void testUnzip() {
        try {
            // given
            String testContent = "some test content";

            String sourcePath = Paths.Library.testDir + File.separator + "testZipInstall.zip";
            File source = new File(sourcePath);
            Files.createParentDirs(source);
            source.createNewFile();
            source.deleteOnExit();

            ZipOutputStream zus = new ZipOutputStream(new FileOutputStream(source));
            zus.putNextEntry(new ZipEntry("testZipInstall.txt"));
            zus.write(testContent.getBytes());
            zus.closeEntry();
            zus.close();

            String destinationDirectoryPath = Paths.Library.testDir + File.separator
                    + "testProcess";
            File destinationDirectory = new File(destinationDirectoryPath);
            destinationDirectory.deleteOnExit();

            String[] args = { "3", "unzip", sourcePath, destinationDirectoryPath };

            // when
            new InstallationPerformer().install(args);
            File destination = new File(destinationDirectoryPath + File.separator
                    + "testZipInstall.txt");

            // then
            assertThat(destination).as(
                    "process(File,File) should unzip file to a selected location").exists();
            assertThat(Files.toString(destination, Charset.defaultCharset())).as(
                    "process(File,File) should contains file's extracted content").isEqualTo(
                    testContent);
        } catch (IOException e) {
            fail("process(File,File) should not throw exception while working on accessible files");
        }
    }
}
