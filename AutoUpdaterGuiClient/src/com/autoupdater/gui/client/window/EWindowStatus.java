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
package com.autoupdater.gui.client.window;

public enum EWindowStatus {
    UNINITIALIZED(true, true, false, false, true), //
    IDLE(true, true, true, false, true), //
    FETCHING_UPDATE_INFO(true, false, false, false, true), //
    FETCHING_UPDATES(false, false, false, true, true), //
    INSTALLING_UPDATES(false, false, false, false, false);

    private final boolean runCommandButtonsEnabled;
    private final boolean checkUpdatesButtonEnabled;
    private final boolean installUpdatesButtonEnabled;
    private final boolean cancelDownloadButtonEnabled;
    private final boolean programAbleToFinish;

    private EWindowStatus(boolean runCommandsButtonEnabled, boolean checkUpdatesButtonEnabled,
            boolean installUpdatesButtonEnabled, boolean cancelDownloadButtonEnabled,
            boolean programAbleToFinish) {
        this.runCommandButtonsEnabled = runCommandsButtonEnabled;
        this.checkUpdatesButtonEnabled = checkUpdatesButtonEnabled;
        this.installUpdatesButtonEnabled = installUpdatesButtonEnabled;
        this.cancelDownloadButtonEnabled = cancelDownloadButtonEnabled;
        this.programAbleToFinish = programAbleToFinish;
    }

    public boolean isRunCommandButtonsEnabled() {
        return runCommandButtonsEnabled;
    }

    public boolean isCheckUpdatesButtonEnabled() {
        return checkUpdatesButtonEnabled;
    }

    public boolean isInstallUpdatesButtonEnabled() {
        return installUpdatesButtonEnabled;
    }

    public boolean isCancelDownloadButtonEnabled() {
        return cancelDownloadButtonEnabled;
    }

    public boolean isProgramAbleToFinish() {
        return programAbleToFinish;
    }
}