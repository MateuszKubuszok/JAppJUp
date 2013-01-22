package com.autoupdater.client.download.aggregated.services;

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedNotifierTester;
import com.autoupdater.client.download.aggregated.services.AbstractAggregatedDownloadService;
import com.autoupdater.client.download.services.PackagesInfoDownloadService;
import com.autoupdater.client.models.Program;

public class AbstractAggregatedDownloadServiceTester
        extends
        AbstractAggregatedDownloadService<PackagesInfoDownloadService, AbstractAggregatedNotifierTester, SortedSet<Program>, SortedSet<Program>, String> {
    @Override
    public SortedSet<Program> getResult() throws DownloadResultException {
        SortedSet<Program> programs = new TreeSet<Program>();
        for (PackagesInfoDownloadService service : getServices())
            programs.addAll(service.getResult());
        return programs;
    }

    @Override
    protected AbstractAggregatedNotifierTester createNotifier() {
        return new AbstractAggregatedNotifierTester();
    }
}