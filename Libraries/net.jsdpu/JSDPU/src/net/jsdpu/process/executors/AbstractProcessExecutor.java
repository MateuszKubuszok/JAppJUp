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

import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.logger.LoggerUtils.listToString;
import static net.jsdpu.process.executors.Commands.secureMultipleCommands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.jsdpu.logger.Logger;

/**
 * Superclass of ProcessExecutors - handles execution of commands and obtaining
 * results through BufferedReader.
 * 
 * <p>
 * It can (and should) be obtained by
 * EOperatingSystem.current().getProcessExecutor().
 * </p>
 * 
 * <p>
 * Commands could be obtained from Commands methods.
 * </p>
 * 
 * @see net.jsdpu.process.executors.Commands
 * @see net.jsdpu.process.executors.LinuxProcessExecutor
 * @see net.jsdpu.process.executors.MacOSProcessExecutor
 * @see net.jsdpu.process.executors.WindowsProcessExecutor
 * 
 * @see net.jsdpu.EOperatingSystem#getProcessExecutor()
 */
public abstract class AbstractProcessExecutor implements IProcessExecutor {
    private static final Logger logger = getLogger(AbstractProcessExecutor.class);

    @Override
    public ExecutionQueueReader execute(List<String[]> commands) throws IOException {
        return executeCommands(secureMultipleCommands(commands));
    }

    @Override
    public ExecutionQueueReader executeRoot(List<String[]> commands) throws IOException {
        return executeCommands(rootCommand(secureMultipleCommands(commands)));
    }

    @Override
    public ExecutionQueueReader execute(List<String[]> commands, boolean asRoot) throws IOException {
        if (asRoot)
            return executeRoot(commands);
        return execute(commands);
    }

    /**
     * Actual execution of commands.
     * 
     * @param commands
     *            commands that should be executed
     * @return reader, which allows to read result of processing
     * @throws IOException
     *             thrown when error occurs in system dependent process
     */
    private ExecutionQueueReader executeCommands(List<String[]> commands) throws IOException {
        logger.trace("Creating ExecutionQueue for: " + listToString(commands));

        List<ProcessBuilder> processBuilders = new ArrayList<ProcessBuilder>();
        for (String[] command : commands)
            processBuilders.add(new ProcessBuilder(command));

        logger.detailedTrace("Created ExecutionQueue");
        return new ExecutionQueueReader(new ProcessQueue(processBuilders));
    }

    /**
     * Generates command(s) executing all commands passed into ProcessExecutor
     * as root (or any other user with administrative privileges).
     * 
     * @param commands
     *            commands passed into ProcessExecutor
     * @return single command
     */
    protected abstract List<String[]> rootCommand(List<String[]> commands);
}
