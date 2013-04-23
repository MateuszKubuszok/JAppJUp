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
package com.autoupdater.gui.config;

import java.awt.Dimension;
import java.awt.Rectangle;

public class GuiConfiguration {
    public static String WINDOW_TITLE = "Manage AutoUpdater";

    public static Rectangle WINDOW_BOUNDS = new Rectangle(50, 50, 800, 500);
    public static Dimension WINDOW_MIN_SIZE = new Dimension(800, 500);

    public static String LOOK_AND_FEEL = "com.seaglasslookandfeel.SeaGlassLookAndFeel";

    public static String APP_ICON_URL = "gfx/AutoUpdater_AppIcon.png";
    public static String TRAY_ICON_URL = "gfx/AutoUpdater_TrayIcon.png";

    public static int ICON_SIZE = 15;
    public static String OUT_OF_DATE_ICON_URL = "gfx/AutoUpdater_OutOfDate.png";
    public static String UP_TO_DATE_ICON_URL = "gfx/AutoUpdater_UpToDate.png";
}
