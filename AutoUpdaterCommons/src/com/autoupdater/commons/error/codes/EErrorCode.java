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
