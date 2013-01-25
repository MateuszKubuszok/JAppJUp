package com.autoupdater.installer.installation.strategies;

import static com.autoupdater.commons.error.codes.EErrorCode.SUCCESS;
import static net.jsdpu.process.executors.Commands.convertMultipleConsoleCommands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.jsdpu.process.executors.InvalidCommandException;

/**
 * Executes command passed into installer.
 */
public class ExecuteInstallationStrategy implements IInstallationStrategy {
    @Override
    public void process(File ignoredFile, String executedCommand) throws IOException,
            InvalidCommandException {
        String[] command = convertMultipleConsoleCommands(executedCommand).get(0);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        rewind(process.getInputStream());
        rewind(process.getErrorStream());

        try {
            if (process.waitFor() != SUCCESS.getCode())
                throw new IOException("Update executaion failed!");
        } catch (InterruptedException e) {
            throw new IOException("Update executaion failed!");
        }
    }

    private void rewind(InputStream is) {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            while (bufferedReader.readLine() != null)
                ;
        } catch (IOException e) {
        }
        try {
            bufferedReader.close();
            reader.close();
            is.close();
        } catch (IOException e) {
        }
    }
}
