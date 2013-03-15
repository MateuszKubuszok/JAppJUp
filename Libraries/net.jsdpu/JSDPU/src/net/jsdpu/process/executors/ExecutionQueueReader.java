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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.Vector;

import net.jsdpu.logger.Logger;

/**
 * Enqueues execution of Process, from each of them obtains Output and Error
 * stream and read them sequentially till both of them are closed as long as
 * there are enqueued process'.
 * 
 * <p>
 * Should be created by ProcessExecutors.
 * </p>
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class ExecutionQueueReader {
    private static final Logger logger = getLogger(ExecutionQueueReader.class);

    private Process currentProcess = null;
    private final ProcessQueue processQueue;
    private BufferedReader reader;

    /**
     * Creates instance of ExecutionQueueReader
     * 
     * <p>
     * Should be called only by ProcessExecutors.
     * </p>
     * 
     * @see net.jsdpu.process.executors.AbstractProcessExecutor
     * 
     * @param processQueue
     *            list of ProcessBuilders, serves as a queue
     */
    ExecutionQueueReader(ProcessQueue processQueue) {
        this.processQueue = processQueue != null ? processQueue : new ProcessQueue();
    }

    /**
     * Returns next line from input from enqueued programs.
     * 
     * @return read line from input
     * @throws InvalidCommandException
     *             thrown if attempt to run of any of commands happen to fail
     *             (e.g. program doesn't exists)
     */
    public String getNextOutput() throws InvalidCommandException {
        String line;

        while (true) {
            if ((line = readNextLine()) != null)
                return line;

            if (processQueue.isEmpty())
                return null;
            loadNextReader();
        }
    }

    /**
     * Reads all enqueued programs' results till last output stream is closed.
     * 
     * <p>
     * WARNING!: If any of process doesn't finish method will stuck as infinite
     * loop.
     * </p>
     * 
     * @throws InvalidCommandException
     *             thrown if attempt to run of any of commands happen to fail
     *             (e.g. program doesn't exists)
     */
    public void rewind() throws InvalidCommandException {
        logger.trace("Reading to the end of stream");
        while (getNextOutput() != null)
            ;
    }

    /**
     * Reads next line. If it is first read it initializes reader.
     * 
     * @return next reader line, or null if stream ended
     * @throws InvalidCommandException
     *             thrown if attempt to run of any of commands happen to fail
     *             (e.g. program doesn't exists)
     */
    private String readNextLine() throws InvalidCommandException {
        logger.trace("Attempting to read next line");
        if (reader == null) {
            if (!processQueue.isEmpty())
                loadNextReader();
            else
                return null;
        }

        String line = null;
        try {
            while ((line = reader.readLine()) != null && line.isEmpty())
                ;
        } catch (IOException e) {
        }

        logger.detailedTrace("Reading next line: " + line);
        return line;
    }

    /**
     * If reader is null or all of its input streams reached end/were closed
     * tries to obtain streams from next program.
     * 
     * @throws InvalidCommandException
     *             thrown if attempt to run of any of commands happen to fail
     *             (e.g. program doesn't exists)
     */
    private void loadNextReader() throws InvalidCommandException {
        if (!processQueue.isEmpty()) {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }

            try {
                logger.trace("Obtaining next reader");
                currentProcess = processQueue.getNextProcess();
                Vector<InputStream> vector = new Vector<InputStream>();
                vector.add(currentProcess.getInputStream());
                vector.add(new ByteArrayInputStream(new byte[] { '\n' }));
                vector.add(currentProcess.getErrorStream());
                vector.add(new ByteArrayInputStream(new byte[] { '\n' }));
                reader = new BufferedReader(new InputStreamReader(new SequenceInputStream(
                        vector.elements())));
            } catch (IOException e) {
                logger.error("Failed to initiate next process (exception thrown)", e);
                throw new InvalidCommandException(e.getMessage());
            }
        }
    }

    /**
     * Kills current process.
     */
    public void killCurrentProcess() {
        if (currentProcess != null)
            currentProcess.destroy();
    }
}