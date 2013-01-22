package com.autoupdater.client.environment;

public class Mocks {
    private static EnvironmentData environmentData;

    public static EnvironmentData environmentData() {
        return (environmentData != null) ? environmentData
                : (environmentData = new EnvironmentData(
                        com.autoupdater.client.environment.settings.Mocks.clientSettings(),
                        com.autoupdater.client.environment.settings.Mocks.programsSettings()));
    }
}
