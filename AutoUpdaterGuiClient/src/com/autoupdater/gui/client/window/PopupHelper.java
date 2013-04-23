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

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import javax.imageio.ImageIO;

import org.widgetfx.ui.JXTrayIcon;

import com.autoupdater.client.models.Program;
import com.autoupdater.gui.Resources;
import com.autoupdater.gui.config.GuiConfiguration;

public class PopupHelper {
    private final SortedSet<Program> programs;

    private final GuiClientWindow clientWindow;

    private TrayIcon trayIcon;
    private MenuItem showHideGUI;
    private MenuItem checkUpdates;
    private MenuItem installUpdates;
    private MenuItem cancelDownload;
    private MenuItem exitClient;
    private Map<Program, MenuItem> programsLaunchers;

    PopupHelper(GuiClientWindow clientWindow, SystemTray tray, SortedSet<Program> programs) {
        this.clientWindow = clientWindow;
        this.programs = programs;

        PopupMenu popup = new PopupMenu();
        addShowHideToPopup(popup);
        addProgramsToPopup(popup);
        addControlsToPopup(popup);
        addExitToPopup(popup);
        createTray(tray, popup);
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public MenuItem getShowHideGUI() {
        return showHideGUI;
    }

    public MenuItem getCheckUpdates() {
        return checkUpdates;
    }

    public MenuItem getInstallUpdates() {
        return installUpdates;
    }

    public MenuItem getCancelDownload() {
        return cancelDownload;
    }

    public MenuItem getExitClient() {
        return exitClient;
    }

    public Map<Program, MenuItem> getProgramsLaunchers() {
        return programsLaunchers;
    }

    private void addShowHideToPopup(PopupMenu popup) {
        showHideGUI = new MenuItem(clientWindow.isVisible() ? "Hide" : "Show");
        showHideGUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientWindow.setVisible(!clientWindow.isVisible());
            }
        });
        popup.add(showHideGUI);
    }

    private void addControlsToPopup(PopupMenu popup) {
        popup.addSeparator();
        checkUpdates = new MenuItem("Check updates");
        popup.add(checkUpdates);
        installUpdates = new MenuItem("Install all updates");
        popup.add(installUpdates);
        cancelDownload = new MenuItem("Cancel downloads");
        popup.add(cancelDownload);
    }

    private void addProgramsToPopup(PopupMenu popup) {
        programsLaunchers = new HashMap<Program, MenuItem>();

        if (programs == null || programs.isEmpty())
            return;

        popup.addSeparator();

        for (final Program program : programs) {
            MenuItem programLauncher = new MenuItem("Run " + program.getName());
            popup.add(programLauncher);
            programsLaunchers.put(program, programLauncher);
        }
    }

    private void addExitToPopup(PopupMenu popup) {
        popup.addSeparator();

        exitClient = new MenuItem("Exit");
        exitClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientWindow.getStatus() != EWindowStatus.INSTALLING_UPDATES)
                    System.exit(0);
            }
        });
        popup.add(exitClient);
    }

    private void createTray(SystemTray tray, PopupMenu popup) {
        try {
            trayIcon = new JXTrayIcon(ImageIO.read(Resources.class
                    .getResourceAsStream(GuiConfiguration.TRAY_ICON_URL)));
            trayIcon.setToolTip(GuiConfiguration.WINDOW_TITLE);
            trayIcon.setPopupMenu(popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if ((e.getButton() == MouseEvent.BUTTON1))
                        clientWindow.setVisible(!clientWindow.isVisible());
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            tray.add(trayIcon);
        } catch (IOException | AWTException e1) {
            e1.printStackTrace();
        }
    }
}
