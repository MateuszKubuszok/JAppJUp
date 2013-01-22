package com.autoupdater.client;

import java.io.File;

public class Paths {
    public static class System {
        public static final String tmpDir;
        static {
            String dir = java.lang.System.getProperty("java.io.tmpdir");
            if (dir.endsWith("/"))
                dir = dir.substring(0, dir.length() - 1);
            else if (dir.endsWith("\\"))
                dir = dir.substring(0, dir.length() - 2);
            tmpDir = dir;
        }

        public static String userDir;
        static {
            String dir = java.lang.System.getProperty("user.dir");
            if (dir.endsWith("/"))
                dir = dir.substring(0, dir.length() - 1);
            else if (dir.endsWith("\\"))
                dir = dir.substring(0, dir.length() - 2);
            userDir = dir;
        }
    }

    public static class Library {
        public final static String testDir = System.tmpDir + File.separator + "Tests";

        public final static String clientDir = System.userDir + File.separator + "AutoUpdater";

        public final static String clientPath = clientDir + File.separator
                + Values.ClientSettings.clientExecutableName;

        public static final String executablePath = clientDir + File.separator
                + Values.ClientSettings.clientExecutableName;

        public static final String installerPath = clientDir + File.separator
                + Values.ClientSettings.installer;

        public static final String uacHandlerPath = clientDir + File.separator
                + Values.ClientSettings.uacHandler;
    }

    public static class Setting {
        public final static String settingsXMLPath = Library.testDir + File.separator
                + "settings.xml";
        public final static String installationDataXMLPath = Library.testDir + File.separator
                + "installationData.xml";
    }

    public static class Installations {
        public static class Program {
            public static final String programDir = System.userDir + File.separator + "Program1";

            public static final String programPath = programDir + File.separator
                    + Values.ProgramSettings.programExecutableName;

        }

        public static class Program2 {
            public static final String programDir = System.userDir + File.separator + "Program2";

            public static final String programPath = programDir + File.separator
                    + Values.ProgramSettings2.programExecutableName;
        }
    }
}
