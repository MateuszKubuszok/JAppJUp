package com.autoupdater.gui.window;

import java.awt.SystemTray;

import javax.swing.JFrame;

import com.autoupdater.client.utils.enums.Enums;

public enum ETrayStrategy {
    TRAY_DISABLED(false), //
    TRAY_ENABLED(true);

    private static ETrayStrategy currentTrayStrategy;

    private final boolean trayEnabled;

    private ETrayStrategy(boolean trayEnabled) {
        this.trayEnabled = trayEnabled;
    }

    public boolean isTrayEnabled() {
        return trayEnabled;
    }

    public void initializeTrayIfPossible(GuiClientWindow clientWindow) {
        if (isTrayEnabled()) {
            clientWindow.setSystemTray(SystemTray.getSystemTray());
        }
    }

    public void configureWindowBehaviour(GuiClientWindow clientWindow, EWindowStatus status) {
        if (isTrayEnabled()) {
            clientWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            clientWindow.setExitEnabled(status.isProgramAbleToFinish());
        } else {
            if (status.isProgramAbleToFinish()) {
                clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } else {
                clientWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        }
    }

    public static ETrayStrategy resolve() {
        try {
            return (currentTrayStrategy != null) ? currentTrayStrategy
                    : (currentTrayStrategy = Enums.parseField(ETrayStrategy.class, "trayEnabled",
                            SystemTray.isSupported()));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
