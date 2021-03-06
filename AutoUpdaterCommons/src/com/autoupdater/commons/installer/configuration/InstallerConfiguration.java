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
package com.autoupdater.commons.installer.configuration;

import java.io.File;

/**
 * Contains settings for Installer, namely backup directory.
 */
public class InstallerConfiguration {
    /**
     * Static class.
     */
    private InstallerConfiguration() {
    }

    /**
     * Path to Installer's backup directory.
     */
    public final static String BACKUP_DIRECTORY;
    static {
        String dir = System.getProperty("java.io.tmpdir");
        if (dir.endsWith("\\") || dir.endsWith("/"))
            dir = dir.substring(0, dir.length() - 1);
        dir += File.separator + "AutoUpdater" + File.separator + "Backup";
        BACKUP_DIRECTORY = dir;
    }
}
