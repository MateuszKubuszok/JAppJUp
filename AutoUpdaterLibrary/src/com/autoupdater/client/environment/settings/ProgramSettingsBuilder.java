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
