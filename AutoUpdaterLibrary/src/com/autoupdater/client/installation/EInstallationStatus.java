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
    INSTALLING_UPDATES("Installing updates..."),

    /**
     * Installation finished installing all updates.
     */
    INSTALLATION_SUCCEEDED("Updates installed successfully"),

    /**
     * Installation service failed to install updates.
     */
    INSTALLATION_FAILED("Installation failed");

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
