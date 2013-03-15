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

import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedNotifierTester;
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