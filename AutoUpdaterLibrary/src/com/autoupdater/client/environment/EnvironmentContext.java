package com.autoupdater.client.environment;

import static com.autoupdater.client.environment.EnvironmentDefaultConfiguration.*;

/**
 * Context used by EnvironmentDataManager to create, read and save
 * EnvironmentData.
 * 
 * <p>
 * Is initiated with values from EnvironmentDefualtConfiguration, which can be
 * changed later.
 * </p>
 * 
 * @see com.autoupdater.client.environment.EnvironmentData
 * @see com.autoupdater.client.environment.EnvironmentDataManager
 * @see com.autoupdater.client.environment.EnvironmentDefaultConfiguration
 */
public class EnvironmentContext {
    private String defaultClientName;
    private String defaultClientExecutable;
    private String temporaryDirectory;
    private String localAppData;
    private String settingsXMLPath;
    private String installationDataXMLPath;

    private String defaultClientDir;
    private String defaultPathToClient;
    private String defaultPathToInstaller;
    private String defaultProxyAddress;
    private int defaultProxyPort;

    /**
     * Creates Context that can be used for defining default values for
     * EnviromentDataManager.
     */
    public EnvironmentContext() {
        setSettingsXMLPath(DEFAULT_SETTINGS_XML_PATH);
        setInstallationDataXMLPath(DEFAULT_INSTALLATION_DATA_XML_PATH);

        setDefaultClientName(DEFAULT_CLIENT_NAME);
        setDefaultClientExecutable(DEFAULT_CLIENT_EXECUTABLE_NAME);
        setTempporaryDirectory(DEFAULT_TEMPORARY_DIRECTORY);
        setLocalAppData(DEFAULT_LOCAL_APPLICATION_DATA);

        setDefaultPathToClientDirectory(DEFAULT_CLIENT_DIRECTORY_PATH);
        setDefaultPathToClient(DEFAULT_CLIENT_EXECUTABLE_PATH);
        setDefaultPathToInstaller(DEFAULT_INSTALLER_PATH);
        setDefaultProxyAddress(DEFAULT_PROXY_ADDRESS);
        setDefaultProxyPort(DEFAULT_PROXY_PORT);
    }

    /**
     * Gets Client's default settings XML location.
     * 
     * <p>
     * Used by EnvironmentDataManager to find location of XML files.
     * </p>
     * 
     * @see #setSettingsXMLPath(String)
     * 
     * @return Client's default settings XML location
     */
    public String getSettingsXMLPath() {
        return settingsXMLPath;
    }

    /**
     * Sets Client's default settings XML location.
     * 
     * @see #getSettingsXMLPath()
     * 
     * @param settingsXML
     *            Client's default settings XML location
     */
    public void setSettingsXMLPath(String settingsXML) {
        this.settingsXMLPath = settingsXML;
    }

    /**
     * Gets Client's default installation data XML location.
     * 
     * <p>
     * Used by EnvironmentDataManager to find location of XML files.
     * </p>
     * 
     * @see #setInstallationDataXMLPath(String)
     * 
     * @return Client's default installation data XML location
     */
    public String getInstallationDataXMLPath() {
        return installationDataXMLPath;
    }

    /**
     * Sets Client's default installation data XML location.
     * 
     * @see #getInstallationDataXMLPath()
     * 
     * @param installationDataXML
     *            Client's default installation data XML location
     */
    public void setInstallationDataXMLPath(String installationDataXML) {
        this.installationDataXMLPath = installationDataXML;
    }

    /**
     * Gets Client's default name.
     * 
     * <p>
     * Used by EnvironmentDataManager to set Client's default name.
     * </p>
     * 
     * @see #setDefaultClientName(String)
     * @see com.autoupdater.client.environment.settings.ClientSettings#getClientName()
     * 
     * @return Client's default name
     */
    public String getDefaultClientName() {
        return defaultClientName;
    }

    /**
     * Sets Client's default name.
     * 
     * @see #getDefaultClientName()
     * @see com.autoupdater.client.environment.settings.ClientSettings#getClientName()
     * 
     * @param defaultClientName
     *            Client's default name
     */
    public void setDefaultClientName(String defaultClientName) {
        this.defaultClientName = defaultClientName;
    }

    /**
     * Gets Client's default executable's name.
     * 
     * <p>
     * Used by EnvironmentDataManager to set Client's default executable.
     * </p>
     * 
     * @see #getDefaultPathToClient()
     * @see #setDefaultClientExecutable(String)
     * @see com.autoupdater.client.environment.settings.ClientSettings#getClientExecutableName()
     * 
     * @return Client's default executable's name
     */
    public String getDefaultClientExecutable() {
        return defaultClientExecutable;
    }

    /**
     * Sets Client's default executable's name.
     * 
     * @see #getDefaultClientExecutable()
     * @see com.autoupdater.client.environment.settings.ClientSettings#getClientExecutableName()
     * 
     * @param defaultClientExecutable
     *            Client's default executable's name
     */
    public void setDefaultClientExecutable(String defaultClientExecutable) {
        this.defaultClientExecutable = defaultClientExecutable;
    }

    /**
     * Gets Client's default temporary directory.
     * 
     * <p>
     * Not yet implemented in code.
     * </p>
     * 
     * @TODO
     * @see #setTempporaryDirectory(String)
     * 
     * @return Client's default temporary directory
     */
    public String getTemporaryDirectory() {
        return temporaryDirectory;
    }

    /**
     * Sets Client's default temporary directory.
     * 
     * @TODO
     * @see #getTemporaryDirectory()
     * 
     * @param temporaryDirectory
     *            Client's default temporary directory
     */
    public void setTempporaryDirectory(String temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
    }

    /**
     * Gets Client's default local application data (settings storage place).
     * 
     * <p>
     * Not yet implemented in code.
     * </p>
     * 
     * @TODO
     * @see #setLocalAppData(String)
     * 
     * @return Client's default local application data
     */
    public String getLocalAppData() {
        return localAppData;
    }

    /**
     * Sets Client's default local application data (settings storage place).
     * 
     * @see #getLocalAppData()
     * 
     * @param localAppData
     *            Client's default local application data
     */
    public void setLocalAppData(String localAppData) {
        this.localAppData = localAppData;
    }

    /**
     * Gets Client's default directory path.
     * 
     * <p>
     * Used by EnvironmentDataManager to set Client's default directory path.
     * </p>
     * 
     * @see #setDefaultPathToClientDirectory(String)
     * 
     * @return Client's default directory path
     */
    public String getDefaultPathToClientDirectory() {
        return defaultClientDir;
    }

    /**
     * Sets Client's default directory path.
     * 
     * @see #getDefaultPathToClientDirectory()
     * 
     * @param defaultClientDir
     *            Client's default directory path
     */
    public void setDefaultPathToClientDirectory(String defaultClientDir) {
        this.defaultClientDir = defaultClientDir;
    }

    /**
     * Gets Client's default executable path.
     * 
     * <p>
     * Used by EnvironmentDataManager to set Client's default executable path.
     * </p>
     * 
     * @see #getDefaultClientExecutable()
     * @see #setDefaultPathToClient(String)
     * 
     * @return Client's default directory path
     */
    public String getDefaultPathToClient() {
        return defaultPathToClient;
    }

    /**
     * Sets Client's default executable path.
     * 
     * @see #getDefaultPathToClient()
     * 
     * @param defaultPathToClient
     *            Client's default executable path
     */
    public void setDefaultPathToClient(String defaultPathToClient) {
        this.defaultPathToClient = defaultPathToClient;
    }

    /**
     * Gets Client's default installer's path.
     * 
     * <p>
     * Used by EnvironmentDataManager to set Client's default installer's path.
     * </p>
     * 
     * @see #setDefaultPathToInstaller(String)
     * 
     * @return Client's default installer's path
     */
    public String getDefaultPathToInstaller() {
        return defaultPathToInstaller;
    }

    /**
     * Sets Client's default installer's path.
     * 
     * @see #getDefaultPathToInstaller()
     * 
     * @param defaultPathToInstaller
     *            Client's default installer's path
     */
    public void setDefaultPathToInstaller(String defaultPathToInstaller) {
        this.defaultPathToInstaller = defaultPathToInstaller;
    }

    /**
     * Gets Client's default proxy address.
     * 
     * <p>
     * Used by EnvironmentDataManager to set Client's default proxy address.
     * </p>
     * 
     * @see #setDefaultProxyAddress(String)
     * 
     * @return Client's default proxy address
     */
    public String getDefaultProxyAddress() {
        return defaultProxyAddress;
    }

    /**
     * Sets Client's default proxy address.
     * 
     * @see #getDefaultProxyAddress()
     * 
     * @param defaultProxyAddress
     *            Client's default proxy address
     */
    public void setDefaultProxyAddress(String defaultProxyAddress) {
        this.defaultProxyAddress = defaultProxyAddress;
    }

    /**
     * Gets Client's default proxy port.
     * 
     * <p>
     * Used by EnvironmentDataManager to set Client's default proxy port.
     * </p>
     * 
     * @see #getDefaultProxyPort()
     * 
     * @return Client's default proxy port
     */
    public int getDefaultProxyPort() {
        return defaultProxyPort;
    }

    /**
     * Sets Client's default proxy port.
     * 
     * @see #getDefaultProxyAddress()
     * 
     * @param defaultProxyPort
     *            Client's default proxy posrt
     */
    public void setDefaultProxyPort(int defaultProxyPort) {
        this.defaultProxyPort = defaultProxyPort;
    }
}
