package net.jsdpu.process.executors;

import static net.jsdpu.process.executors.Commands.secureMultipleCommands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public ExecutionQueueReader execute(List<String[]> commands) throws IOException {
        return executeCommands(commands);
    }

    @Override
    public ExecutionQueueReader executeRoot(List<String[]> commands) throws IOException {
        return executeCommands(rootCommand(commands));
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
        List<ProcessBuilder> processBuilders = new ArrayList<ProcessBuilder>();
        for (String[] command : secureMultipleCommands(commands))
            processBuilders.add(new ProcessBuilder(command));

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
