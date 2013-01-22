package com.autoupdater.client.download.aggregated.notifiers;

import com.autoupdater.client.models.Package;

/**
 * Notifier that aggregates and sends messages from several
 * UpdateInfoDownloadServices.
 * 
 * @see com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier
 * @see com.autoupdater.client.download.services.UpdateInfoDownloadService
 */
public class UpdateInfoAggregatedNotifier extends AbstractAggregatedDownloadNotifier<Package> {
}
