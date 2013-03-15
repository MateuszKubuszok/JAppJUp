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

/**
 * Replacement for java.util.logging.Level.
 */
public enum Level {
    /**
     * Indicates unrecoverable error.
     */
    ERROR(java.util.logging.Level.SEVERE),
    /**
     * Indicates recoverable error.
     */
    WARNING(java.util.logging.Level.WARNING),
    /**
     * General high level execution.
     */
    INFO(java.util.logging.Level.INFO),
    /**
     * Platform related information.
     */
    CONFIG(java.util.logging.Level.CONFIG),
    /**
     * Information used for debug.
     */
    DEBUG(java.util.logging.Level.FINE),
    /**
     * Single method execution (call).
     */
    TRACE(java.util.logging.Level.FINER),
    /**
     * Single method execution (run).
     */
    DETAILED_TRACE(java.util.logging.Level.FINEST),
    /**
     * All information.
     */
    ALL(java.util.logging.Level.ALL);

    private final java.util.logging.Level level;

    private Level(java.util.logging.Level level) {
        this.level = level;
    }

    /**
     * Returns original java.util.logging.Level.
     * 
     * @return java.util.logging.Level
     */
    public java.util.logging.Level getOrignialLevel() {
        return level;
    }

    /**
     * Returns level instance for given name or null if none exists.
     * 
     * @param name
     *            name for which value is searched
     * @return Level value
     */
    public static Level parse(String name) {
        for (Level level : values())
            if (level.name().equals(name))
                return level;
        return null;
    }

    /**
     * Returns level instance for given original level or null if none exists.
     * 
     * @param originalLevel
     *            original java.util.loging.Level
     * @return Level value
     */
    public static Level parse(java.util.logging.Level originalLevel) {
        for (Level level : values())
            if (level.level.equals(originalLevel))
                return level;
        return null;
    }
}
