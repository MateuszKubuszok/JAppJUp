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
package com.autoupdater.client.download.connections;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;

/**
 * Factory creating connections for specified ClienSettings and ProgramSettings.
 * 
 * <p>
 * Should be spawned only by ConnectionFactory instance.
 * </p>
 * 
 * <p>
 * Uses URLResolver for generating URLs.
 * </p>
 * 
 * @see com.autoupdater.client.download.connections.ConnectionFactory
 */
public class PerProgramConnectionFactory extends ConnectionFactory {
    private final URLResolver urlResolver;

    /**
     * Creates PerProgramConnectionFactory instance.
     * 
     * @param clientConfiguration
     *            client's configuration
     * @param programConfiguration
     *            program's configuration
     */
    PerProgramConnectionFactory(ClientSettings clientConfiguration,
            ProgramSettings programConfiguration) {
        super(clientConfiguration);
        this.urlResolver = new URLResolver(programConfiguration);
    }

    /**
     * Creates connection for obtaining program/packages info.
     * 
     * @return connection that can be used for obtaining data
     * @throws IOException
     *             thrown if error occurs while opening connection
     */
    public HttpURLConnection createPackagesInfoConnection() throws IOException {
        return createConnectionForURL(urlResolver.getPackagesInfoURL());
    }

    /**
     * Creates connection for obtaining update info.
     * 
     * @param packageID
     *            ID of package for which update info should be obtained
     * @return connection that can be used for obtaining data
     * @throws IOException
     *             thrown if error occurs while opening connection
     */
    public HttpURLConnection createUpdateInfoConnection(String packageID) throws IOException {
        return createConnectionForURL(urlResolver.getUpdateInfoURL(packageID));
    }

    /**
     * Creates connection for obtaining changelog info.
     * 
     * @param packageID
     *            ID of package for which changelog info should be obtained
     * @return connection that can be used for obtaining data
     * @throws IOException
     *             thrown if error occurs while opening connection
     */
    public HttpURLConnection createChangelogInfoConnection(String packageID) throws IOException {
        return createConnectionForURL(urlResolver.getChangelogInfoURL(packageID));
    }

    /**
     * Creates connection for obtaining bugs info.
     * 
     * @param programName
     *            name of program for which bugs info should be obtained
     * @return connection that can be used for obtaining data
     * @throws IOException
     *             thrown if error occurs while opening connection
     */
    public HttpURLConnection createBugsInfoConnection(String programName) throws IOException {
        return createConnectionForURL(urlResolver.getBugsInfoURL(programName));
    }

    /**
     * Creates connection for obtaining file.
     * 
     * @param updateID
     *            ID of update for which update file should be obtained
     * @return connection that can be used for obtaining data
     * @throws IOException
     *             thrown if error occurs while opening connection
     */
    public HttpURLConnection createFileConnection(String updateID) throws IOException {
        return createConnectionForURL(urlResolver.getFileURL(updateID));
    }

    /**
     * Creates connection for given URL address.
     * 
     * @param urlAddress
     *            URL for which connection should be opened
     * @return connection that can be used for obtaining data
     * @throws IOException
     *             thrown if error occurs while opening connection
     */
    private HttpURLConnection createConnectionForURL(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection httpURLConnection;
        if (clientConfiguration.getProxyAddress() != null
                && !clientConfiguration.getProxyAddress().isEmpty())
            httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP,
                    new InetSocketAddress(clientConfiguration.getProxyAddress(),
                            clientConfiguration.getProxyPort())));
        else
            httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setDefaultUseCaches(false);
        return httpURLConnection;
    }
}
