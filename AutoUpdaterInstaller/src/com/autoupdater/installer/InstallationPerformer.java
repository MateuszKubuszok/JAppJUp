package com.autoupdater.installer;

import java.io.File;
import java.io.IOException;

import net.jsdpu.process.executors.Commands;
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
     * @return result of installation
     */
    public EErrorCode install(String[] args) {
        if (args.length > 5)
            return EErrorCode.TOO_MANY_ARGUMENTS;
        else if (args.length < 4)
            return EErrorCode.INVALID_ARGUMENT;

        String ID = args[0];
        String updateStrategy = args[1];
        String sourceFilePath = args[2];
        String destinationPath = args[3];
        String postInstallationCommand = args.length == 5 ? args[4] : "";

        info(ID, EInstallerMessage.PREPARING_INSTALLATION);

        IInstallationStrategy updateInstallationStrategy;
        if ((updateInstallationStrategy = resolveExecutionDelegate(updateStrategy)) == null)
            return EErrorCode.INVALID_ARGUMENT;

        info(ID, EInstallerMessage.BACKUP_STARTED);
        if (new BackupPerformer().createBackup(ID, destinationPath) != EErrorCode.SUCCESS) {
            error(ID, EInstallerMessage.BACKUP_FAILED);
            return EErrorCode.BACKUP_ERROR;
        }
        info(ID, EInstallerMessage.BACKUP_FINISHED);

        info(ID, EInstallerMessage.INSTALLATION_STARTED);

        File source = new File(sourceFilePath);
        if (!source.exists()) {
            error(ID, EInstallerMessage.INSTALLATION_FAILED);
            return EErrorCode.FILE_DONT_EXISTS;
        }

        try {
            updateInstallationStrategy.process(source, destinationPath);
        } catch (IOException e) {
            error(ID, EInstallerMessage.INSTALLATION_FAILED);
            return EErrorCode.IO_ERROR;
        } catch (InvalidCommandException e) {
            error(ID, EInstallerMessage.INSTALLATION_FAILED);
            return EErrorCode.INVALID_ARGUMENT;
        }

        if (!postInstallationCommand.isEmpty())
            try {
                info(ID, EInstallerMessage.POST_INSTALLATION_COMMAND_EXECUTION);
                if (runPostInstallationCommand(postInstallationCommand) == EErrorCode.SUCCESS
                        .getCode())
                    info(ID, EInstallerMessage.POST_INSTALLATION_COMMAND_EXECUTION_FINISHED);
                else {
                    error(ID, EInstallerMessage.POST_INSTALLATION_COMMAND_EXECUTION_FAILED);
                    return EErrorCode.INTERRUPTED_SYSTEM_CALL;
                }
            } catch (InvalidCommandException | IOException e) {
                error(ID, EInstallerMessage.POST_INSTALLATION_COMMAND_EXECUTION_FAILED);
                return EErrorCode.INTERRUPTED_SYSTEM_CALL;
            }

        info(ID, EInstallerMessage.INSTALLATION_FINISHED);
        return EErrorCode.SUCCESS;
    }

    private int runPostInstallationCommand(String command) throws InvalidCommandException,
            IOException {
        try {
            return new ProcessBuilder(Commands.convertSingleConsoleCommand(command)).start()
                    .waitFor();
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
        System.out.println("[info] " + id + ": " + message);
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
        System.err.println("[error] " + id + ": " + message);
    }
}
