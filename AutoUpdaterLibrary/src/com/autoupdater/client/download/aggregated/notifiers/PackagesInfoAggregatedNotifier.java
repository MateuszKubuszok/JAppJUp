package com.autoupdater.client.download.aggregated.notifiers;

import com.autoupdater.client.environment.settings.ProgramSettings;

/**
 * Notifier that aggregates and sends messages from several
 * PackagesInfoDownloadServices.
 * 
 * @see com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier
 * @see com.autoupdater.client.download.services.PackagesInfoDownloadService
 */
public class PackagesInfoAggregatedNotifier extends AbstractAggregatedDownloadNotifier<ProgramSettings> {
}
