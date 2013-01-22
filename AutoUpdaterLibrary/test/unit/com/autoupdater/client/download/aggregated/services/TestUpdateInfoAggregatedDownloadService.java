package com.autoupdater.client.download.aggregated.services;

import static org.fest.assertions.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.UpdateInfoDownloadService;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.PackageBuilder;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.VersionNumber;

public class TestUpdateInfoAggregatedDownloadService {
    private final String update1Package1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<updates>"
                    + ("<update packageName=\"Test package\" packageId=\"2\" "
                            + "version=\"12.34.56.78\" developmentVersion=\"true\" "
                            + "id=\"1\" type=\"copy\" originalName=\"file.ini\" relativePath=\"/\">Some changes</update>")
                    + "</updates>");
    private final String update1Package2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<updates>"
                    + ("<update packageName=\"other test package\" packageId=\"3\" "
                            + "version=\"98.76.54.32\" developmentVersion=\"true\" "
                            + "id=\"4\" type=\"copy\" originalName=\"file.ini\" relativePath=\"/\">Some other changes</update>")
                    + "</updates>");
    private final String update2Package1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<updates>"
                    + ("<update packageName=\"Test package\" packageId=\"2\" "
                            + "version=\"12.34.56.89\" developmentVersion=\"true\" "
                            + "id=\"1\" type=\"copy\" originalName=\"file.ini\" relativePath=\"/\">Some changes</update>")
                    + "</updates>");
    private final String update2Package2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<updates>"
                    + ("<update packageName=\"other test package\" packageId=\"3\" "
                            + "version=\"98.76.54.43\" developmentVersion=\"true\" "
                            + "id=\"4\" type=\"copy\" originalName=\"file.ini\" relativePath=\"/\">Some other changes</update>")
                    + "</updates>");

    @Test
    public void testService() {
        try {
            // given
            Program program = program();

            UpdateInfoAggregatedDownloadService aggregatedService = new UpdateInfoAggregatedDownloadService();
            addService(aggregatedService, update1Package1, program.getPackages().first());
            addService(aggregatedService, update1Package2, program.getPackages().last());

            SortedSet<Update> result = null;

            // when
            aggregatedService.start();
            aggregatedService.joinThread();
            result = aggregatedService.getResult();

            // then
            assertThat(result).as("getResult() should aggregate results from all services")
                    .isNotNull().hasSize(2);
            assertThat(program.getPackages().first().getUpdate()).as(
                    "getResult() properly inserts Update into Package").isNotNull();
            assertThat(program.getPackages().first().getUpdate().getVersionNumber())
                    .as("getResult() properly inserts Update into Package").isNotNull()
                    .isEqualTo(VersionNumber.version(12, 34, 56, 78));
            assertThat(program.getPackages().last().getUpdate()).as(
                    "getResult() properly inserts Update into Package").isNotNull();
            assertThat(program.getPackages().last().getUpdate().getVersionNumber())
                    .as("getResult() properly inserts Update into Package").isNotNull()
                    .isEqualTo(VersionNumber.version(98, 76, 54, 32));
        } catch (DownloadResultException e) {
            fail("getResult() should not throw exception when result is ready, and without errors");
        }
    }

    @Test
    public void testUpdate() {
        try {
            // given
            Program program = program();

            UpdateInfoAggregatedDownloadService aggregatedService = new UpdateInfoAggregatedDownloadService();
            addService(aggregatedService, update1Package1, program.getPackages().first());
            addService(aggregatedService, update1Package2, program.getPackages().last());

            UpdateInfoAggregatedDownloadService aggregatedService2 = new UpdateInfoAggregatedDownloadService();
            addService(aggregatedService2, update2Package1, program.getPackages().first());
            addService(aggregatedService2, update2Package2, program.getPackages().last());

            // when
            aggregatedService.start();
            aggregatedService.joinThread();
            aggregatedService.getResult();

            aggregatedService2.start();
            aggregatedService2.joinThread();
            aggregatedService2.getResult();

            // then
            assertThat(program.getPackages().first().getUpdate()).as(
                    "getResult() properly inserts Update into Package").isNotNull();
            assertThat(program.getPackages().first().getUpdate().getVersionNumber())
                    .as("getResult() properly inserts Update into Package").isNotNull()
                    .isEqualTo(VersionNumber.version(12, 34, 56, 89));
            assertThat(program.getPackages().last().getUpdate()).as(
                    "getResult() properly inserts Update into Package").isNotNull();
            assertThat(program.getPackages().last().getUpdate().getVersionNumber())
                    .as("getResult() properly inserts Update into Package").isNotNull()
                    .isEqualTo(VersionNumber.version(98, 76, 54, 43));
        } catch (DownloadResultException e) {
            fail("getResult() should not throw exception when result is ready, and without errors");
        }
    }

    private Program program() {
        return ProgramBuilder
                .builder()
                .setName("name")
                .setPackages(
                        new TreeSet<Package>(Arrays.asList(PackageBuilder.builder().setName("1")
                                .setID("1").build(),
                                PackageBuilder.builder().setName("2").setID("2").build())))
                .setDevelopmentVersion(true).build();
    }

    private void addService(UpdateInfoAggregatedDownloadService aggregatedService, String xml,
            Package _package) {
        try {
            aggregatedService.addService(new UpdateInfoDownloadService(new HttpURLConnectionMock(
                    new URL("http://127.0.0.1"), xml)), _package);
        } catch (MalformedURLException e) {
        }
    }
}
