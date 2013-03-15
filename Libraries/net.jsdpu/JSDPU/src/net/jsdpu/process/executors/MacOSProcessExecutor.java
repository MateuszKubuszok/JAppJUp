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

import java.util.List;

import net.jsdpu.logger.Logger;

/**
 * Implementation of AbstractProcessExecutor for Mac OS family systems.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class MacOSProcessExecutor extends AbstractProcessExecutor {
    private static final Logger logger = getLogger(MacOSProcessExecutor.class);

    @Override
    protected List<String[]> rootCommand(List<String[]> commands) {
        logger.error("MacOSProcessExecutor is not yet implemented! (exception thrown)");
        throw new RuntimeException("MacOSProcessExecutor is not yet implemented!");
    }
}
