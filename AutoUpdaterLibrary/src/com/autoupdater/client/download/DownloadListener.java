package com.autoupdater.client.download;

import com.autoupdater.client.utils.services.IObserver;

/**
 * Interface which implementation guarantee that class will be able to listen do
 * DownloadNotifiers and DownloadServices.
 * 
 * @see com.autoupdater.client.download.services.AbstractDownloadService
 * @see com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier
 */
public interface DownloadListener extends IObserver<DownloadServiceMessage> {
}
