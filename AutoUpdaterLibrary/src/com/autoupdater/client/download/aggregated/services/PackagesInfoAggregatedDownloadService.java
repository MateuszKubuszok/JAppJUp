package com.autoupdater.client.download.aggregated.services;

import static net.jsdpu.logger.Logger.getLogger;

import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.logger.Logger;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.notifiers.PackagesInfoAggregatedNotifier;
import com.autoupdater.client.download.services.PackagesInfoDownloadService;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Models;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.PackageBuilder;
import com.autoupdater.client.models.Program;

/**
 * Aggregator that download programs/packages information from several servers
 * at the same time.
 * 
 * <p>
 * Result is aggregated as Set of all available Programs.
 * </p>
 * 
 * <p>
 * Compliments local Programs with newly discovered Packages.
 * </p>
 * 
 * @see com.autoupdater.client.download.services.PackagesInfoDownloadService
 * @see com.autoupdater.client.download.aggregated.notifiers.PackagesInfoAggregatedNotifier
 */
public class PackagesInfoAggregatedDownloadService
        extends
        AbstractAggregatedDownloadService<PackagesInfoDownloadService, PackagesInfoAggregatedNotifier, SortedSet<Program>, SortedSet<Program>, ProgramSettings> {
    private static final Logger logger = getLogger(PackagesInfoAggregatedDownloadService.class);

    private SortedSet<Program> installedPrograms;

    @Override
    protected PackagesInfoAggregatedNotifier createNotifier() {
        return new PackagesInfoAggregatedNotifier();
    }

    /**
     * Sets set of locally installed programs. Allows to update packages IDs on
     * result return.
     * 
     * <p>
     * If set, Packages' IDs' will be automatically updated on getResult() call
     * and Programs will be complimented with packages available on servers.
     * </p>
     * 
     * @param installedPrograms
     *            set of locally installed programs
     */
    public void setInstalledPrograms(SortedSet<Program> installedPrograms) {
        this.installedPrograms = installedPrograms;
    }

    @Override
    public SortedSet<Program> getResult() throws DownloadResultException {
        logger.debug("Starts calculating results");
        SortedSet<Program> allServersPrograms = new TreeSet<Program>();
        ProgramSettings programSettings;
        for (PackagesInfoDownloadService service : getServices()) {
            SortedSet<Program> serverPrograms = service.getResult();
            if ((programSettings = getAdditionalMessage(service)) != null)
                for (Program program : serverPrograms)
                    program.setServerAddress(programSettings.getServerAddress());
            allServersPrograms.addAll(serverPrograms);
        }

        updateInstalledPrograms(allServersPrograms);
        logger.debug("Finshed calculating results");
        return allServersPrograms;
    }

    /**
     * Updates IDs of installed packages to match those on server.
     * 
     * <p>
     * This method will update IDs only id installedPrograms are set.
     * </p>
     * 
     * @see #setInstalledPrograms(SortedSet)
     * 
     * @param allServersPrograms
     *            set of locally installed programs
     */
    private void updateInstalledPrograms(SortedSet<Program> allServersPrograms) {
        logger.trace("Update installed Programs data");
        if (installedPrograms == null)
            return;

        for (Program installedProgram : installedPrograms)
            for (Program serverProgram : allServersPrograms)
                if (Models.equal(installedProgram, serverProgram,
                        Models.EComparisionType.LOCAL_TO_SERVER))
                    updateInstalledPackages(installedProgram, serverProgram);
    }

    /**
     * Rewrites ID from Packages on server to corresponding local Packages.
     * 
     * @param installedProgram
     *            locally installed program
     * @param serverProgram
     *            program on server
     */
    private void updateInstalledPackages(Program installedProgram, Program serverProgram) {
        logger.trace("Update installed Packges data");
        SortedSet<Package> installedPackages = installedProgram.getPackages();
        SortedSet<Package> serverPackages = serverProgram.getPackages();

        if (installedPackages == null || installedPackages.isEmpty() || serverPackages == null
                || serverPackages.isEmpty())
            return;

        for (Package installedPackage : installedPackages)
            for (Package serverPackage : serverPackages)
                if (Models.equal(installedPackage, serverPackage,
                        Models.EComparisionType.LOCAL_TO_SERVER))
                    installedPackage.setID(serverPackage.getID());

        for (Package serverPackage : serverPackages)
            if (!Models.contains(installedPackages, serverPackage,
                    Models.EComparisionType.LOCAL_TO_SERVER))
                installedPackages.add(PackageBuilder.builder().copy(serverPackage)
                        .setProgram(installedProgram).build());
    }
}
