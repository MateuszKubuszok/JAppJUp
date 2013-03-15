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
