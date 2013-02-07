package com.autoupdater.client.environment;

import static java.io.File.separator;
import static java.lang.System.getProperty;
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
    public static final String DEFAULT_CLIENT_EXECUTABLE_PATH = "java -jar "
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
