package com.autoupdater.client.environment.settings;

/**
 * Builder that creates ClientSettings instances.
 * 
 * @see com.autoupdater.client.environment.settings.ClientSettings
 */
public class ClientSettingsBuilder {
    private final ClientSettings clientSettings;

    private ClientSettingsBuilder() {
        clientSettings = new ClientSettings();
    }

    /**
     * Creates new ClientSettingsBuilder.
     * 
     * @return ClientSettingsBuilder
     */
    public static ClientSettingsBuilder builder() {
        return new ClientSettingsBuilder();
    }

    public ClientSettingsBuilder setClientName(String clientName) {
        clientSettings.setClientName(clientName);
        return this;
    }

    public ClientSettingsBuilder setClientExecutableName(String clientExecutableName) {
        clientSettings.setClientExecutableName(clientExecutableName);
        return this;
    }

    public ClientSettingsBuilder setPathToClient(String pathToClient) {
        clientSettings.setPathToClient(pathToClient);
        return this;
    }

    public ClientSettingsBuilder setPathToClientDirectory(String pathToClientDirectory) {
        clientSettings.setPathToClientDirectory(pathToClientDirectory);
        return this;
    }

    public ClientSettingsBuilder setPathToInstaller(String pathToInstaller) {
        clientSettings.setPathToInstaller(pathToInstaller);
        return this;
    }

    public ClientSettingsBuilder setProxyAddress(String proxyAddress) {
        clientSettings.setProxyAddress(proxyAddress);
        return this;
    }

    public ClientSettingsBuilder setProxyPort(String proxyPort) {
        clientSettings.setProxyPort(proxyPort);
        return this;
    }

    public ClientSettingsBuilder setProxyPort(int proxyPort) {
        clientSettings.setProxyPort(proxyPort);
        return this;
    }

    /**
     * Builds ClientSettings.
     * 
     * @return ClientSettings
     */
    public ClientSettings build() {
        return clientSettings;
    }
}
