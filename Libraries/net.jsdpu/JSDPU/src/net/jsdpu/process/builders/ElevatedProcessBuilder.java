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
package net.jsdpu.process.builders;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Map;

/**
 * This class is used to create operating system elevated processes.
 * 
 * <p>
 * It uses ProcessBuilder for creating process, but adds its own functionality
 * to perform an elevation.
 * </p>
 * 
 * @see java.lang.ProcessBuilder
 */
public interface ElevatedProcessBuilder {
    /**
     * @see java.lang.ProcessBuilder#command()
     */
    public List<String> getCommand();

    /**
     * @see java.lang.ProcessBuilder#command(List)
     */
    public ElevatedProcessBuilder setCommand(List<String> command);

    /**
     * @see java.lang.ProcessBuilder#command(String...)
     */
    public ElevatedProcessBuilder setCommand(String... command);

    /**
     * @see java.lang.ProcessBuilder#directory()
     */
    public File getDirectory();

    /**
     * @see java.lang.ProcessBuilder#directory(File)
     */
    public ElevatedProcessBuilder setDirectory(File directory);

    /**
     * @see java.lang.ProcessBuilder#environment()
     */
    public Map<String, String> getEnvironment();

    /**
     * @see java.lang.ProcessBuilder#inheritIO()
     */
    public ElevatedProcessBuilder inheritIO();

    /**
     * @see java.lang.ProcessBuilder#redirectError()
     */
    public Redirect getRedirectError();

    /**
     * @see java.lang.ProcessBuilder#redirectError(File)
     */
    public ElevatedProcessBuilder setRedirectError(File file);

    /**
     * @see java.lang.ProcessBuilder#redirectError(Redirect)
     */
    public ElevatedProcessBuilder setRedirectError(Redirect destination);

    /**
     * @see java.lang.ProcessBuilder#redirectErrorStream()
     */
    public boolean isRedirectErrorStream();

    /**
     * @see java.lang.ProcessBuilder#redirectErrorStream(boolean)
     */
    public ElevatedProcessBuilder setRedirectErrorStream(boolean redirectErrorStream);

    /**
     * @see java.lang.ProcessBuilder#redirectInput()
     */
    public Redirect getRedirectInput();

    /**
     * @see java.lang.ProcessBuilder#redirectInput(File)
     */
    public ElevatedProcessBuilder setRedirectInput(File file);

    /**
     * @see java.lang.ProcessBuilder#redirectInput(Redirect)
     */
    public ElevatedProcessBuilder setRedirectInput(Redirect destination);

    /**
     * @see java.lang.ProcessBuilder#redirectOutput()
     */
    public Redirect getRedirectOutput();

    /**
     * @see java.lang.ProcessBuilder#redirectOutput(File)
     */
    public ElevatedProcessBuilder setRedirectOutput(File file);

    /**
     * @see java.lang.ProcessBuilder#redirectOutput(Redirect)
     */
    public ElevatedProcessBuilder setRedirectOutput(Redirect destination);

    /**
     * @see java.lang.ProcessBuilder#start()
     */
    public Process start();
}
