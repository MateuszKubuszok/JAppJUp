/**
 * Copyright 2012-2013 Mateusz Kubuszok
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
package net.jsdpu.process.killers;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.process.killers.ProcessKillerConfiguration.*;

import java.io.IOException;

import net.jsdpu.logger.Logger;

/**
 * Provides common logic to all process killers.
 */
public abstract class AbstractProcessKiller implements IProcessKiller {
    private static final Logger logger = getLogger(AbstractProcessKiller.class);

    @Override
    public void killProcess(String programName) throws IOException, InterruptedException,
            ProcessKillerException {
        logger.trace("Attempt to kill " + programName);
        int attempts = 0;

        if (!isProgramRunning(programName))
            return;

        for (attempts = 0; attempts < HOW_MANY_ATTEMPTS_BEFORE_FAIL; attempts++) {
            if (!askToDieGracefully(programName)) {
                killAllResistants(programName);
                return;
            }

            if (!isProgramRunning(programName)) {
                logger.detailedTrace("Successfully killed all instances of " + programName);
                return;
            }

            sleep(SECONDS.toMillis(HOW_MANY_SECONDS_BETWEEN_ATTEMPTS));
        }

        logger.error("Failed to kill " + programName + " (exception thrown)");
        throw new ProcessKillerException("Couldn't kill process - " + HOW_MANY_ATTEMPTS_BEFORE_FAIL
                + " attempts failed");
    }

    /**
     * Attempts to kill process "gracefully" - by sending TERM signal.
     * 
     * <p>
     * Should make program pop
     * "Do you want to save before exit?"/"Are you sure you want to quit?"
     * dialog and then finish. Otherwise program should just die.
     * </p>
     * 
     * @param pid
     *            PID of a program that should be killed
     * @return true if succeed to kill process
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted, while waiting for system
     *             dependent process
     */
    protected abstract boolean askToDieGracefully(String pid) throws IOException,
            InterruptedException;

    /**
     * Kills process forcefully, if attempt to kill it gracefully failed.
     * 
     * @param pid
     *            PID of a program that should be killed
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted, while waiting for system
     *             dependent process
     */
    protected abstract void killAllResistants(String pid) throws IOException, InterruptedException;

    /**
     * Checks whether program with given name is currently executed.
     * 
     * @param programName
     *            program that should be checked
     * @return true if program is running
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted, while waiting for system
     *             dependent process
     */
    protected abstract boolean isProgramRunning(String programName) throws IOException,
            InterruptedException;
}
