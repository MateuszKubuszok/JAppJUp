package com.autoupdater.client.download.connections;

import java.util.HashMap;
import java.util.Map;

import com.autoupdater.client.environment.settings.ClientSettings;
import com.autoupdater.client.environment.settings.ProgramSettings;

/**
 * Factory used for obtaining PerProgramConnectionFactories.
 * 
 * <p>
 * At least one instance of ConnectionFactory is required in client to create
 * connections used by DownloadServices.
 * </p>
 * 
 * <p>
 * ConnectionFactory is bound to ClientConfiguration, and as such should be used
 * for creating connection only for original Client.
 * </p>
 * 
 * @see com.autoupdater.client.download.connections.PerProgramConnectionFactory
 * @see com.autoupdater.client.download.services.AbstractDownloadService
 */
public class ConnectionFactory {
    protected final ClientSettings clientConfiguration;

    private final Map<ProgramSettings, PerProgramConnectionFactory> perProgramConnectionFactories;

    /**
     * Creates ConnectionFactory instance.
     * 
     * @param clientSettings
     *            client's settings required by ConnectionFactory
     */
    public ConnectionFactory(ClientSettings clientSettings) {
        this.clientConfiguration = clientSettings;
        this.perProgramConnectionFactories = new HashMap<ProgramSettings, PerProgramConnectionFactory>();
    }

    /**
     * Returns PerProgramConnectionFactory for specified ProgramSettings.
     * 
     * <p>
     * For each ConnectionFactory, for given ProgramSettings always the same
     * PerProgramConnecitonFactory instance is returned.
     * </p>
     * 
     * @param programSettings
     *            program's settings
     * @return PerProgramConnectionFactory instance
     */
    public PerProgramConnectionFactory getPerProgramConnectionFactory(
            ProgramSettings programSettings) {
        if (perProgramConnectionFactories.containsKey(programSettings))
            return perProgramConnectionFactories.get(programSettings);

        PerProgramConnectionFactory newPerProgramConnectionFactory = new PerProgramConnectionFactory(
                clientConfiguration, programSettings);
        perProgramConnectionFactories.put(programSettings, newPerProgramConnectionFactory);
        return newPerProgramConnectionFactory;
    }
}
