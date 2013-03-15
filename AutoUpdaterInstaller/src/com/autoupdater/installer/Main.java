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
        FileInputStream configFile = null;
        try {
            configFile = new FileInputStream("./installer.logger.properties");
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (SecurityException | IOException e) {
        } finally {
            if (configFile != null)
                try {
                    configFile.close();
                } catch (IOException e) {
                }
        }
    }
}
