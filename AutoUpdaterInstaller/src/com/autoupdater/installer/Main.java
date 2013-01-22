package com.autoupdater.installer;

import com.autoupdater.commons.error.codes.EErrorCode;

/**
 * Runs InstallationPerformer.
 * 
 * @see com.autoupdater.installer.InstallationPerformer
 */
public class Main {
    /**
     * Pass arguments into InstallationPerformer. Returns result and displays
     * description.
     * 
     * @param args
     *            arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            EErrorCode result = new InstallationPerformer().install(args);
            if (result == EErrorCode.SUCCESS)
                System.out.println("[info] " + args[0] + ": " + result);
            else
                System.err.println("[error] " + args[0] + ": " + result);
            System.exit(result.getCode());
        }
    }
}
