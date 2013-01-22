package com.autoupdater.client.models;

import com.autoupdater.commons.messages.EInstallerMessage;

/**
 * Represents stage if Update's installation as well as the fact whether or not
 * Update should be installed.
 * 
 * @see com.autoupdater.client.models.Update
 */
public enum EUpdateStatus {
    /**
     * Used to mark Updates that shouldn't be installed.
     * 
     * <p>
     * Updates with this status won't be installed, even if they will be passed
     * into UpdateAggregatedDownloadService/FileAggregatedDownloadService/
     * InstallationAggregatedService.
     * </p>
     */
    NOT_SELECTED("Update not installed nor selected to install", false, false, null),

    /**
     * Used to mark updates that should be installed. While fetching/installing
     * status will start to change automatically.
     */
    SELECTED("Update is selected to install", true, false, null),

    /**
     * Update is being downloaded.
     */
    DOWNLOADING("Update is being downloaded", true, false, null),

    /**
     * Update status is marked as downloaded and queued to installation.
     */
    DOWNLOADED("Update is downloaded and wait for being installed", true, false, null),

    /**
     * Installation is being prepared.
     */
    PREPARING_INSTALLTION("Update installation is being prepared", true, false,
            EInstallerMessage.PREPARING_INSTALLATION),

    /**
     * Program directory is being backed up.
     */
    BACKING_UP("Directory is being backed up before update", true, false,
            EInstallerMessage.BACKUP_STARTED),

    /**
     * Backup completed successfully.
     */
    BACKED_UP("Backup completed", true, false, EInstallerMessage.BACKUP_FINISHED),

    /**
     * Backup creation failed.
     */
    BACK_UP_FAILED("Back up failed", true, true, EInstallerMessage.BACKUP_FAILED),

    /**
     * Update is being installed.
     */
    INSTALLING("Update is being installed", true, false, EInstallerMessage.INSTALLATION_STARTED),

    /**
     * Post-installation command execution is being performed.
     */
    POST_INSTALLATION_EXECUTION("Performing post-installation command execution", true, false,
            EInstallerMessage.POST_INSTALLATION_COMMAND_EXECUTION),

    /**
     * Post installation command execution finished successfully.
     */
    POST_INSTALLATION_EXECUTION_FINISHED("Performing post-installation command execution", true,
            false, EInstallerMessage.POST_INSTALLATION_COMMAND_EXECUTION_FINISHED),

    /**
     * Post installation command execution failed.
     */
    POST_INSTALLATION_EXECUTION_FAILED("Performing post-installation command execution", true,
            true, EInstallerMessage.POST_INSTALLATION_COMMAND_EXECUTION_FAILED),

    /**
     * Update installed successfully.
     * 
     * <p>
     * Update marked as installed will be ignored with any next attempt to
     * updates installation.
     * </p>
     * 
     * <p>
     * Update marked as installed will also be recognized as "finished" task in
     * updates' package installation.
     * </p>
     */
    INSTALLED("Update installed successfully", false, true, EInstallerMessage.INSTALLATION_FINISHED),

    /**
     * Update installation failed.
     * 
     * <p>
     * Update marked as failed will act as selected with any next attempt to
     * install updates.
     * </p>
     * 
     * <p>
     * Update marked as failed will also be recognized as "finished" task in
     * updates' package installation.
     * </p>
     */
    INSTALLATION_FAILED("Update couldn't be installed", true, true,
            EInstallerMessage.INSTALLATION_FAILED),

    /**
     * Update installation was cancelled.
     * 
     * <p>
     * Update marked as cancelled will act as selected with any next attempt to
     * install updates.
     * </p>
     * 
     * <p>
     * Update marked as cancelled will also be recognized as "finished" task in
     * updates' package installation.
     * </p>
     */
    CANCELLED("Update installation was cancelled", true, true, null);

    private final String message;
    private final boolean intendedToBeChanged;
    private final boolean updateAttemptFinished;
    private final EInstallerMessage installerMessage;

    private EUpdateStatus(String message, boolean intendedToBeChanged,
            boolean updateAttemptFinished, EInstallerMessage installerMessage) {
        this.message = message;
        this.intendedToBeChanged = intendedToBeChanged;
        this.updateAttemptFinished = updateAttemptFinished;
        this.installerMessage = installerMessage;
    }

    /**
     * Returns message describing current status.
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns installer's message related to this status.
     * 
     * @return installer's message
     */
    public EInstallerMessage installerMessage() {
        return installerMessage;
    }

    /**
     * Whether or not status should be changed.
     * 
     * <p>
     * Describes whether update is in the installation process, or is intended
     * to be installed at all.
     * </p>
     * 
     * @return true if Update is intended to be changed
     */
    public boolean isIntendedToBeChanged() {
        return intendedToBeChanged;
    }

    /**
     * Whether or not update attempt is finished.
     * 
     * <p>
     * Attempt is finished if not more actions will be performed despite the
     * result (success, failure).
     * </p>
     * 
     * @return true if Update attempt is finished
     */
    public boolean isUpdateAttemptFinished() {
        return updateAttemptFinished;
    }

    /**
     * Whether or not Update's attempt resulted in failure.
     * 
     * @return true if Update installation failed
     */
    public boolean isInstallationFailed() {
        return isIntendedToBeChanged() && isUpdateAttemptFinished();
    }

    @Override
    public String toString() {
        return message;
    }
}
