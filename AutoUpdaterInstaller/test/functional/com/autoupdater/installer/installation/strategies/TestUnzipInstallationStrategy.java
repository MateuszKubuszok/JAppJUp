package com.autoupdater.installer.installation.strategies;

import static org.fest.assertions.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

import com.autoupdater.installer.Paths;
import com.google.common.io.Files;

public class TestUnzipInstallationStrategy {
    @Test
    public void testProcess() {
        try {
            // given
            String testContent = "some test content";

            String sourcePath = Paths.Library.testDir + File.separator + "testZip.zip";
            File source = new File(sourcePath);
            Files.createParentDirs(source);
            source.createNewFile();
            source.deleteOnExit();

            ZipOutputStream zus = new ZipOutputStream(new FileOutputStream(source));
            zus.putNextEntry(new ZipEntry("testZip.txt"));
            zus.write(testContent.getBytes());
            zus.closeEntry();
            zus.close();

            String destinationDirectoryPath = Paths.Library.testDir + File.separator
                    + "testProcess";
            File destinationDirectory = new File(destinationDirectoryPath);
            destinationDirectory.deleteOnExit();

            // when
            new UnzipInstallationStrategy().process(source, destinationDirectoryPath);
            File destination = new File(destinationDirectoryPath + File.separator + "testZip.txt");

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
