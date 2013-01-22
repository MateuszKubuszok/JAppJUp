package com.autoupdater.client.download.runnables.post.download.strategies;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.ConnectionConfiguration;
import com.autoupdater.client.download.runnables.post.download.strategies.FilePostDownloadStrategy;
import com.google.common.io.Files;

public class TestFilePostDownloadStrategy {
    @Test
    public void testStrategy() throws IOException {
        // given
        File temp = new File(Paths.Library.testDir + File.separator + "fileDownloadStrategy.txt");
        Files.createParentDirs(temp);
        temp.createNewFile();
        temp.deleteOnExit();

        File result = null;
        temp.deleteOnExit();
        FilePostDownloadStrategy strategy = new FilePostDownloadStrategy(temp);
        String content = "some test content", contentWritten = "";

        // when
        strategy.write(content.getBytes(ConnectionConfiguration.XML_ENCODING), content.length());
        result = strategy.processDownload();
        if (result != null)
            result.deleteOnExit();
        contentWritten = Files.toString(temp, ConnectionConfiguration.XML_ENCODING);

        // then
        assertThat(result).exists().as("processDownload() should properly return File");
        assertThat(contentWritten).isEqualTo(content).as("strategy should properly write to file");
    }
}
