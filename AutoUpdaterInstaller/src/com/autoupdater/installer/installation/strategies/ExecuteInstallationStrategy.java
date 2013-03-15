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
package com.autoupdater.installer.installation.strategies;

import static com.autoupdater.commons.error.codes.EErrorCode.SUCCESS;
import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.process.executors.Commands.convertMultipleConsoleCommands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.jsdpu.logger.Logger;
import net.jsdpu.process.executors.InvalidCommandException;

/**
 * Executes command passed into installer.
 */
public class ExecuteInstallationStrategy implements IInstallationStrategy {
    private static final Logger logger = getLogger(ExecuteInstallationStrategy.class);

    @Override
    public void process(File ignoredFile, String executedCommand) throws IOException,
            InvalidCommandException {
        logger.debug("Executes command: " + executedCommand);

        String[] command = convertMultipleConsoleCommands(executedCommand).get(0);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        rewind(process.getInputStream());
        rewind(process.getErrorStream());

        try {
            int errorCode = process.waitFor();
            if (errorCode != SUCCESS.getCode()) {
                logger.error("Execution finished with code: " + errorCode + " (exception thrown)");
                throw new IOException("Update executaion failed!");
            }
        } catch (InterruptedException e) {
            logger.error("Exception occured: " + e.getMessage(), e);
            throw new IOException("Update executaion failed!");
        }
        logger.debug("Execution finished successfully");
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
            logger.warning("Excepton occured while reading: " + e.getMessage(), e);
        }
    }
}
