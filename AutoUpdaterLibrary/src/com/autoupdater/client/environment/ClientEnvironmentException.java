package com.autoupdater.client.environment;

import com.autoupdater.client.AutoUpdaterClientException;

/**
 * Thrown when there is some inconsistency in EnvironmentData e.g. during
 * attempt to obtain non existing ProgramSettings or when EnvironmentData cannot
 * be read from default path.
 * 
 * @see com.autoupdater.client.environment.EnvironmentData
 * @see com.autoupdater.client.environment.EnvironmentDataManager
 */
public class ClientEnvironmentException extends AutoUpdaterClientException {
    /**
     * Creates instance of ClientEnvironmentException.
     * 
     * @param message
     *            message to be passed
     */
    public ClientEnvironmentException(String message) {
        super(message);
    }
}
