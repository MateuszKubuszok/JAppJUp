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

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import org.junit.Test;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.runnables.HttpURLConnectionMock;
import com.autoupdater.client.download.services.BugsInfoDownloadService;
import com.autoupdater.client.models.BugEntry;
import com.autoupdater.client.models.BugEntryBuilder;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.ProgramBuilder;

public class TestBugsInfoAggregatedDownloadService {
    private final String program1Bugs1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<bugs>" + ("<bug>Some bug description</bug>")
                    + ("<bug>Some other description</bug>") + "</bugs>");
    private final String program2Bugs1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<bugs>" + ("<bug>Some bug description 2</bug>")
                    + ("<bug>Some other description 2</bug>") + "</bugs>");
    private final String program1Bugs2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + ("<bugs>" + ("<bug>Some bug description</bug>")
                    + ("<bug>Some bug description 3</bug>")
                    + ("<bug>Some other description 3</bug>") + "</bugs>");

    private final BugEntry bug1 = BugEntryBuilder.builder().setDescription("Some bug description")
            .build();
    private final BugEntry bug2 = BugEntryBuilder.builder()
            .setDescription("Some other description").build();
    private final BugEntry bug3 = BugEntryBuilder.builder()
            .setDescription("Some bug description 2").build();
    private final BugEntry bug4 = BugEntryBuilder.builder()
            .setDescription("Some other description 2").build();
    private final BugEntry bug5 = BugEntryBuilder.builder()
            .setDescription("Some bug description 3").build();
    private final BugEntry bug6 = BugEntryBuilder.builder()
            .setDescription("Some other description 3").build();

    @Test
    public void testService() throws DownloadResultException {
        // given
        Program program1 = ProgramBuilder.builder().setName("Program")
                .setPathToProgramDirectory("/").setServerAddress("127.0.0.1").build();
        Program program2 = ProgramBuilder.builder().setName("Program 2")
                .setPathToProgramDirectory("/").setServerAddress("127.0.0.1").build();

        BugsInfoAggregatedDownloadService aggregatedService = new BugsInfoAggregatedDownloadService();
        addService(aggregatedService, program1Bugs1, program1);
        addService(aggregatedService, program2Bugs1, program2);

        SortedSet<Program> result = null;

        // when
        aggregatedService.start();
        aggregatedService.joinThread();
        result = aggregatedService.getResult();

        // then
        assertThat(result).as("getResult() should aggregate results from all services").isNotNull()
                .hasSize(2);
        assertThat(program1.getBugs()).as("getResult() should properly insert Bugs").isNotNull()
                .hasSize(2).containsExactly(bug1, bug2);
        assertThat(program2.getBugs()).as("getResult() should properly insert Bugs").isNotNull()
                .hasSize(2).containsExactly(bug3, bug4);
    }

    @Test
    public void testUpdate() throws DownloadResultException {
        // given
        Program program1 = ProgramBuilder.builder().setName("Program")
                .setPathToProgramDirectory("/").setServerAddress("127.0.0.1").build();
        Program program2 = ProgramBuilder.builder().setName("Program 2")
                .setPathToProgramDirectory("/").setServerAddress("127.0.0.1").build();

        BugsInfoAggregatedDownloadService aggregatedService = new BugsInfoAggregatedDownloadService();
        addService(aggregatedService, program1Bugs1, program1);
        addService(aggregatedService, program2Bugs1, program2);

        BugsInfoAggregatedDownloadService aggregatedService2 = new BugsInfoAggregatedDownloadService();
        addService(aggregatedService2, program1Bugs2, program1);
        addService(aggregatedService2, program2Bugs1, program2);

        // when
        aggregatedService.start();
        aggregatedService.joinThread();
        aggregatedService.getResult();

        aggregatedService2.start();
        aggregatedService2.joinThread();
        aggregatedService2.getResult();

        // then
        assertThat(program1.getBugs()).as("getResult() should properly insert Bugs").isNotNull()
                .hasSize(3).containsExactly(bug1, bug5, bug6);
        assertThat(program2.getBugs()).as("getResult() should properly insert Bugs").isNotNull()
                .hasSize(2).containsExactly(bug3, bug4);
    }

    private void addService(BugsInfoAggregatedDownloadService aggregatedService, String xml,
            Program program) {
        try {
            aggregatedService.addService(new BugsInfoDownloadService(new HttpURLConnectionMock(
                    new URL("http://127.0.0.1"), xml)), program);
        } catch (MalformedURLException e) {
        }
    }
}
