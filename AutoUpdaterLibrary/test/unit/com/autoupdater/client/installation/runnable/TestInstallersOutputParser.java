package com.autoupdater.client.installation.runnable;

import static com.autoupdater.client.models.EUpdateStatus.*;
import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.InvalidCommandException;

import org.fest.util.Lists;
import org.junit.Test;

import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.UpdateBuilder;

public class TestInstallersOutputParser {
    @Test
    public void testParseInstallersOutput() throws InvalidCommandException {
        forInfoParsing();
        forErrorParsing();
        forEndOfStream();
    }

    private void forInfoParsing() throws InvalidCommandException {
        // given
        Update update = UpdateBuilder.builder().setStatus(SELECTED).build();
        String[] input = { "[info] " + update.getUniqueIdentifer() + ": "
                + INSTALLED.installerMessage().getMessage() };
        SortedSet<Update> updates = new TreeSet<Update>(asList(update));

        // when
        InstallersOutputParser parser = new InstallersOutputParser();
        parser.parseInstallersOutput(updates, getReader(input));

        // then
        assertThat(update.getStatus()).as("Should parse [info] messages into status").isEqualTo(
                INSTALLED);
    }

    private void forErrorParsing() throws InvalidCommandException {
        // given
        Update update = UpdateBuilder.builder().setStatus(SELECTED).build();
        String[] input = { "[error] " + update.getUniqueIdentifer() + ": "
                + FAILED.installerMessage().getMessage() };
        SortedSet<Update> updates = new TreeSet<Update>(asList(update));

        // when
        InstallersOutputParser parser = new InstallersOutputParser();
        parser.parseInstallersOutput(updates, getReader(input));

        // then
        assertThat(update.getStatus()).as("Should parse [info] messages into status").isEqualTo(
                FAILED);
    }

    private void forEndOfStream() throws InvalidCommandException {
        // given
        Update update = UpdateBuilder.builder().setStatus(SELECTED).build();
        String[] input = { null };
        SortedSet<Update> updates = new TreeSet<Update>(asList(update));

        // when
        InstallersOutputParser parser = new InstallersOutputParser();
        parser.parseInstallersOutput(updates, getReader(input));

        // then
        // finishes
    }

    private ExecutionQueueReader getReader(String[] lines) throws InvalidCommandException {
        List<String> linesWithStop = Lists.newArrayList(lines);
        String firstValue = linesWithStop.remove(0);
        linesWithStop.add(null);

        ExecutionQueueReader reader = mock(ExecutionQueueReader.class);
        when(reader.getNextOutput()).thenReturn(firstValue, linesWithStop.toArray(lines));
        return reader;
    }
}
