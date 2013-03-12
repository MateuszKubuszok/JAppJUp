package net.jsdpu.process.executors;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.jsdpu.logger.Logger;

/**
 * Queue starting and returning Process' sequentially.
 */
public class ProcessQueue {
    private static final Logger logger = getLogger(ProcessQueue.class);

    private final List<ProcessBuilder> processBuilders;

    /**
     * Creates queue instance.
     */
    ProcessQueue() {
        this.processBuilders = new ArrayList<ProcessBuilder>();
    }

    /**
     * Creates queue instance.
     * 
     * @param processBuilders
     *            list of builders that will create queue
     */
    ProcessQueue(List<ProcessBuilder> processBuilders) {
        this.processBuilders = processBuilders != null ? processBuilders
                : new ArrayList<ProcessBuilder>();
    }

    /**
     * Starts and returns next Process.
     * 
     * @return next process if possible, null if none available
     * @throws IOException
     *             thrown if attempt to run of any of commands happen to fail
     *             (e.g. program doesn't exists)
     */
    public Process getNextProcess() throws IOException {
        if (processBuilders.isEmpty())
            return null;
        logger.trace("Initialization of process: " + processBuilders.get(0).command());
        Process process = processBuilders.get(0).start();
        processBuilders.remove(0);
        return process;
    }

    /**
     * Returns true if queue is empty.
     * 
     * @return true if queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return processBuilders.isEmpty();
    }
}
