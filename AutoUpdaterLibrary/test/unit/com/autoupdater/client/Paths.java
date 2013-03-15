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
