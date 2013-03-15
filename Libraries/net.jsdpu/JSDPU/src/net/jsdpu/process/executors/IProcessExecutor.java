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
package net.jsdpu.process.executors;

import java.io.IOException;
import java.util.List;

/**
 * Common interface for classes responsible for executing processes in their
 * respective operating systems with the possible privilege elevation.
 * 
 * <p>
 * It can (and should) be obtained by
 * EOperatingSystem.current().getProcessExecutor().
 * </p>
 * 
 * @see net.jsdpu.process.killers.LinuxProcessKiller
 * @see net.jsdpu.process.killers.MacOSProcessKiller
 * @see net.jsdpu.process.killers.WindowsProcessKiller
 * 
 * @see net.jsdpu.EOperatingSystem#getProcessKiller()
 */
public interface IProcessExecutor {
    /**
     * Executes commands as a common user (namely the one that run Java VM).
     * 
     * @param commands
     *            commands to be executed
     * @return reader, which allows to read result of processing
     * @throws IOException
     *             thrown when error occurs in system dependent process
     */
    public ExecutionQueueReader execute(List<String[]> commands) throws IOException;

    /**
     * Executes commands as root.
     * 
     * <p>
     * If there is need to pass a command with argument(s) using quotation mark,
     * it should be escaped, e.g.:
     * </p>
     * 
     * <pre>
     * &quot;program \&quot;parameter 1\&quot; parameter2&quot;
     * </pre>
     * 
     * @param commands
     *            commands to be executed
     * @return reader, which allows to read result of processing
     * @throws IOException
     *             thrown when error occurs in system dependent process
     */
    public ExecutionQueueReader executeRoot(List<String[]> commands) throws IOException;

    /**
     * Executes commands as a common user or root, depending on parameter.
     * 
     * <p>
     * If there is need to pass a command with argument(s) using quotation mark,
     * it should be escaped, e.g.:
     * </p>
     * 
     * <pre>
     * &quot;program \&quot;parameter 1\&quot; parameter2&quot;
     * </pre>
     * 
     * @param commands
     *            commands to be executed
     * @param asRoot
     *            whether or not run commands as root
     * @return reader, which allows to read result of processing
     * @throws IOException
     *             thrown when error occurs in system dependent process
     */
    public ExecutionQueueReader execute(List<String[]> commands, boolean asRoot) throws IOException;
}
