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
 * Used for passing messages to/from InstallationService.
 */
public class InstallationServiceMessage {
    private final String message;
    private final boolean interruptedByError;

    /**
     * Creates new installation message.
     * 
     * @param message
     *            message to pass
     */
    public InstallationServiceMessage(String message) {
        this.message = message;
        interruptedByError = false;
    }

    /**
     * Creates new installation message with information about error.
     * 
     * @param message
     *            message to pass
     * @param errorOccured
     *            whether or not error occurred
     */
    public InstallationServiceMessage(String message, boolean errorOccured) {
        this.message = message;
        this.interruptedByError = errorOccured;
    }

    /**
     * Returns passed message.
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Whether or not installation service was interrupted by error.
     * 
     * @return true if error occurred, false otherwise
     */
    public boolean isInterruptedByError() {
        return interruptedByError;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
