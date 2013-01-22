package com.autoupdater.client.installation;

/**
 * Defines the current status of an installation.
 */
public enum EInstallationStatus {
    /**
     * Installation service is calculating all commands, and haven't done
     * anything yet.
     */
    PREPARING_INSTALLATION("Preparing installation..."),

    /**
     * Installation service started to shutting down any updated program that is
     * run at the moment.
     */
    KILLING_UPDATED_APPLICATIONS("Shutting down applications that will be updated..."),

    /**
     * Installation service finished shutting down updated programs.
     */
    KILLED_UPDATED_APPLICATIONS("Updated applications shut down successfully"),

    /**
     * Installation service is installing updates at the moment.
     */
    INSTALLING("Installing updates..."),

    /**
     * Installation finished installing all updates.
     */
    INSTALLED("Updates installed successfully"),

    /**
     * Installation service failed to install updates.
     */
    FAILED("Installation failed");

    private String message;

    private EInstallationStatus(String message) {
        this.message = message;
    }

    /**
     * Message describing current installation status.
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
