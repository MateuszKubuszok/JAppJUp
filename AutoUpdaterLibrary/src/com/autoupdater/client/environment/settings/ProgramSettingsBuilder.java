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
package com.autoupdater.client.environment.settings;

/**
 * Builder that creates ProgramSettings instances.
 * 
 * @see com.autoupdater.client.environment.settings.ProgramSettings
 */
public class ProgramSettingsBuilder {
    private final ProgramSettings programSettings;

    private ProgramSettingsBuilder() {
        programSettings = new ProgramSettings();
    }

    /**
     * Creates new ProgramSettingsBuilder.
     * 
     * @return ProgramSettingsBuilder
     */
    public static ProgramSettingsBuilder builder() {
        return new ProgramSettingsBuilder();
    }

    public ProgramSettingsBuilder setProgramName(String programName) {
        programSettings.setProgramName(programName);
        return this;
    }

    public ProgramSettingsBuilder setProgramExecutableName(String programExecutableName) {
        programSettings.setProgramExecutableName(programExecutableName);
        return this;
    }

    public ProgramSettingsBuilder setPathToProgram(String pathToProgram) {
        programSettings.setPathToProgram(pathToProgram);
        return this;
    }

    public ProgramSettingsBuilder setPathToProgramDirectory(String pathToProgramDirectory) {
        programSettings.setPathToProgramDirectory(pathToProgramDirectory);
        return this;
    }

    public ProgramSettingsBuilder setServerAddress(String serverAddress) {
        programSettings.setServerAddress(serverAddress);
        return this;
    }

    public ProgramSettingsBuilder setDevelopmentVersion(String developmentVersion) {
        programSettings.setDevelopmentVersion(developmentVersion);
        return this;
    }

    public ProgramSettingsBuilder setDevelopmentVersion(boolean developmentVersion) {
        programSettings.setDevelopmentVersion(developmentVersion);
        return this;
    }

    /**
     * Builds ProgramSettings.
     * 
     * @return ProgramSettings
     */
    public ProgramSettings build() {
        return programSettings;
    }
}
