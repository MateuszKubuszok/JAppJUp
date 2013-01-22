package com.autoupdater.client.download.aggregated.notifiers;

import com.autoupdater.client.models.Update;

/**
 * Notifier that aggregates and sends messages from several
 * FileDownloadServices.
 * 
 * @see com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier
 * @see com.autoupdater.client.download.services.FileDownloadService
 */
public class FileAggregatedNotifier extends AbstractAggregatedDownloadNotifier<Update> {
}
