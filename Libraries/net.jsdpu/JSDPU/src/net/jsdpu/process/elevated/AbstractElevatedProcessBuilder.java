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
package net.jsdpu.process.elevated;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

/**
 * Abstract class implementing common functionality for ElevatedProcessBuilders.
 * 
 * @see net.jsdpu.process.elevated.LinuxElevatedProcessBuilder
 * @see net.jsdpu.process.elevated.MacOSElevatedProcessBuilder
 * @see net.jsdpu.process.elevated.WindowsElevatedProcessBuilder
 */
abstract class AbstractElevatedProcessBuilder implements ElevatedProcessBuilder {
    private final ProcessBuilder builder;
    private final List<String> command;

    /**
     * Created builder that creates elevated process.
     */
    public AbstractElevatedProcessBuilder() {
        builder = new ProcessBuilder();
        command = new ArrayList<String>();
    }

    @Override
    public List<String> getCommand() {
        return ImmutableList.copyOf(command);
    }

    @Override
    public ElevatedProcessBuilder setCommand(List<String> command) {
        this.command.clear();
        this.command.addAll(command);
        return this;
    }

    @Override
    public ElevatedProcessBuilder setCommand(String... command) {
        this.command.clear();
        this.command.addAll(Arrays.asList(command));
        return this;
    }

    @Override
    public File getDirectory() {
        return builder.directory();
    }

    @Override
    public ElevatedProcessBuilder setDirectory(File directory) {
        builder.directory(directory);
        return this;
    }

    @Override
    public Map<String, String> getEnvironment() {
        return builder.environment();
    }

    @Override
    public ElevatedProcessBuilder inheritIO() {
        builder.inheritIO();
        return this;
    }

    @Override
    public Redirect getRedirectError() {
        return builder.redirectError();
    }

    @Override
    public ElevatedProcessBuilder setRedirectError(File file) {
        builder.redirectError(file);
        return this;
    }

    @Override
    public ElevatedProcessBuilder setRedirectError(Redirect destination) {
        builder.redirectError(destination);
        return this;
    }

    @Override
    public boolean isRedirectErrorStream() {
        return builder.redirectErrorStream();
    }

    @Override
    public ElevatedProcessBuilder setRedirectErrorStream(boolean redirectErrorStream) {
        builder.redirectErrorStream(redirectErrorStream);
        return this;
    }

    @Override
    public Redirect getRedirectInput() {
        return builder.redirectInput();
    }

    @Override
    public ElevatedProcessBuilder setRedirectInput(File file) {
        builder.redirectInput(file);
        return this;
    }

    @Override
    public ElevatedProcessBuilder setRedirectInput(Redirect source) {
        builder.redirectInput(source);
        return this;
    }

    @Override
    public Redirect getRedirectOutput() {
        return builder.redirectOutput();
    }

    @Override
    public ElevatedProcessBuilder setRedirectOutput(File file) {
        builder.redirectOutput(file);
        return this;
    }

    @Override
    public ElevatedProcessBuilder setRedirectOutput(Redirect destination) {
        builder.redirectOutput(destination);
        return this;
    }

    @Override
    public Process start() throws IOException {
        return getProcessBuilder().start();
    }

    /**
     * Returns ProcessBuilder used by this ElevatedProcessBuilder.
     * 
     * @return ProcessBuilder
     */
    protected ProcessBuilder getBuilder() {
        return builder;
    }
}
