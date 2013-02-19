package com.autoupdater.gui.adapters.runnables;

import static com.autoupdater.gui.window.EInfoTarget.ALL;
import static javax.swing.JOptionPane.*;
import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.process.executors.Commands.convertMultipleConsoleCommands;

import java.io.IOException;
import java.util.SortedSet;

import net.jsdpu.EOperatingSystem;
import net.jsdpu.logger.Logger;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;

public class RunCommand implements Runnable {
    private static final Logger logger = getLogger(RunCommand.class);

    private final Gui2ClientAdapter adapter;

    private final ProgramSettings programSettings;
    private final Program program;

    public RunCommand(Gui2ClientAdapter adapter, ProgramSettings programSettings, Program program) {
        this.adapter = adapter;
        this.programSettings = programSettings;
        this.program = program;
    }

    @Override
    public void run() {
        logger.info("Starting up program: " + program);

        if (!isUpdateDataChecked()) {
            logger.debug("Starting up before update fetch completed - asking for decision");
            int choice = showConfirmDialog(
                    adapter.clientWindow(),
                    "Updater hasn't yet fetched data from repositories. Would You like to run program without cheking updates?",
                    "Updates not fetched", YES_NO_OPTION);
            switch (choice) {
            default:
            case YES_OPTION:
                logger.debug("Continue running");
                break;
            case NO_OPTION:
                logger.debug("Cancel running");
                return;
            }
        } else if (isAnyUpdateAvailable()) {
            logger.debug("Installing updates before running");

            installUpdates();

            if (hasInstallationSucceded()) {
                logger.debug("Update installation succeded - continue running");
            } else {
                logger.debug("Update installation failed - asking for decision");
                int choice = showConfirmDialog(
                        adapter.clientWindow(),
                        "Update installation failed. Do You wish to start up outdated program anyway?",
                        "Updates not installed", YES_NO_OPTION);
                switch (choice) {
                default:
                case YES_OPTION:
                    logger.debug("Continue running");
                    break;
                case NO_OPTION:
                    logger.debug("Cancel running");
                    return;
                }
            }
        }

        logger.debug("Calling start up command: " + programSettings.getPathToProgram());
        try {
            adapter.windowOperations().reportInfo(programSettings.getProgramName(),
                    programSettings.getProgramName() + " is starting up", ALL);
            EOperatingSystem.current().getProcessExecutor()
                    .execute(convertMultipleConsoleCommands(programSettings.getPathToProgram()))
                    .rewind();
        } catch (IOException | InvalidCommandException e) {
            logger.error("Start up failed", e);
            adapter.windowOperations().reportWarning(e.toString(),
                    programSettings.getProgramName(), ALL);
        }
    }

    private boolean isUpdateDataChecked() {
        return adapter.dataStorage().isInitiated();
    }

    private boolean isAnyUpdateAvailable() {
        return !adapter.dataStorage().getUpdatesForProgram(program).isEmpty();
    }

    private void installUpdates() {
        adapter.installationUtils().installUpdatesForProgram(program);
        try {
            adapter.installationUtils().getCurrentInstallationThread().join();
        } catch (InterruptedException e) {
            logger.warning("Update thread interrupter", e);
        }
    }

    private boolean hasInstallationSucceded() {
        SortedSet<Update> updates = adapter.dataStorage().getUpdatesForProgram(program);
        for (Update update : updates) {
            if (update.getStatus().isInstallationFailed())
                return false;
        }
        return true;
    }
}