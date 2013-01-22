package com.autoupdater.installer.installation.strategies;

import static org.fest.assertions.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Test;

import com.autoupdater.installer.Paths;
import com.google.common.io.Files;

public class TestCopyInstallationStrategy {
    @Test
    public void testProcess() {
        try {
            // given
            String testContent = "some content";

            String sourcePath = Paths.Library.testDir + File.separator + "testExecuteDownload.txt";
            File source = new File(sourcePath);
            Files.createParentDirs(source);
            source.deleteOnExit();
            Files.write(testContent, source, Charset.defaultCharset());

            String destinationPath = Paths.Library.testDir + File.separator + "testProcess"
                    + File.separator + "testExecuteDownload.txt";
            File destination = new File(destinationPath);
            destination.deleteOnExit();

            // when
            new CopyInstallationStrategy().process(source, destinationPath);
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
}
