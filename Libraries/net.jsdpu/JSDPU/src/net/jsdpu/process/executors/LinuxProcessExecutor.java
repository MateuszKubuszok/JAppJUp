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

import static java.util.Arrays.asList;
import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.process.executors.Commands.convertSingleCommand;
import static net.jsdpu.process.executors.MultiCaller.prepareCommand;

import java.util.ArrayList;
import java.util.List;

import net.jsdpu.logger.Logger;

/**
 * Implementation of AbstractProcessExecutor for Linux family systems.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class LinuxProcessExecutor extends AbstractProcessExecutor {
    private final static Logger logger = getLogger(LinuxProcessExecutor.class);

    @Override
    protected List<String[]> rootCommand(List<String[]> commands) {
        logger.trace("Preparing root command for: " + commands);
        List<String> command = new ArrayList<String>();
        command.add("pkexec");
        command.addAll(asList(prepareCommand(commands)));
        logger.detailedTrace("Root command: " + command);
        return convertSingleCommand(command);
    }
}
