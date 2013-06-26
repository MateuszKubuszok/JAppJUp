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
package com.autoupdater.client;

import java.io.File;
import java.util.SortedSet;

import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.ProcessQueue;

import com.autoupdater.client.Paths.Library;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;

public abstract class AbstractTest {
    public AbstractTest() {
        new File(Library.testDir).mkdirs();
    }

    protected ClientSettings clientSettings() {
        return com.autoupdater.client.environment.settings.Mocks.clientSettings();
    }

    protected ProgramSettings programSettings() {
        return com.autoupdater.client.environment.settings.Mocks.programSettings();
    }

    protected ProgramSettings programSettings2() {
        return com.autoupdater.client.environment.settings.Mocks.programSettings2();
    }

    protected SortedSet<ProgramSettings> programsSettings() {
        return com.autoupdater.client.environment.settings.Mocks.programsSettings();
    }

    protected EnvironmentData environmentData() throws ProgramSettingsNotFoundException {
        return com.autoupdater.client.environment.Mocks.environmentData();
    }

    protected ProcessQueue processQueue(String... resultsToReturn) {
        return net.jsdpu.process.executors.Mocks.processQueue(resultsToReturn);
    }

    protected ExecutionQueueReader executionQueueReader(String... resultsToReturn) {
        return net.jsdpu.process.executors.Mocks.executionQueueReader(resultsToReturn);
    }
}
