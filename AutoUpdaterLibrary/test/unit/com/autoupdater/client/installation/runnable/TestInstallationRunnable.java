package com.autoupdater.client.installation.runnable;

import static com.autoupdater.client.installation.EInstallationStatus.*;
import static com.autoupdater.client.models.EUpdateStatus.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.IProcessExecutor;
import net.jsdpu.process.executors.InvalidCommandException;

import org.fest.util.Lists;
import org.junit.Test;
import org.mockito.Matchers;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.models.Update;

public class TestInstallationRunnable {
    @Test
    public void testRun() throws InvalidCommandException, IOException,
            ProgramSettingsNotFoundException {
        forSuccessfulInstallation();
        forInstallationWithExternalFailure();
        forInstallationWithInternalFailure();
    }

    private void forSuccessfulInstallation() throws InvalidCommandException, IOException,
            ProgramSettingsNotFoundException {
        // given
        SortedSet<Update> updates = getUpdates();
        updates.first().setStatus(SELECTED);
        EnvironmentData environmentData = getEnvironmentData();
        String[] lines = { "[info] " + updates.first().getUniqueIdentifer() + ": "
                + INSTALLED.installerMessage().getMessage() };
        mockReader(environmentData, lines);

        // when
        InstallationRunnable runnable = new InstallationRunnable(environmentData, updates);
        runnable.run();

        // then
        assertThat(runnable.getState()).as(
                "When all installations succeeded installation should be marked as successful")
                .isEqualTo(INSTALLATION_SUCCEEDED);
        assertThat(runnable.getThrownException()).as(
                "Successful installation should not throw exception").isNull();
        assertThat(updates.first().getStatus()).as(
                "When installation succeeded update should be marked as installed").isEqualTo(
                INSTALLED);
    }

    private void forInstallationWithExternalFailure() throws InvalidCommandException, IOException,
            ProgramSettingsNotFoundException {
        // given
        SortedSet<Update> updates = getUpdates();
        updates.first().setStatus(SELECTED);
        EnvironmentData environmentData = getEnvironmentData();
        String[] lines = { "[error] " + updates.first().getUniqueIdentifer() + ": "
                + FAILED.installerMessage().getMessage() };
        mockReader(environmentData, lines);

        // when
        InstallationRunnable runnable = new InstallationRunnable(environmentData, updates);
        runnable.run();

        // then
        assertThat(runnable.getState()).as(
                "When some installations failed installation should be marked as failed")
                .isEqualTo(INSTALLATION_FAILED);
        assertThat(runnable.getThrownException()).as("External failure should not throw exception")
                .isNull();
        assertThat(updates.first().getStatus()).as(
                "When installation failed update should be marked as failed").isEqualTo(FAILED);
    }

    private void forInstallationWithInternalFailure() throws ProgramSettingsNotFoundException {
        // given
        SortedSet<Update> updates = getUpdates();
        updates.first().setStatus(SELECTED);
        EnvironmentData environmentData = getEnvironmentData();
        failReader(environmentData);

        // when
        InstallationRunnable runnable = new InstallationRunnable(environmentData, updates);
        runnable.run();

        // then
        assertThat(runnable.getState()).as(
                "When exception occured installation should be marked as failed").isEqualTo(
                INSTALLATION_FAILED);
        assertThat(runnable.getThrownException()).as("External failure should throw exception")
                .isNotNull();
        assertThat(updates.first().getStatus()).as(
                "When installation failed update should be marked as failed").isEqualTo(FAILED);
    }

    private SortedSet<Update> getUpdates() {
        return com.autoupdater.client.models.Mocks.updates();
    }

    private EnvironmentData getEnvironmentData() throws ProgramSettingsNotFoundException {
        return com.autoupdater.client.environment.Mocks.environmentData();
    }

    private void mockReader(EnvironmentData environmentData, String[] lines)
            throws InvalidCommandException, IOException {
        List<String> linesWithStop = Lists.newArrayList(lines);
        String firstValue = linesWithStop.remove(0);
        linesWithStop.add(null);

        IProcessExecutor executor = environmentData.getSystem().getProcessExecutor();

        ExecutionQueueReader reader = mock(ExecutionQueueReader.class);
        when(reader.getNextOutput()).thenReturn(firstValue, linesWithStop.toArray(lines));

        when(executor.executeRoot(Matchers.<List<String[]>> any())).thenReturn(reader);
    }

    private void failReader(EnvironmentData environmentData)
            throws ProgramSettingsNotFoundException {
        when(environmentData.findProgramSettingsForUpdate(Matchers.<Update> any())).thenThrow(
                new ProgramSettingsNotFoundException(null));
    }
}
