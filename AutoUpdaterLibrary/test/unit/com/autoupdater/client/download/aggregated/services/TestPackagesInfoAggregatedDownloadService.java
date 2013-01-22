package com.autoupdater.client.download.aggregated.services;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.PackagesInfoDownloadService;
import com.autoupdater.client.models.Program;

public class TestPackagesInfoAggregatedDownloadService {
    @Test
    public void testService() throws MalformedURLException {
        // given
        PackagesInfoAggregatedDownloadService aggregatedService = new PackagesInfoAggregatedDownloadService();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<programs>"
                        + ("<program name=\"Program 1\">"
                                + "<package name=\"Package 1\" id=\"1\" />"
                                + "<package name=\"Package 2\" id=\"2\" />" + "</program>")
                        + "</programs>");
        aggregatedService.addService(new PackagesInfoDownloadService(new HttpURLConnectionMock(
                new URL("http://127.0.0.1"), xml)));

        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<programs>"
                        + ("<program name=\"Program 2\">"
                                + "<package name=\"Package 3\" id=\"3\" />" + "</program>")
                        + "</programs>");
        aggregatedService.addService(new PackagesInfoDownloadService(new HttpURLConnectionMock(
                new URL("http://127.0.0.1"), xml)));

        SortedSet<Program> result = null;
        boolean exceptionThrown = false;

        // when
        aggregatedService.start();
        aggregatedService.joinThread();
        try {
            result = aggregatedService.getResult();
        } catch (DownloadResultException e) {
            exceptionThrown = true;
        }

        // then
        assertThat(exceptionThrown).as(
                "getResult() should not throw exception when result is ready, and without errors")
                .isFalse();
        assertThat(result).as("getResult() should aggregate results from all services").isNotNull()
                .hasSize(2);
    }
}
