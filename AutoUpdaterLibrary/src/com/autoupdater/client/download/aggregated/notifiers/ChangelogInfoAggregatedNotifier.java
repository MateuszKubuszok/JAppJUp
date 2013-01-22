package com.autoupdater.client.download.aggregated.notifiers;

import com.autoupdater.client.models.Package;

/**
 * Notifier that aggregates and sends messages from several
 * ChangelogInfoDownloadServices.
 * 
 * @see com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier
 * @see com.autoupdater.client.download.services.ChangelogInfoDownloadService
 */
public class ChangelogInfoAggregatedNotifier extends AbstractAggregatedDownloadNotifier<Package> {
}
