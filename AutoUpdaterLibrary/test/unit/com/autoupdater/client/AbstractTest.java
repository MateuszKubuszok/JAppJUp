package com.autoupdater.client;

import java.io.File;
import java.util.SortedSet;

import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.ProcessQueue;

import com.autoupdater.client.Paths.Library;
import com.autoupdater.client.environment.EnvironmentData;
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

    protected EnvironmentData environmentData() {
        return com.autoupdater.client.environment.Mocks.environmentData();
    }

    protected ProcessQueue processQueue(String... resultsToReturn) {
        return net.jsdpu.process.executors.Mocks.processQueue(resultsToReturn);
    }

    protected ExecutionQueueReader executionQueueReader(String... resultsToReturn) {
        return net.jsdpu.process.executors.Mocks.executionQueueReader(resultsToReturn);
    }
}
