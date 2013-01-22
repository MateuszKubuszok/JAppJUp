package com.autoupdater.client;

import com.autoupdater.client.models.EUpdateStrategy;
import com.autoupdater.client.models.VersionNumber;

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

    public static class Program {
        public static final String name = ProgramSettings.programName;
        public static final String serverAddress = ProgramSettings.serverAddress;
    }

    public static class Program2 {
        public static final String name = ProgramSettings2.programName;
        public static final String serverAddress = ProgramSettings2.serverAddress;
    }

    public static class Package {
        public static final String name = "Package 1";
        public static final String ID = "1";
        public static final VersionNumber version = new VersionNumber(1, 0, 0, 0);
    }

    public static class Package2 {
        public static final String name = "Package 2";
        public static final String ID = "2";
        public static final VersionNumber version = new VersionNumber(1, 5, 0, 0);
    }

    public static class Package3 {
        public static final String name = "Package 3";
        public static final String ID = "3";
        public static final VersionNumber version = new VersionNumber(1, 2, 3, 4);
    }

    public static class Changelog {
        public static final String content = "Initial release";
        public static final VersionNumber version = new VersionNumber(1, 0, 0, 0);
    }

    public static class Changelog2 {
        public static final String content = "Update";
        public static final VersionNumber version = new VersionNumber(1, 5, 0, 0);
    }

    public static class Update {
        public static final String packageName = "Package 1";
        public static final String packageID = "1";
        public static final VersionNumber version = new VersionNumber(1, 2, 3, 4);
        public static final boolean developmentVersion = true;
        public static final String changelog = "Some changes";
        public static final EUpdateStrategy type = EUpdateStrategy.UNZIP;
        public static final String originalName = "name.zip";
        public static final String relativePath = "/";
        public static final String updaterCommand = "";
    }

    public static class Bug {
        public static final String description = "Some bug description";
    }

    public static class Bug2 {
        public static final String description = "Some other bug description";
    }
}
