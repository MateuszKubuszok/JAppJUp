/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
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
package com.autoupdater.client.environment;

import static java.io.File.separator;
import static java.lang.System.getProperty;
import static net.jsdpu.JavaSystemUtils.getJavaExecutablePath;
import net.jsdpu.EOperatingSystem;

/**
 * Defines default configuration for EnvironmentData.
 * 
 * @see com.autoupdater.client.environment.EnvironmentContext
 */
public class EnvironmentDefaultConfiguration {
    /**
     * Operating system Client is working on.
     */
    public static final EOperatingSystem os = EOperatingSystem.current();

    /**
     * Defines client name used on server/in repository.
     */
    public static final String DEFAULT_CLIENT_NAME = "AutoUpdater";

    /**
     * Defines client executable name.
     */
    public static final String DEFAULT_CLIENT_EXECUTABLE_NAME = "Client.jar";

    /**
     * Defines default
     */
    public static final String DEFAULT_TEMPORARY_DIRECTORY = getProperty("java.io.tmpdir")
            + DEFAULT_CLIENT_NAME;

    /**
     * Defines default localization of local app data for client.
     */
    public static final String DEFAULT_LOCAL_APPLICATION_DATA = os.getLocalAppData() + separator
            + DEFAULT_CLIENT_NAME;

    /**
     * Defines default localization of settings XML file.
     */
    public static final String DEFAULT_SETTINGS_XML_PATH = DEFAULT_LOCAL_APPLICATION_DATA
            + separator + "settings.xml";

    /**
     * Defines default localization of installation data XML file.
     */
    public static final String DEFAULT_INSTALLATION_DATA_XML_PATH = DEFAULT_LOCAL_APPLICATION_DATA
            + separator + "installationData.xml";

    /**
     * Defines default path to client directory.
     */
    public static final String DEFAULT_CLIENT_DIRECTORY_PATH = ".";

    /**
     * Defines default path to client.
     */
    public static final String DEFAULT_CLIENT_EXECUTABLE_PATH = getJavaExecutablePath() + " -jar "
            + DEFAULT_CLIENT_DIRECTORY_PATH + separator + "Client.jar";

    /**
     * Defines default path to Installer.jar.
     */
    public static final String DEFAULT_INSTALLER_PATH = DEFAULT_CLIENT_DIRECTORY_PATH + separator
            + "Installer.jar";

    /**
     * Defines default proxy address.
     */
    public static final String DEFAULT_PROXY_ADDRESS = "127.0.0.1";

    /**
     * Defines default proxy port.
     */
    public static final int DEFAULT_PROXY_PORT = 8080;
}
