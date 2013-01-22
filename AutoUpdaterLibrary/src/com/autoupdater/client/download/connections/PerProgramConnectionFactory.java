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
