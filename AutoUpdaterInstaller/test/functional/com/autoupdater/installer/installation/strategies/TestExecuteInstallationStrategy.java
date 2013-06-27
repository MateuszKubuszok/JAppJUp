package com.autoupdater.installer.installation.strategies;

import static org.fest.assertions.api.Assertions.fail;

import java.io.IOException;

import net.jsdpu.EOperatingSystem;
import net.jsdpu.process.executors.InvalidCommandException;

import org.junit.Test;


public class TestExecuteInstallationStrategy {
    @Test
    public void testProcess() {
        try {
            // given
            String testCommand = EOperatingSystem.currentOperatingSystem().getTestCommand();

            // when
            new ExecuteInstallationStrategy().process(null, testCommand);
        } catch (IOException | InvalidCommandException e) {
            fail("process(File,File) should not throw exception while working on accessible files");
        }
    }
}
