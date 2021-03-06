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
package com.autoupdater.client.environment.settings;

import java.io.File;

import com.autoupdater.client.environment.EnvironmentDefaultConfiguration;

/**
 * Class containing client settings. It describes properties common to all
 * update scenarios, e.g.: what is the path to installer, is there a proxy that
 * need to be used, what is the path to the client.
 * 
 * <p>
 * In each client instance there should be exactly one ClientSettings instance,
 * obtained through EnvionmentData.
 * </p>
 * 
 * <p>
 * Settings program-specific are stored inside ProgramSettings class.
 * </p>
 * 
 * @see com.autoupdater.client.environment.EnvironmentData
 * @see com.autoupdater.client.environment.settings.ProgramSettings
 */
public class ClientSettings {
    private String clientName;
    private String clientExecutableName;
    private String pathToClientDirectory;
    private String pathToClient;
    private String pathToInstaller;
    private String proxyAddress;
    private int proxyPort;

    /**
     * Creates instance of ClientSettings.
     */
    public ClientSettings() {
    }

    /**
     * Returns client's name used on server/in repository.
     * 
     * @return client's name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets clients's name.
     * 
     * @param clientName
     *            clients's name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName != null ? clientName : "";
    }

    /**
     * Returns client's executable name used by ProcessKiller.
     * 
     * @return client's executable name
     */
    public String getClientExecutableName() {
        return clientExecutableName;
    }

    /**
     * Sets clients's executable's name.
     * 
     * @param clientExecutableName
     *            clients's executable's name
     */
    public void setClientExecutableName(String clientExecutableName) {
        this.clientExecutableName = clientExecutableName != null ? clientExecutableName : "";
    }

    /**
     * Returns path to client's directory.
     * 
     * <p>
     * All update's installation/extraction paths will be relative to this
     * directory. It should be set bearing that in mind.
     * </p>
     * 
     * @return path to client's directory
     */
    public String getPathToClientDirectory() {
        return pathToClientDirectory;
    }

    /**
     * Sets path to client's directory.
     * 
     * @param pathToClientDirectory
     *            path to client's directory
     */
    public void setPathToClientDirectory(String pathToClientDirectory) {
        this.pathToClientDirectory = pathToClientDirectory != null ? pathToClientDirectory : "";
    }

    /**
     * Returns path to client executable.
     * 
     * <p>
     * Should contain path to client executable or, to be precise, console
     * command that will start up the client after self-update.
     * </p>
     * 
     * @return path to client executable
     */
    public String getPathToClient() {
        return pathToClient;
    }

    /**
     * Sets path to client
     * 
     * @param pathToClient
     *            path to client
     */
    public void setPathToClient(String pathToClient) {
        this.pathToClient = pathToClient != null ? pathToClient : "";
    }

    /**
     * Returns path to installer.
     * 
     * <p>
     * Should be absolute path to the Installer.jar file. Client will change
     * console command to "java jar path/to/Installler.jar", so there mustn't be
     * any additional information or parameters.
     * </p>
     * 
     * @return path to installer
     */
    public String getPathToInstaller() {
        return pathToInstaller;
    }

    /**
     * Sets path to installer.
     * 
     * @param pathToInstaller
     *            path to installer
     */
    public void setPathToInstaller(String pathToInstaller) {
        this.pathToInstaller = pathToInstaller != null ? pathToInstaller : "";

        if (this.pathToInstaller.startsWith(".")) {
            pathToInstaller = this.pathToInstaller.substring(1);

            for (File userDir = new File(System.getProperty("user.dir")); userDir != null; userDir = userDir
                    .getParentFile()) {
                File file = new File(userDir + pathToInstaller);

                if (file.exists()) {
                    this.pathToInstaller = file.getAbsolutePath();
                    return;
                }
            }
        }
    }

    /**
     * Returns proxy address.
     * 
     * <p>
     * Should be a valid URL address (e.g. http://proxy.address.net) or IP
     * address (e.g. 12.34.56.78).
     * </p>
     * 
     * @return proxy address
     */
    public String getProxyAddress() {
        return proxyAddress;
    }

    /**
     * Sets proxy address.
     * 
     * @param proxyAddress
     *            proxy address
     */
    public void setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress != null ? proxyAddress : "";
    }

    /**
     * Returns proxy port number.
     * 
     * @return proxy port number
     */
    public int getProxyPort() {
        return proxyPort;
    }

    /**
     * Sets proxy port.
     * 
     * @param proxyPort
     *            proxy port
     */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * Sets proxy port.
     * 
     * @param proxyPort
     *            proxy port
     */
    public void setProxyPort(String proxyPort) {
        try {
            this.proxyPort = Integer.parseInt(proxyPort);
        } catch (NumberFormatException e) {
            this.proxyPort = EnvironmentDefaultConfiguration.DEFAULT_PROXY_PORT;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[ClientSettings]").append('\n');
        builder.append("Name on server:\t\t").append(clientName).append('\n');
        builder.append("Installation dir:\t").append(pathToClientDirectory).append('\n');
        builder.append("Startup command:\t").append(pathToClient).append('\n');
        builder.append("Name for killer:\t").append(clientExecutableName).append('\n');
        builder.append("Installer exec.:\t").append(pathToInstaller).append('\n');
        builder.append("Proxy:\t\t\t\t")
                .append(proxyAddress != null ? (proxyAddress + ':' + proxyPort) : "none")
                .append('\n');

        return builder.toString();
    }
}
