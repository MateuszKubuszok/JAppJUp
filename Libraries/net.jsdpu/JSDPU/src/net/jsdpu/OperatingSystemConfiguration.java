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
package net.jsdpu;

/**
 * Contains configurations specific for some Operating Systems.
 */
public class OperatingSystemConfiguration {
    /**
     * Static class.
     */
    private OperatingSystemConfiguration() {
    }

    /**
     * Contains local application data for Windows system based on environment
     * data.
     */
    public static final String WINDOWS_LOCAL_APP_DATA = System.getenv("LOCALAPPDATA") != null ? System
            .getenv("LOCALAPPDATA") : System.getenv("APPDATA");

    /**
     * Contains local application data for Linux system based on environment
     * data.
     */
    public static final String LINUX_LOCAL_APP_DATA = System.getProperty("user.home") + "/.local";

    /**
     * Contains local application data for Mac OS system based on environment
     * data.
     */
    public static final String MAC_OS_LOCAL_APP_DATA = System.getProperty("user.home")
            + "/Library/Application Support";
}
