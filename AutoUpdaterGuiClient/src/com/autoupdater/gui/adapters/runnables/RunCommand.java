package com.autoupdater.gui.adapters.runnables;

import static com.autoupdater.gui.window.EInfoTarget.ALL;
import static net.jsdpu.process.executors.Commands.convertMultipleConsoleCommands;

import java.io.IOException;
import java.util.SortedSet;

import net.jsdpu.EOperatingSystem;
import net.jsdpu.process.executors.InvalidCommandException;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;

public class RunCommand implements Runnable {
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
        if (isAnyUpdateAvailable()) {
            installUpdates();

            // check whether update succeeded
        }

        try {
            adapter.windowOperations().reportInfo(programSettings.getProgramName(),
                    programSettings.getProgramName() + " is starting up", ALL);
            EOperatingSystem.current().getProcessExecutor()
                    .execute(convertMultipleConsoleCommands(programSettings.getPathToProgram()))
                    .rewind();
        } catch (IOException | InvalidCommandException e) {
            adapter.windowOperations().reportWarning(e.toString(),
                    programSettings.getProgramName(), ALL);
        }
    }

    private boolean isAnyUpdateAvailable() {
        return !adapter.dataStorage().getUpdatesForProgram(program).isEmpty();
    }

    private void installUpdates() {
        adapter.installationUtils().installUpdatesForProgram(program);
    }

    private boolean hasInstallationSucceded() {
        SortedSet<Update> updates = adapter.dataStorage().getUpdatesForProgram(program);
        return true;
    }
}