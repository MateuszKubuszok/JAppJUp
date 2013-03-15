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
package com.autoupdater.gui.adapter.helpers;

import static java.awt.EventQueue.invokeLater;

import com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.adapter.listeners.action.RunCommandActionListener;
import com.autoupdater.gui.adapter.listeners.triggers.CancelDownloadsTriggerListener;
import com.autoupdater.gui.adapter.listeners.triggers.CheckUpdateTriggerListener;
import com.autoupdater.gui.adapter.listeners.triggers.InstallUpdateTriggerListener;
import com.autoupdater.gui.client.window.EInfoTarget;
import com.autoupdater.gui.client.window.EWindowStatus;
import com.autoupdater.gui.client.window.GuiClientWindow;
import com.autoupdater.gui.client.window.tabs.updates.UpdateInformationPanel;

public class WindowOperations {
    private final Gui2ClientAdapter adapter;

    public WindowOperations(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
    }

    public WindowOperations configureClientWindow(GuiClientWindow clientWindow) {
        clientWindow.setSettings(adapter.environmentData());

        clientWindow.bindCheckUpdatesButton(new CheckUpdateTriggerListener(adapter),
                new CheckUpdateTriggerListener(adapter));
        clientWindow.bindInstallUpdatesButton(new InstallUpdateTriggerListener(adapter),
                new InstallUpdateTriggerListener(adapter));
        clientWindow.bindCancelDownloadButton(new CancelDownloadsTriggerListener(adapter),
                new CancelDownloadsTriggerListener(adapter));

        for (final Program program : adapter.dataStorage().getProgramsThatShouldBeDisplayed()) {
            ProgramSettings programSettings = program.findProgramSettings(adapter.client()
                    .getProgramsSettings());
            if (programSettings != null)
                clientWindow.bindProgramLauncher(program, new RunCommandActionListener(adapter,
                        programSettings));
        }
        return this;
    }

    public UpdateInformationPanel getUpdateInformationPanel(Update update) {
        return adapter.clientWindow().getUpdateInformationPanel(update);
    }

    public WindowOperations setInstallationInactive() {
        adapter.clientWindow().setProgressBarInactive();
        return this;
    }

    public WindowOperations setInstallationIndetermined() {
        adapter.clientWindow().setProgressBarIndetermined();
        return this;
    }

    public WindowOperations setInstallationProgress(int numberOfUpdatesBeingInstalled,
            int numberOfUpdatesMarkedAsDone) {
        if (adapter.clientWindow() != null)
            adapter.clientWindow().setProgressBar(numberOfUpdatesBeingInstalled,
                    numberOfUpdatesMarkedAsDone);
        return this;
    }

    public WindowOperations setProgramLauncherEnabled(Program program, boolean enabled) {
        adapter.clientWindow().setProgramLauncherEnabled(program, enabled);
        return this;
    }

    public WindowOperations setState(EWindowStatus state) {
        adapter.clientWindow().setStatus(state);
        return this;
    }

    public WindowOperations setStatusMessage(String message) {
        adapter.clientWindow().setStatusMessage(message);
        return this;
    }

    public WindowOperations refreshGUI() {
        invokeLater(new RefreshGUI());
        return this;
    }

    public WindowOperations reportQuiet(String message) {
        adapter.clientWindow().setStatusMessage(message);
        return this;
    }

    public WindowOperations reportInfo(String title, String message, EInfoTarget target) {
        adapter.clientWindow().reportInfo(title, message, target);
        return this;
    }

    public WindowOperations reportWarning(String title, String message, EInfoTarget target) {
        adapter.clientWindow().reportWarning(title, message, target);
        return this;
    }

    public WindowOperations reportError(String title, String message, EInfoTarget target) {
        adapter.clientWindow().reportError(title, message, target);
        return this;
    }

    public WindowOperations bindDownloadServicesToUpdateInformationPanels(
            FileAggregatedDownloadService aggregatedService) {
        for (FileDownloadService downloadService : aggregatedService.getServices()) {
            Update update = aggregatedService.getAdditionalMessage(downloadService);
            if (update != null && getUpdateInformationPanel(update) != null)
                getUpdateInformationPanel(update).setDownloadService(downloadService);
        }
        return this;
    }

    private class RefreshGUI implements Runnable {
        @Override
        public void run() {
            adapter.clientWindow().refresh();
        }
    }
}
