package com.autoupdater.client.download.aggregated.notifiers;

import com.autoupdater.client.models.Program;

/**
 * Notifier that aggregates and sends messages from several
 * BugsInfoDownloadServices.
 * 
 * @see com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier
 * @see com.autoupdater.client.download.services.BugsInfoDownloadService
 */
public class BugsInfoAggregatedNotifier extends AbstractAggregatedDownloadNotifier<Program> {
}
