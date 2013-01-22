package com.autoupdater.installer;


public class Values {
    public static class ClientSettings {
        public static final String clientName = "AutoUpdater";
        public static final String clientExecutableName = "Client.jar";
        public static final String installer = "Installer.jar";
        public static final String uacHandler = "UACHandler.exe";
        public static final String proxyAddress = "UACHandler.exe";
        public static final int proxyPort = 8888;
    }

    public static class ProgramSettings {
        public static final String programName = "Program 1";
        public static final String programExecutableName = "Program1.exe";
        public static final String serverAddress = "http://localhost:8080/server";
        public static boolean developmentVersion = true;
    }

    public static class ProgramSettings2 {
        public static final String programName = "Program 2";
        public static final String programExecutableName = "Program2.exe";
        public static final String serverAddress = "http://localhost:8080/server";
        public static boolean developmentVersion = false;
    }
}
