package com.autoupdater.commons.messages;

/**
 * Messages returned by installer through console log.
 */
public enum EInstallerMessage {
    /**
     * Installation is being prepared.
     */
    PREPARING_INSTALLATION("Preparing installation..."),

    /**
     * Backup started.
     */
    BACKUP_STARTED("Backup started..."),

    /**
     * Backup complete.
     */
    BACKUP_FINISHED("Backup finished"),

    /**
     * Backup failed.
     */
    BACKUP_FAILED("Backup failed"),

    /**
     * Installation started.
     */
    INSTALLATION_STARTED("Installation started..."),

    /**
     * Installing process was finished, and post-installation process'
     * executions are being performed.
     */
    POST_INSTALLATION_COMMAND_EXECUTION("Executing post-installation commands..."),

    /**
     * Post-installation process' execution finished successfully.
     */
    POST_INSTALLATION_COMMAND_EXECUTION_FINISHED("Post-installation command execution failed"),

    /**
     * Post-installation process' execution failed.
     */
    POST_INSTALLATION_COMMAND_EXECUTION_FAILED("Post-installation command execution failed"),

    /**
     * Installation finished.
     */
    INSTALLATION_FINISHED("Installation finished"),

    /**
     * Installation failed.
     */
    INSTALLATION_FAILED("Installation failed");

    private String message;

    private EInstallerMessage(String message) {
        this.message = message;
    }

    /**
     * Returns message describing specific stage of installation.
     * 
     * @return message describing stage of installation
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
