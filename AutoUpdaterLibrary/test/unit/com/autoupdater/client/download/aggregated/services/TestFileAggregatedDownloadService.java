package com.autoupdater.client.download.aggregated.services;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.UpdateBuilder;

public class TestFileAggregatedDownloadService {
    @Test
    public void testService() throws MalformedURLException {
        try {
            // given
            FileAggregatedDownloadService aggregatedService = new FileAggregatedDownloadService();

            String content = "some content";
            String filePath1 = Paths.Library.testDir + File.separator
                    + "testFileDownloadAggregatedService1.xml";
            aggregatedService.addService(new FileDownloadService(new HttpURLConnectionMock(new URL(
                    "http://127.0.0.1"), content), filePath1), UpdateBuilder.builder().setID("1")
                    .setPackageName("Name").setVersionNumber("1.0.0.0").setDevelopmentVersion(true)
                    .setOriginalName("name.zip").setRelativePath("/").setUpdateStrategy("unzip")
                    .build());

            content = "some other";
            String filePath2 = Paths.Library.testDir + File.separator
                    + "testFileDownloadAggregatedService2.xml";
            aggregatedService.addService(new FileDownloadService(new HttpURLConnectionMock(new URL(
                    "http://127.0.0.1"), content), filePath2), UpdateBuilder.builder().setID("2")
                    .setPackageName("Name").setVersionNumber("2.0.0.0")
                    .setDevelopmentVersion(false).setOriginalName("name.zip").setRelativePath("/")
                    .setUpdateStrategy("copy").build());

            SortedSet<Update> result = null;

            // when
            aggregatedService.start();
            aggregatedService.joinThread();
            result = aggregatedService.getResult();
            for (Update update : result)
                update.getFile().deleteOnExit();

            // then
            assertThat(result).as("getResult() should aggregate results from all services")
                    .isNotNull().hasSize(2);
        } catch (DownloadResultException e) {
            fail("getResult() should not throw exception when result is ready, and without errors");
        }
    }
}
