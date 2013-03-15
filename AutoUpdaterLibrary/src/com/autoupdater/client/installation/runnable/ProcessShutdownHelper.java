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
package com.autoupdater.client.installation.runnable;

import java.io.IOException;
import java.util.SortedSet;

import net.jsdpu.process.killers.IProcessKiller;
import net.jsdpu.process.killers.ProcessKillerException;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.client.models.Update;

/**
 * Helper responsible for shutting down all updated programs that might be
 * running at the moment of updates' installation.
 * 
 * <p>
 * Used by InstallationRunnable.
 * </p>
 * 
 * @see com.autoupdater.client.installation.runnable.InstallationRunnable
 */
class ProcessShutdownHelper {
    private final EnvironmentData environmentData;

    /**
     * Creates helper responsible for shutting down updated programs.
     * 
     * @param environmentData
     *            environmentData instance
     */
    public ProcessShutdownHelper(EnvironmentData environmentData) {
        this.environmentData = environmentData;
    }

    /**
     * Shuts down programs for required updates.
     * 
     * @param updates
     *            set of updates to install
     * @throws ProgramSettingsNotFoundException
     *             thrown if some of updates cannot resolve its ProgramSettings
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted during waiting for system
     *             dependent process to finish
     * @throws ProcessKillerException
     *             thrown if unable to kill the process
     */
    public void killProcesses(SortedSet<Update> updates) throws ProgramSettingsNotFoundException,
            IOException, InterruptedException, ProcessKillerException {
        IProcessKiller killer = environmentData.getSystem().getProcessKiller();
        for (Update update : updates)
            if (update != null && update.getStatus().isIntendedToBeChanged())
                killer.killProcess(environmentData.findProgramSettingsForUpdate(update)
                        .getProgramExecutableName());
    }
}
