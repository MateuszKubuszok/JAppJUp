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
package net.jsdpu.process.independent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This class is used to create operating system independent processes.
 * 
 * <p>
 * It uses ProcessBuilder for creating process, but adds its own functionality
 * to ensure that process is not run as Java VM's child process.
 * </p>
 * 
 * <p>
 * Because created process is completely independent from Java VM, it cannot be
 * killed by it, and no IO stream is available.
 * </p>
 * 
 * @see java.lang.ProcessBuilder
 */
public interface IndependentProcessBuilder {
    /**
     * @see java.lang.ProcessBuilder#command()
     */
    @SuppressWarnings("javadoc")
    public List<String> getCommand();

    /**
     * @see java.lang.ProcessBuilder#command(List)
     */
    @SuppressWarnings("javadoc")
    public IndependentProcessBuilder setCommand(List<String> command);

    /**
     * @see java.lang.ProcessBuilder#command(String...)
     */
    @SuppressWarnings("javadoc")
    public IndependentProcessBuilder setCommand(String... command);

    /**
     * @see java.lang.ProcessBuilder#directory()
     */
    @SuppressWarnings("javadoc")
    public File getDirectory();

    /**
     * @see java.lang.ProcessBuilder#directory(File)
     */
    @SuppressWarnings("javadoc")
    public IndependentProcessBuilder setDirectory(File directory);

    /**
     * @see java.lang.ProcessBuilder#environment()
     */
    @SuppressWarnings("javadoc")
    public Map<String, String> getEnvironment();

    /**
     * @see java.lang.ProcessBuilder#start()
     */
    @SuppressWarnings({ "javadoc" })
    public void start() throws IOException;
}
