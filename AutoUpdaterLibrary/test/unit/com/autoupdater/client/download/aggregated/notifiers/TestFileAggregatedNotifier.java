package com.autoupdater.client.download.aggregated.notifiers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.autoupdater.client.Paths;
import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;

public class TestFileAggregatedNotifier {
    private String recievedMessage;

    @Test
    public void testNotifier() throws MalformedURLException {
        // given
        FileAggregatedDownloadService aggregatedService = new FileAggregatedDownloadService();

        String content = "some content";
        aggregatedService.addService(new FileDownloadService(new HttpURLConnectionMock(new URL(
                "http://127.0.0.1"), content), Paths.Library.testDir + File.separator
                + "TestFileAggreagatedDownload1.txt"));

        content = "some other";
        aggregatedService.addService(new FileDownloadService(new HttpURLConnectionMock(new URL(
                "http://127.0.0.1"), content), Paths.Library.testDir + File.separator
                + "TestFileAggreagatedDownload2.txt"));

        FileAggregatedNotifier listener = aggregatedService.getNotifier();
        listener.addObserver(new ListenerObserver());

        // when
        aggregatedService.start();
        aggregatedService.joinThread();

        // then
        assertThat(recievedMessage)
                .as("FileDownloadAggregatedNotifier should inform about finishing of all downloads")
                .isNotNull().isEqualTo(EDownloadStatus.PROCESSED.getMessage());
    }

    private class ListenerObserver implements IObserver<DownloadServiceMessage> {
        @Override
        public void update(ObservableService<DownloadServiceMessage> observable,
                DownloadServiceMessage message) {
            recievedMessage = message.getMessage();
        }
    }
}
