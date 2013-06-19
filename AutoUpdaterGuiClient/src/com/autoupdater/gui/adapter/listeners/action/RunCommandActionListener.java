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
package com.autoupdater.gui.adapter.listeners.action;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.ProgramBuilder;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.adapter.runnables.RunCommandRunnable;

public class RunCommandActionListener implements ActionListener {
    private final Gui2ClientAdapter adapter;
    private final ProgramSettings programSettings;
    private final Program program;

    public RunCommandActionListener(Gui2ClientAdapter adapter, ProgramSettings programSettings) {
        this.adapter = adapter;
        this.programSettings = programSettings;
        this.program = ProgramBuilder.builder().setName(programSettings.getProgramName())
                .setPathToProgramDirectory(programSettings.getPathToProgramDirectory())
                .setServerAddress(programSettings.getServerAddress())
                .setDevelopmentVersion(programSettings.isDevelopmentVersion()).build();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExecutorService executorService = newSingleThreadExecutor();
        executorService.submit(new RunCommandRunnable(adapter, programSettings, program));
    }
}