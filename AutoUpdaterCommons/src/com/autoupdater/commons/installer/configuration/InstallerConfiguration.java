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
    public static String backupDirectory;
    static {
        String dir = System.getProperty("java.io.tmpdir");
        if (dir.endsWith("\\") || dir.endsWith("/"))
            dir = dir.substring(0, dir.length() - 1);
        dir += File.separator + "AutoUpdater" + File.separator + "Backup";
        backupDirectory = dir;
    }
}
