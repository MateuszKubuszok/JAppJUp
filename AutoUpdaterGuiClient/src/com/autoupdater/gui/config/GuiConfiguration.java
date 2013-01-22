package com.autoupdater.gui.config;

import java.awt.Dimension;
import java.awt.Rectangle;

public class GuiConfiguration {
    public static String WINDOW_TITLE = "AutoUpdater";

    public static Rectangle WINDOW_BOUNDS = new Rectangle(50, 50, 800, 500);
    public static Dimension WINDOW_MIN_SIZE = new Dimension(800, 500);

    public static String LOOK_AND_FEEL = "com.seaglasslookandfeel.SeaGlassLookAndFeel";

    public static String appIconURL = "gfx/AutoUpdater_AppIcon.png";
    public static String trayIconURL = "gfx/AutoUpdater_TrayIcon.png";
}
