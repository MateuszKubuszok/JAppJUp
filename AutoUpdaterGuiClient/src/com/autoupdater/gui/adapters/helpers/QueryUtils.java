package com.autoupdater.gui.adapters.helpers;

import java.io.IOException;
import java.util.SortedSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.services.BugsInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService;
import com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.listeners.notification.PackagesInfoNotificationListener;
import com.autoupdater.gui.adapters.runnables.CheckUpdatesRunnable;

public class QueryUtils {
    private final Gui2ClientAdapter adapter;

    public QueryUtils(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
    }

    public SortedSet<Program> queryServerForPackages() throws IOException, DownloadResultException {
        PackagesInfoAggregatedDownloadService aggregatedService = adapter.client()
                .createPackagesInfoAggregatedDownloadService();
        aggregatedService.getNotifier().addObserver(
                new PackagesInfoNotificationListener(adapter, aggregatedService));

        aggregatedService.start();
        aggregatedService.joinThread();

        return aggregatedService.getResult();
    }

    public void queryServerForInformation() throws DownloadResultException, IOException,
            ProgramSettingsNotFoundException {
        SortedSet<Program> selectedPrograms = adapter.dataStorage().getSelectedPrograms();
        SortedSet<Package> selectedPackages = adapter.dataStorage().getSelectedPackages();

        UpdateInfoAggregatedDownloadService aggregatedUpdateInfoService = adapter.client()
                .createUpdateInfoAggregatedDownloadService(selectedPackages);

        ChangelogInfoAggregatedDownloadService aggregatedChangelogInfoService = adapter.client()
                .createChangelogInfoAggregatedDownloadService(selectedPackages);

        BugsInfoAggregatedDownloadService aggregatedBugsInfoService = adapter.client()
                .createBugsInfoAggregatedDownloadService(selectedPrograms);

        new Thread(new CheckUpdatesRunnable(adapter, aggregatedUpdateInfoService,
                aggregatedChangelogInfoService, aggregatedBugsInfoService)).start();
    }
}
