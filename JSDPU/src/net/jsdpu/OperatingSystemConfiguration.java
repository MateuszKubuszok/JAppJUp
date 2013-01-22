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
