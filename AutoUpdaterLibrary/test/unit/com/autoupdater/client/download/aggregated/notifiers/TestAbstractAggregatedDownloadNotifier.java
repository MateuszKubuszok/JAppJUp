package com.autoupdater.client.download.aggregated.notifiers;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.aggregated.services.AbstractAggregatedDownloadServiceTester;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.PackagesInfoDownloadService;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;

public class TestAbstractAggregatedDownloadNotifier {
    private String recievedMessage;

    @Test
    public void testNotifier() throws MalformedURLException {
        // given
        AbstractAggregatedDownloadServiceTester aggregatedService = new AbstractAggregatedDownloadServiceTester();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<program>"
                        + ("<programs>" + "<programName>Program 1</programName>"
                                + "<packages><name>Package 1</name><id>1</id></packages>"
                                + "<packages><name>Package 2</name><id>2</id></packages>"
                                + "</programs>") + "</program>");
        aggregatedService.addService(new PackagesInfoDownloadService(new HttpURLConnectionMock(
                new URL("http://127.0.0.1"), xml)));

        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + ("<program>"
                        + ("<programs>" + "<programName>Program 2</programName>"
                                + "<packages><name>Package 3</name><id>3</id></packages>"
                                + "</programs>") + "</program>");
        aggregatedService.addService(new PackagesInfoDownloadService(new HttpURLConnectionMock(
                new URL("http://127.0.0.1"), xml)));

        AbstractAggregatedNotifierTester listener = aggregatedService.getNotifier();
        listener.addObserver(new ListenerObserver());

        // when
        aggregatedService.start();
        aggregatedService.joinThread();

        // then
        assertThat(recievedMessage)
                .as("AbstractAggregatedNotifier should inform about finishing of all downloads")
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
