package com.autoupdater.client.installation;

import com.autoupdater.client.utils.services.IObserver;

/**
 * Interface marking listener as InstallationListener. Ensures that it will
 * accept InstallationServiceMessage.
 * 
 * @see com.autoupdater.client.installation.InstallationServiceMessage
 */
public interface IInstallationListener extends
        IObserver<InstallationServiceMessage> {
}
