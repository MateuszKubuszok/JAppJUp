package com.autoupdater.installer;

import static java.lang.System.*;
import static net.jsdpu.logger.Logger.getLogger;

import java.io.FileInputStream;
import java.io.IOException;

import net.jsdpu.logger.LogManager;
import net.jsdpu.logger.Logger;

import com.autoupdater.commons.error.codes.EErrorCode;

/**
 * Runs InstallationPerformer.
 * 
 * @see com.autoupdater.installer.InstallationPerformer
 */
public class Main {
    private static final Logger logger = getLogger(Main.class);

    /**
     * Pass arguments into InstallationPerformer. Returns result and displays
     * description.
     * 
     * @param args
     *            arguments
     */
    public static void main(String[] args) {
        setUpLogger();
        if (args.length > 0) {
            EErrorCode result = new InstallationPerformer().install(args);
            if (result == EErrorCode.SUCCESS) {
                logger.info("[info] " + args[0] + ": " + result);
                out.println("[info] " + args[0] + ": " + result);
            } else {
                logger.error("[error] " + args[0] + ": " + result);
                err.println("[error] " + args[0] + ": " + result);
            }
            logger.debug("Exit with: " + result + "(" + result.getCode() + ")");
            exit(result.getCode());
        }
    }

    /**
     * Sets up logger.
     */
    private static void setUpLogger() {
        try {
            FileInputStream configFile = new FileInputStream("./installer.logger.properties");
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (SecurityException | IOException e) {
        }
    }
}
