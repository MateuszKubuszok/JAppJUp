package com.autoupdater.client.environment;

/**
 * Thrown during search of ProgramSettings when there is no settings for given
 * model.
 * 
 * @see com.autoupdater.client.environment.ClientEnvironmentException
 */
public class ProgramSettingsNotFoundException extends ClientEnvironmentException {
    /**
     * Creates new ProgramSettingsNotFoundException instance.
     * 
     * @param message
     */
    public ProgramSettingsNotFoundException(String message) {
        super(message);
    }
}
