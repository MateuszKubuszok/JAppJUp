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

import static com.autoupdater.commons.error.codes.EErrorCode.*;
import static com.autoupdater.commons.messages.EInstallerMessage.*;
import static java.lang.System.*;
import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.process.executors.Commands.convertSingleConsoleCommand;

import java.io.File;
import java.io.IOException;

import net.jsdpu.logger.Logger;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.commons.error.codes.EErrorCode;
import com.autoupdater.commons.messages.EInstallerMessage;
import com.autoupdater.installer.backup.BackupPerformer;
import com.autoupdater.installer.installation.strategies.CopyInstallationStrategy;
import com.autoupdater.installer.installation.strategies.ExecuteInstallationStrategy;
import com.autoupdater.installer.installation.strategies.IInstallationStrategy;
import com.autoupdater.installer.installation.strategies.UnzipInstallationStrategy;

/**
 * Class performing actual installation.
 */
public class InstallationPerformer {
    private static final Logger logger = getLogger(InstallationPerformer.class);

    /**
     * Runs installation by arguments passed from Main. Requires exactly 4
     * arguments:
     * <ul>
     * <li>ID by which update can be identified in output</li>
     * <li>strategy that should be used (copy | unzip)</li>
     * <li>source file</li>
     * <li>target file (copy)/directory (unzip)</li>
     * <li>post-installation command (optional)</li>
     * </ul>
     * 
     * @param args
     *            arguments passed by main
     * @return result of installation
     */
    public EErrorCode install(String[] args) {
        if (args.length > 5)
            return TOO_MANY_ARGUMENTS;
        else if (args.length < 4)
            return INVALID_ARGUMENT;

        String ID = args[0];
        String updateStrategy = args[1];
        String sourceFilePath = args[2];
        String destinationPath = args[3];
        String postInstallationCommand = args.length == 5 ? args[4] : "";

        info(ID, PREPARING_INSTALLATION);

        IInstallationStrategy updateInstallationStrategy;
        if ((updateInstallationStrategy = resolveExecutionDelegate(updateStrategy)) == null)
            return INVALID_ARGUMENT;

        info(ID, BACKUP_STARTED);
        if (new BackupPerformer().createBackup(ID, destinationPath) != SUCCESS) {
            error(ID, BACKUP_FAILED);
            return BACKUP_ERROR;
        }
        info(ID, BACKUP_FINISHED);

        info(ID, INSTALLATION_STARTED);

        File source = new File(sourceFilePath);
        if (!source.exists()) {
            error(ID, INSTALLATION_FAILED);
            return FILE_DONT_EXISTS;
        }

        try {
            updateInstallationStrategy.process(source, destinationPath);
        } catch (IOException e) {
            error(ID, INSTALLATION_FAILED);
            return IO_ERROR;
        } catch (InvalidCommandException e) {
            error(ID, INSTALLATION_FAILED);
            return INVALID_ARGUMENT;
        }

        if (!postInstallationCommand.isEmpty())
            try {
                info(ID, POST_INSTALLATION_COMMAND_EXECUTION);
                if (runPostInstallationCommand(postInstallationCommand) == SUCCESS.getCode())
                    info(ID, POST_INSTALLATION_COMMAND_EXECUTION_FINISHED);
                else {
                    error(ID, EInstallerMessage.POST_INSTALLATION_COMMAND_EXECUTION_FAILED);
                    return INTERRUPTED_SYSTEM_CALL;
                }
            } catch (InvalidCommandException | IOException e) {
                error(ID, POST_INSTALLATION_COMMAND_EXECUTION_FAILED);
                return INTERRUPTED_SYSTEM_CALL;
            }

        info(ID, INSTALLATION_FINISHED);
        return SUCCESS;
    }

    private int runPostInstallationCommand(String command) throws InvalidCommandException,
            IOException {
        try {
            return new ProcessBuilder(convertSingleConsoleCommand(command)).start().waitFor();
        } catch (InterruptedException e) {
            return -1;
        }
    }

    /**
     * Resolves update strategy into ExecutionDelegate.
     * 
     * @param updateStrategy
     *            update strategy name
     * @return executaionDelegate instance if resolved, null otherwise
     */
    private IInstallationStrategy resolveExecutionDelegate(String updateStrategy) {
        if ("copy".equalsIgnoreCase(updateStrategy))
            return new CopyInstallationStrategy();
        else if ("unzip".equalsIgnoreCase(updateStrategy))
            return new UnzipInstallationStrategy();
        else if ("execute".equalsIgnoreCase(updateStrategy))
            return new ExecuteInstallationStrategy();
        return null;
    }

    /**
     * Prints information about current state of installation.
     * 
     * @param id
     *            Update's ID
     * @param message
     *            message to print
     */
    private void info(String id, EInstallerMessage message) {
        logger.info("[info] " + id + ": " + message);
        out.println("[info] " + id + ": " + message);
    }

    /**
     * Prints information about error in installation.
     * 
     * @param id
     *            Update's ID
     * @param message
     *            message to print
     */
    private void error(String id, EInstallerMessage message) {
        logger.error("[error] " + id + ": " + message);
        err.println("[error] " + id + ": " + message);
    }
}
