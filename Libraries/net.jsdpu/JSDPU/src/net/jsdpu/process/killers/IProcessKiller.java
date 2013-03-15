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

import java.io.IOException;

/**
 * Common interface for classes responsible for killing processes in their
 * respective operating systems.
 * 
 * <p>
 * It can (and should) be obtained by
 * EOperatingSystem.current().getProcessKiller().
 * </p>
 * 
 * @see net.jsdpu.process.killers.LinuxProcessKiller
 * @see net.jsdpu.process.killers.MacOSProcessKiller
 * @see net.jsdpu.process.killers.WindowsProcessKiller
 * 
 * @see net.jsdpu.EOperatingSystem#getProcessKiller()
 */
public interface IProcessKiller {
    /**
     * Attempt to shutdown process with given program name.
     * 
     * @param programName
     *            name of program that should be killed
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted during waiting for system
     *             dependent process to finish
     * @throws ProcessKillerException
     *             thrown if killer is unable to shutdown the process
     */
    public void killProcess(String programName) throws IOException, InterruptedException,
            ProcessKillerException;
}
