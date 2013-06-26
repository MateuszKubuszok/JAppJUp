/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package com.autoupdater.client.download.aggregated.services;

import static com.autoupdater.client.models.VersionNumber.version;
import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.ChangelogInfoDownloadService;
import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.models.ChangelogEntryBuilder;
import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.PackageBuilder;

public class TestChangelogInfoAggregatedDownloadService {
    private final String package1Changelog1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<changelogs>" + ("<changelog version=\"1\">Initial release</changelog>")
                    + ("<changelog version=\"1.5.0.0\">Update</changelog>") + "</changelogs>");
    private final String package2Changelog1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<changelogs>" + ("<changelog version=\"2\">Other release</changelog>")
                    + ("<changelog version=\"2.0.0.0\">Update</changelog>") + "</changelogs>");
    private final String package1Changelog2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<changelogs>" + ("<changelog version=\"1\">Initial release</changelog>")
                    + ("<changelog version=\"1.5.0.0\">Update</changelog>")
                    + ("<changelog version=\"2.5.0.0\">Newest</changelog>") + "</changelogs>");

    private final ChangelogEntry changelog1 = ChangelogEntryBuilder.builder()
            .setDescription("Initial release").setVersionNumber(version("1")).build();
    private final ChangelogEntry changelog2 = ChangelogEntryBuilder.builder()
            .setDescription("Update").setVersionNumber(version("1.5.0.0")).build();
    private final ChangelogEntry changelog3 = ChangelogEntryBuilder.builder()
            .setDescription("Newest").setVersionNumber(version("2.5.0.0")).build();

    private final ChangelogEntry changelog4 = ChangelogEntryBuilder.builder()
            .setDescription("Other release").setVersionNumber(version("2")).build();
    private final ChangelogEntry changelog5 = ChangelogEntryBuilder.builder()
            .setDescription("Update").setVersionNumber(version("2.0.0.0")).build();

    @Test
    public void testService() throws DownloadResultException {
        // given
        Package package1 = PackageBuilder.builder().setName("1").setID("1").build();
        Package package2 = PackageBuilder.builder().setName("2").setID("2").build();

        ChangelogInfoAggregatedDownloadService aggregatedService = new ChangelogInfoAggregatedDownloadService();
        addService(aggregatedService, package1Changelog1, package1);
        addService(aggregatedService, package2Changelog1, package2);

        SortedSet<Package> result = null;

        // when
        aggregatedService.start();
        aggregatedService.joinThread();
        result = aggregatedService.getResult();

        // then
        assertThat(result).as("getResult() should aggregate results from all services").isNotNull()
                .hasSize(2);
        assertThat(package1.getChangelog()).as("getResult() should properly insert Changelogs")
                .isNotNull().hasSize(2).containsExactly(changelog1, changelog2);
        assertThat(package2.getChangelog()).as("getResult() should properly insert Changelogs")
                .isNotNull().hasSize(2).containsExactly(changelog4, changelog5);
    }

    @Test
    public void testUpdate() throws DownloadResultException {
        // given
        Package package1 = PackageBuilder.builder().setName("1").setID("1").build();
        Package package2 = PackageBuilder.builder().setName("2").setID("2").build();

        ChangelogInfoAggregatedDownloadService aggregatedService = new ChangelogInfoAggregatedDownloadService();
        addService(aggregatedService, package1Changelog1, package1);
        addService(aggregatedService, package2Changelog1, package2);

        ChangelogInfoAggregatedDownloadService aggregatedService2 = new ChangelogInfoAggregatedDownloadService();
        addService(aggregatedService2, package1Changelog2, package1);
        addService(aggregatedService2, package2Changelog1, package2);

        // when
        aggregatedService.start();
        aggregatedService.joinThread();
        aggregatedService.getResult();

        aggregatedService2.start();
        aggregatedService2.joinThread();
        aggregatedService2.getResult();

        // then
        assertThat(package1.getChangelog()).as("getResult() should properly insert Changelogs")
                .isNotNull().hasSize(3).containsExactly(changelog1, changelog2, changelog3);
        assertThat(package2.getChangelog()).as("getResult() should properly insert Changelogs")
                .isNotNull().hasSize(2).containsExactly(changelog4, changelog5);
    }

    private void addService(ChangelogInfoAggregatedDownloadService aggregatedService, String xml,
            Package _package) {
        try {
            aggregatedService.addService(new ChangelogInfoDownloadService(
                    new HttpURLConnectionMock(new URL("http://127.0.0.1"), xml)), _package);
        } catch (MalformedURLException e) {
        }
    }
}
