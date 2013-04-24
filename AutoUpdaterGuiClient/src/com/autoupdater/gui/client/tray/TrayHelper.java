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

import static com.autoupdater.gui.client.window.tabs.updates.UpdateInformationPanel.*;

import java.awt.AWTException;
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
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.widgetfx.ui.JXTrayIcon;

import com.autoupdater.client.models.Program;
import com.autoupdater.gui.Resources;
import com.autoupdater.gui.client.window.EWindowStatus;
import com.autoupdater.gui.client.window.GuiClientWindow;
import com.autoupdater.gui.config.GuiConfiguration;

public class TrayHelper {
    private final SortedSet<Program> programs;

    private final GuiClientWindow clientWindow;

    private JXTrayIcon trayIcon;
    private JMenuItem showHideGUI;
    private JMenuItem checkUpdates;
    private JMenuItem installUpdates;
    private JMenuItem cancelDownload;
    private JMenuItem exitClient;
    private Map<Program, JMenuItem> programsLaunchers;

    public TrayHelper(GuiClientWindow clientWindow, SystemTray tray, SortedSet<Program> programs) {
        this.clientWindow = clientWindow;
        this.programs = programs;

        JPopupMenu popup = new JPopupMenu();
        addShowHideToPopup(popup);
        addProgramsToPopup(popup);
        addControlsToPopup(popup);
        addExitToPopup(popup);
        createTray(tray, popup);
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public JMenuItem getShowHideGUI() {
        return showHideGUI;
    }

    public JMenuItem getCheckUpdates() {
        return checkUpdates;
    }

    public JMenuItem getInstallUpdates() {
        return installUpdates;
    }

    public JMenuItem getCancelDownload() {
        return cancelDownload;
    }

    public JMenuItem getExitClient() {
        return exitClient;
    }

    public Map<Program, JMenuItem> getProgramsLaunchers() {
        return programsLaunchers;
    }

    public void refreshIcons() {
        for (Program program : programsLaunchers.keySet())
            setProgramIcon(program, programsLaunchers.get(program));
    }

    private void addShowHideToPopup(JPopupMenu popup) {
        showHideGUI = new JMenuItem(clientWindow.isVisible() ? "Hide" : "Show");
        showHideGUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientWindow.setVisible(!clientWindow.isVisible());
            }
        });
        popup.add(showHideGUI);
    }

    private void addControlsToPopup(JPopupMenu popup) {
        popup.addSeparator();
        checkUpdates = new JMenuItem("Check updates");
        popup.add(checkUpdates);
        installUpdates = new JMenuItem("Install all updates");
        popup.add(installUpdates);
        cancelDownload = new JMenuItem("Cancel downloads");
        popup.add(cancelDownload);
    }

    private void addProgramsToPopup(JPopupMenu popup) {
        programsLaunchers = new HashMap<Program, JMenuItem>();

        if (programs == null || programs.isEmpty())
            return;

        popup.addSeparator();

        for (final Program program : programs) {
            JMenuItem programLauncher = new JMenuItem("Run " + program.getName());
            setProgramIcon(program, programLauncher);
            popup.add(programLauncher);
            programsLaunchers.put(program, programLauncher);
        }
    }

    private void addExitToPopup(JPopupMenu popup) {
        popup.addSeparator();

        exitClient = new JMenuItem("Exit");
        exitClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientWindow.getStatus() != EWindowStatus.INSTALLING_UPDATES)
                    System.exit(0);
            }
        });
        popup.add(exitClient);
    }

    private void setProgramIcon(Program program, JMenuItem menuItem) {
        if (program.isNotOutdated())
            menuItem.setIcon(new ImageIcon(UP_TO_DATE_IMAGE));
        else
            menuItem.setIcon(new ImageIcon(OUT_OF_DATE_IMAGE));
    }

    private void createTray(SystemTray tray, JPopupMenu popup) {
        try {
            trayIcon = new JXTrayIcon(ImageIO.read(Resources.class
                    .getResourceAsStream(GuiConfiguration.TRAY_ICON_URL)));
            trayIcon.setToolTip(GuiConfiguration.WINDOW_TITLE);
            trayIcon.setJPopupMenu(popup);
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
