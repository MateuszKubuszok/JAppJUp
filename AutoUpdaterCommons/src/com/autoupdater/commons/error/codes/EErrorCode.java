package com.autoupdater.commons.error.codes;

/**
 * Describes error values that can be returned by Installer together with error
 * message that describes them.
 */
public enum EErrorCode {
    /**
     * Installation finished successfully.
     */
    SUCCESS(0, "Update installed succesfully"),

    /**
     * Update file don't exists.
     */
    FILE_DONT_EXISTS(2, "No update file found"),

    /**
     * Backup couldn't have been created.
     */
    BACKUP_ERROR(2, "Couldn't create backup"),

    /**
     * Attempt to perform post-installation execution resulted in error.
     */
    INTERRUPTED_SYSTEM_CALL(4, "Post-installatio nexecution failed"),

    /**
     * I/O error occurred.
     */
    IO_ERROR(5, "I/O error during installation"),

    /**
     * Too many arguments passed into Installer.
     */
    TOO_MANY_ARGUMENTS(7, "Too many arguments"),

    /**
     * Invalid argument passed into Installer.
     */
    INVALID_ARGUMENT(22, "Invalid argument");

    private final int code;
    private final String description;

    private EErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Returns error code value.
     * 
     * @return error code returned by program
     */
    public int getCode() {
        return code;
    }

    /**
     * Error code description.
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
