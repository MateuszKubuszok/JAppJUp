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
package net.jsdpu.logger;

import static com.google.common.base.Joiner.on;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * LogManager reponsible for settings up Logger. 
 */
public class LogManager {
    private final java.util.logging.LogManager logManager;

    /**
     * Returns LogManager instance.
     * 
     * @return LogManager instance
     */
    public static LogManager getLogManager() {
        return new LogManager(java.util.logging.LogManager.getLogManager());
    }

    /**
     * Initiates LogManager.
     * @param logManager java.util.logging.LogManager instance
     */
    private LogManager(java.util.logging.LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * Reads configuration.
     * 
     * @param is input stream
     * @throws IOException thrown when there are problems reading from the stream
     */
    public void readConfiguration(InputStream is) throws IOException {
        Properties properties = new Properties();
        properties.load(is);
        for (Object key : properties.keySet()) {
            Level level = Level.parse(properties.getProperty((String) key));
            if (level != null)
                properties.setProperty((String) key, level.getOrignialLevel().getName());
        }
        logManager.readConfiguration(new ByteArrayInputStream(on('\n').join(properties.entrySet())
                .getBytes()));
    }
}
