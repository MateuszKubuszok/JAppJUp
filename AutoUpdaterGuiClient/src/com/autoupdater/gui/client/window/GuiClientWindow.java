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

import static com.autoupdater.gui.client.tray.ETrayStrategy.resolve;
import static com.autoupdater.gui.client.window.EWindowStatus.UNINITIALIZED;
import static com.autoupdater.gui.config.GuiConfiguration.*;
import static java.lang.Double.MIN_VALUE;
import static javax.swing.JOptionPane.*;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SplashScreen;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.Resources;
import com.autoupdater.gui.client.tray.TrayHelper;
import com.autoupdater.gui.client.window.tabs.installed.ProgramTabContentContainer;
import com.autoupdater.gui.client.window.tabs.settings.SettingsTabContentContainer;
import com.autoupdater.gui.client.window.tabs.updates.UpdateInformationPanel;
import com.autoupdater.gui.client.window.tabs.updates.UpdatesTabContentContainer;
import com.autoupdater.gui.mocks.MockModels;
import com.google.common.base.Optional;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GuiClientWindow extends JFrame {
    private EWindowStatus state;

    private final EnvironmentData environmantData;

    private final JPanel contentPane;

    private UpdatesTabContentContainer updatesTab;
    private SettingsTabContentContainer settingsTab;
    private final List<ProgramTabContentContainer> programsTabs;
    private JButton checkUpdatesButton;
    private JButton installUpdatesButton;
    private JButton cancelDownloadButton;
    private JLabel statusLabel;
    private JProgressBar progressBar;

    private Optional<TrayHelper> optionalTrayHelper;

    public GuiClientWindow() {
        this(MockModels.getEnvironmentData());
    }

    public GuiClientWindow(EnvironmentData environmentData) {
        this.environmantData = environmentData;

        this.contentPane = new JPanel();
        this.programsTabs = new ArrayList<ProgramTabContentContainer>();

        this.optionalTrayHelper = Optional.absent();

        initialize();
    }

    public void setSettings(EnvironmentData environmentData) {
        settingsTab.setEnvironmentData(environmentData);
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    public void reportInfo(String title, String message, EInfoTarget target) {
        if (target.shouldUpdateStatusBar())
            setStatusMessage(message);
        if (target.shouldUpdateToolTip()) {
            if (optionalTrayHelper.isPresent())
                optionalTrayHelper.get().getTrayIcon()
                        .displayMessage(title, message, TrayIcon.MessageType.INFO);
            else
                showMessageDialog(this, message, title, INFORMATION_MESSAGE);
        }
    }

    public void reportWarning(String title, String message, EInfoTarget target) {
        if (target.shouldUpdateStatusBar())
            setStatusMessage(message);
        if (target.shouldUpdateToolTip()) {
            if (optionalTrayHelper.isPresent())
                optionalTrayHelper.get().getTrayIcon()
                        .displayMessage(title, message, TrayIcon.MessageType.WARNING);
            else
                showMessageDialog(this, message, title, WARNING_MESSAGE);
        }
    }

    public void reportError(String title, String message, EInfoTarget target) {
        if (target.shouldUpdateStatusBar())
            setStatusMessage(message);
        if (target.shouldUpdateToolTip()) {
            if (optionalTrayHelper.isPresent())
                optionalTrayHelper.get().getTrayIcon()
                        .displayMessage(title, message, TrayIcon.MessageType.ERROR);
            else
                showMessageDialog(this, message, "Error occured", ERROR_MESSAGE);
        }
    }

    public void refresh() {
        updatesTab.refresh();
        if (optionalTrayHelper.isPresent())
            optionalTrayHelper.get().refreshIcons();
        for (ProgramTabContentContainer programTab : programsTabs)
            programTab.refresh();
    }

    public UpdateInformationPanel getUpdateInformationPanel(Update update) {
        return updatesTab.getUpdateInformationPanel(update);
    }

    public void setExitEnabled(boolean exitEnabled) {
        if (optionalTrayHelper.isPresent())
            optionalTrayHelper.get().getExitClient().setEnabled(exitEnabled);
    }

    public void setProgressBarInactive() {
        progressBar.setIndeterminate(false);
        progressBar.setEnabled(false);
        progressBar.setStringPainted(false);
    }

    public void setProgressBarIndetermined() {
        progressBar.setEnabled(true);
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(false);
    }

    public void setProgressBar(int numberOfUpdatesBeingInstalled, int numberOfUpdatesMarkedAsDone) {
        progressBar.setEnabled(true);
        progressBar.setIndeterminate(false);
        progressBar.setMinimum(0);
        progressBar.setMaximum(numberOfUpdatesBeingInstalled);
        progressBar.setValue(numberOfUpdatesMarkedAsDone);
        progressBar.setString("Installed " + numberOfUpdatesMarkedAsDone + "/"
                + numberOfUpdatesBeingInstalled);
        progressBar.setStringPainted(true);
    }

    public EWindowStatus getStatus() {
        return state;
    }

    public void setStatus(EWindowStatus state) {
        this.state = state;
        checkUpdatesButton.setEnabled(state.isCheckUpdatesButtonEnabled());
        installUpdatesButton.setEnabled(state.isInstallUpdatesButtonEnabled());
        cancelDownloadButton.setEnabled(state.isCancelDownloadButtonEnabled());
        if (optionalTrayHelper.isPresent()) {
            optionalTrayHelper.get().getCheckUpdates()
                    .setEnabled(state.isCheckUpdatesButtonEnabled());
            optionalTrayHelper.get().getInstallUpdates()
                    .setEnabled(state.isInstallUpdatesButtonEnabled());
            optionalTrayHelper.get().getCancelDownload()
                    .setEnabled(state.isCancelDownloadButtonEnabled());
        }
        resolve().configureWindowBehaviour(this, state);
    }

    public void setSystemTray(SystemTray tray) {
        TrayHelper helper = new TrayHelper(this, tray, environmantData.getInstallationsData());
        if (helper.isInitiatedCorrectly())
            optionalTrayHelper = Optional.of(helper);
    }

    public void showMessage(String title, String message, TrayIcon.MessageType type) {
        if (optionalTrayHelper.isPresent())
            optionalTrayHelper.get().getTrayIcon().displayMessage(title, message, type);
    }

    public void bindCheckUpdatesButton(MouseListener mouseListener, ActionListener actionListener) {
        checkUpdatesButton.addMouseListener(mouseListener);
        if (optionalTrayHelper.isPresent())
            optionalTrayHelper.get().getCheckUpdates().addActionListener(actionListener);
    }

    public void bindInstallUpdatesButton(MouseListener mouseListener, ActionListener actionListener) {
        installUpdatesButton.addMouseListener(mouseListener);
        if (optionalTrayHelper.isPresent())
            optionalTrayHelper.get().getInstallUpdates().addActionListener(actionListener);
    }

    public void bindCancelDownloadButton(MouseListener mouseListener, ActionListener actionListener) {
        cancelDownloadButton.addMouseListener(mouseListener);
        if (optionalTrayHelper.isPresent())
            optionalTrayHelper.get().getCancelDownload().addActionListener(actionListener);
    }

    public void bindProgramLauncher(Program program, ActionListener listener) {
        if (optionalTrayHelper.isPresent())
            optionalTrayHelper.get().getProgramsLaunchers().get(program)
                    .addActionListener(listener);
    }

    public void setProgramLauncherEnabled(final Program program, final boolean enabled) {
        if (optionalTrayHelper.isPresent()
                && optionalTrayHelper.get().getProgramsLaunchers().containsKey(program))
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        if (optionalTrayHelper.isPresent())
                            optionalTrayHelper.get().getProgramsLaunchers().get(program)
                                    .setEnabled(enabled);
                    }
                });
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void initialize() {
        try {
            setIconImage(ImageIO.read(Resources.class.getResourceAsStream(APP_ICON_URL)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeWindow();
        initializeTabs();
        initializeControlPanel();

        resolve().initializeTrayIfPossible(this);
        setStatus(UNINITIALIZED);
        setProgressBarInactive();

        hideSplashScreen();
    }

    private void initializeWindow() {
        try {
            setLookAndFeel(LOOK_AND_FEEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setBounds(WINDOW_BOUNDS);
        setMinimumSize(WINDOW_MIN_SIZE);
        setTitle(WINDOW_TITLE);

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[] { 0, 0 };
        gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
        gbl_contentPane.columnWeights = new double[] { 1.0, MIN_VALUE };
        gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, MIN_VALUE };
        contentPane.setLayout(gbl_contentPane);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
                if (optionalTrayHelper.isPresent()) {
                    optionalTrayHelper.get().getShowHideGUI().setText("Hide");
                    setVisible(true);
                }
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                if (optionalTrayHelper.isPresent())
                    optionalTrayHelper.get().getShowHideGUI().setText("Show");
            }
        });
    }

    private void initializeTabs() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
        gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
        gbc_tabbedPane.fill = GridBagConstraints.BOTH;
        gbc_tabbedPane.gridx = 0;
        gbc_tabbedPane.gridy = 0;
        contentPane.add(tabbedPane, gbc_tabbedPane);

        updatesTab = new UpdatesTabContentContainer(environmantData.getInstallationsData());
        tabbedPane.addTab("Updates", new JScrollPane(updatesTab));

        settingsTab = new SettingsTabContentContainer(environmantData);
        tabbedPane.add("Settings", settingsTab);

        for (Program program : environmantData.getInstallationsData()) {
            ProgramTabContentContainer programTab = new ProgramTabContentContainer(program);
            tabbedPane.add(program.getName(), programTab);
            programsTabs.add(programTab);
        }
    }

    private void initializeControlPanel() {
        JPanel controlPanel = new JPanel();
        GridBagConstraints gbc_controlPanel = new GridBagConstraints();
        gbc_controlPanel.fill = GridBagConstraints.BOTH;
        gbc_controlPanel.gridx = 0;
        gbc_controlPanel.gridy = 1;
        contentPane.add(controlPanel, gbc_controlPanel);
        controlPanel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"),
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

        checkUpdatesButton = new JButton("Check updates");
        controlPanel.add(checkUpdatesButton, "3, 2, left, fill");

        installUpdatesButton = new JButton("Install all updates");
        controlPanel.add(installUpdatesButton, "5, 2, fill, fill");

        cancelDownloadButton = new JButton("Cancel downloads");
        controlPanel.add(cancelDownloadButton, "7, 2, fill, fill");

        statusLabel = new JLabel("Welcome to AutoUpdater\r\n");
        controlPanel.add(statusLabel, "1, 4, 7, 1");

        progressBar = new JProgressBar();
        controlPanel.add(progressBar, "1, 6, 7, 1, fill, fill");
    }

    private void hideSplashScreen() {
        SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null)
            splash.close();
    }
}
