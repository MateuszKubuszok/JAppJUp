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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

/**
 * Abstract class implementing common functionality for
 * IndependentProcessBuilders.
 * 
 * @see net.jsdpu.process.independent.IndependentProcessBuilder
 * @see net.jsdpu.process.independent.LinuxIndependentProcessBuilder
 * @see net.jsdpu.process.independent.MacOSIndependentProcessBuilder
 * @see net.jsdpu.process.independent.WindowsIndependentProcessBuilder
 */
abstract class AbstractIndependentProcessBuilder implements IndependentProcessBuilder {
    private final ProcessBuilder builder;
    private final List<String> command;

    /**
     * Created builder that creates elevated process.
     */
    public AbstractIndependentProcessBuilder() {
        builder = new ProcessBuilder();
        command = new ArrayList<String>();
    }

    @Override
    public List<String> getCommand() {
        return ImmutableList.copyOf(command);
    }

    @Override
    public IndependentProcessBuilder setCommand(List<String> command) {
        this.command.clear();
        this.command.addAll(command);
        return this;
    }

    @Override
    public IndependentProcessBuilder setCommand(String... command) {
        this.command.clear();
        this.command.addAll(Arrays.asList(command));
        return this;
    }

    @Override
    public File getDirectory() {
        return builder.directory();
    }

    @Override
    public IndependentProcessBuilder setDirectory(File directory) {
        builder.directory(directory);
        return this;
    }

    @Override
    public Map<String, String> getEnvironment() {
        return builder.environment();
    }

    @Override
    public abstract void start() throws IOException;

    /**
     * Returns ProcessBuilder used by this ElevatedProcessBuilder.
     * 
     * @return ProcessBuilder
     */
    protected ProcessBuilder getBuilder() {
        return builder;
    }
}
