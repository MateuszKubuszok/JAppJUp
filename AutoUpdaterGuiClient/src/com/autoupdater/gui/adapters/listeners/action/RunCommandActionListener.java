package com.autoupdater.gui.adapters.listeners.action;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.adapters.runnables.RunCommand;

public class RunCommandActionListener implements ActionListener {
    private final Gui2ClientAdapter adapter;
    private final ProgramSettings programSettings;
    private final Program program;

    public RunCommandActionListener(Gui2ClientAdapter adapter, ProgramSettings programSettings) {
        this.adapter = adapter;
        this.programSettings = programSettings;
        this.program = ProgramBuilder.builder().setName(programSettings.getProgramName())
                .setPathToProgramDirectory(programSettings.getPathToProgramDirectory())
                .setServerAddress(programSettings.getServerAddress()).build();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExecutorService executorService = newSingleThreadExecutor();
        executorService.submit(new RunCommand(adapter, programSettings, program));
    }
}