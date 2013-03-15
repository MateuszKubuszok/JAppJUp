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
package net.jsdpu.process.killers;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.IOException;

import net.jsdpu.logger.Logger;

/**
 * Implementation of ProcessKillerInterface used for killing process in Mac OS
 * family systems.
 * 
 * @see net.jsdpu.process.killers.IProcessKiller
 */
public class MacOSProcessKiller implements IProcessKiller {
    private static final Logger logger = getLogger(MacOSProcessKiller.class);

    /**
     * Not yet implemented!
     * 
     * @TODO write actual procedure
     */
    @Override
    public void killProcess(String programName) throws IOException, InterruptedException {
        logger.error("MacOSProcessKiller is not yet implemented! (exceptio thrown)");
        throw new RuntimeException("MacOSProcessKiller is not yet implemented!");
    }
}
