package com.autoupdater.client.download.aggregated.notifiers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.aggregated.notifiers.ChangelogInfoAggregatedNotifier;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.ChangelogInfoDownloadService;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;

public class TestChangelogInfoAggregatedNotifier {
    private String recievedMessage;

    @Test
    public void testNotifier() throws MalformedURLException {
        // given
        ChangelogInfoAggregatedDownloadService aggregatedService = new ChangelogInfoAggregatedDownloadService();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<package>"
                        + ("<changelogs>" + ("<changelog>Initial release</changelog>")
                                + ("<version>1</version>") + "</changelogs>")
                        + ("<changelogs>" + ("<changelog>Update</changelog>")
                                + "<version>1.5.0.0</version>" + "</changelogs>") + "</package>");
        aggregatedService.addService(new ChangelogInfoDownloadService(new HttpURLConnectionMock(
                new URL("http://127.0.0.1"), xml)));

        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<package>"
                        + ("<changelogs>" + ("<changelog>Other release</changelog>")
                                + ("<version>2</version>") + "</changelogs>")
                        + ("<changelogs>" + ("<changelog>Update</changelog>")
                                + "<version>2.0.0.0</version>" + "</changelogs>") + "</package>");
        aggregatedService.addService(new ChangelogInfoDownloadService(new HttpURLConnectionMock(
                new URL("http://127.0.0.1"), xml)));

        ChangelogInfoAggregatedNotifier listener = aggregatedService.getNotifier();
        listener.addObserver(new ListenerObserver());

        // when
        aggregatedService.start();
        aggregatedService.joinThread();

        // then
        assertThat(recievedMessage)
                .as("ChangelogInfoAggregatedNotifier should inform about finishing of all downloads")
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
