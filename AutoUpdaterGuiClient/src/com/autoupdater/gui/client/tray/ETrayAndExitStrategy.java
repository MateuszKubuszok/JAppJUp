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
package com.autoupdater.gui.client.tray;

import static java.awt.SystemTray.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.*;

import com.autoupdater.gui.client.window.EWindowStatus;
import com.autoupdater.gui.client.window.GuiClientWindow;

public enum ETrayAndExitStrategy {
    TRAY_DISABLED(false), //
    TRAY_ENABLED(true);

    private static ETrayAndExitStrategy currentTrayStrategy;

    private final boolean trayEnabled;

    private ETrayAndExitStrategy(boolean trayEnabled) {
        this.trayEnabled = trayEnabled;
    }

    public boolean isTrayEnabled() {
        return trayEnabled;
    }

    public void initializeTrayIfPossible(GuiClientWindow clientWindow) {
        if (isTrayEnabled())
            clientWindow.setSystemTray(getSystemTray());
    }

    public void configureWindowBehaviour(GuiClientWindow clientWindow, EWindowStatus status) {
        if (isTrayEnabled())
            clientWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
        else
            clientWindow.setDefaultCloseOperation(status.isProgramAbleToFinish() ? EXIT_ON_CLOSE
                    : DO_NOTHING_ON_CLOSE);
    }

    public static ETrayAndExitStrategy resolve() {
        return (currentTrayStrategy != null) ? currentTrayStrategy
                : (currentTrayStrategy = isSupported() ? TRAY_ENABLED : TRAY_DISABLED);
    }
}
