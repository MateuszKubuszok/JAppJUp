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
package com.autoupdater.gui.adapter.runnables;

import static com.autoupdater.gui.client.window.EInfoTarget.ALL;
import static javax.swing.JOptionPane.*;
import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.process.executors.Commands.convertSingleConsoleCommand;

import java.io.IOException;
import java.util.SortedSet;

import net.jsdpu.logger.Logger;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;

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
        setProgramLauncherEnabled(false);
        installIfPossibleAndRun();
        setProgramLauncherEnabled(true);
    }

    private void setProgramLauncherEnabled(boolean enabled) {
        logger.detailedTrace("Sets " + (enabled ? "enabled" : "disabled")
                + " program launcher for:\n" + program);
        adapter.windowOperations().setProgramLauncherEnabled(program, enabled);
    }

    private void installIfPossibleAndRun() {
        logger.info("Starting up program:\n" + program);

        if (!isUpdateDataChecked()) {
            if (askWhetherCancelRun(
                    "Starting up before update fetch completed - asking for decision",
                    "Updater hasn't yet fetched data from repositories. Would You like to run program without cheking updates?",
                    "Updates not fetched"))
                return;
        } else if (isAnyUpdateAvailable()) {
            if (isInstallationInProgress()) {
                if (askWhetherCancelRun(
                        "Some installation is currently in progress - asking for decision",
                        "Another update is currently in process. Do You wish to start up program without updating?",
                        "Another update in process"))
                    return;
            } else {
                logger.debug("Installing updates before running");

                installUpdates();

                if (hasInstallationSucceded()) {
                    logger.debug("Update installation succeded - continue running");
                } else {
                    if (askWhetherCancelRun(
                            "Update installation failed - asking for decision",
                            "Update installation failed. Do You wish to start up outdated program anyway?",
                            "Updates not installed"))
                        return;
                }
            }
        }

        logger.debug("Calling start up command: " + programSettings.getPathToProgram());
        try {
            adapter.windowOperations().reportInfo(programSettings.getProgramName(),
                    programSettings.getProgramName() + " is starting up", ALL);
            runProgram();
        } catch (IOException | InvalidCommandException e) {
            logger.error("Start up failed", e);
            adapter.windowOperations().reportWarning(e.toString(),
                    programSettings.getProgramName(), ALL);
        }
    }

    private boolean askWhetherCancelRun(String reason, String message, String title) {
        logger.debug(reason);
        int choice = showConfirmDialog(adapter.clientWindow(), message, title, YES_NO_OPTION);
        switch (choice) {
        default:
        case YES_OPTION:
            logger.debug("Continue running");
            return false;
        case NO_OPTION:
            logger.debug("Cancel running");
            return true;
        }
    }

    private boolean isUpdateDataChecked() {
        return adapter.dataStorage().isInitiated();
    }

    private boolean isAnyUpdateAvailable() {
        return !adapter.dataStorage().getUpdatesForProgram(program).isEmpty();
    }

    private boolean isInstallationInProgress() {
        return adapter.installationUtils().getCurrentInstallationThread() != null;
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
        for (Update update : updates)
            if (update.getStatus().isInstallationFailed())
                return false;
        return true;
    }

    private void runProgram() throws InvalidCommandException, IOException {
        new ProcessBuilder(convertSingleConsoleCommand(programSettings.getPathToProgram())).start();
    }
}