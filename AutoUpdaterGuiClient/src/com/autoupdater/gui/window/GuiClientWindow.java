package com.autoupdater.gui.window;

import static com.autoupdater.gui.mocks.MockModels.getInstalledPrograms;
import static com.autoupdater.gui.window.ETrayStrategy.resolve;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.gui.Resources;
import com.autoupdater.gui.config.GuiConfiguration;
import com.autoupdater.gui.tabs.installed.ProgramTabContentContainer;
import com.autoupdater.gui.tabs.settings.SettingsTabContentContainer;
import com.autoupdater.gui.tabs.updates.UpdateInformationPanel;
import com.autoupdater.gui.tabs.updates.UpdatesTabContentContainer;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GuiClientWindow extends JFrame {
    private EWindowStatus state;

    private final SortedSet<Program> programs;

    private final JPanel contentPane;

    private UpdatesTabContentContainer updatesTab;
    private SettingsTabContentContainer settingsTab;
    private final List<ProgramTabContentContainer> programsTabs;
    private JButton checkUpdatesButton;
    private JButton installUpdatesButton;
    private JButton cancelDownloadButton;
    private JLabel statusLabel;
    private JProgressBar progressBar;

    private TrayIcon trayIcon;
    private MenuItem showHideGUI;
    private MenuItem checkUpdates;
    private MenuItem installUpdates;
    private MenuItem cancelDownload;
    private MenuItem exitClient;
    private Map<Program, MenuItem> programsLaunchers;

    public GuiClientWindow() {
        contentPane = new JPanel();
        programsTabs = new ArrayList<ProgramTabContentContainer>();

        this.programs = getInstalledPrograms();

        initialize();
    }

    public GuiClientWindow(SortedSet<Program> programs) {
        contentPane = new JPanel();
        programsTabs = new ArrayList<ProgramTabContentContainer>();

        this.programs = programs;

        initialize();
    }

    public void setSettings(EnvironmentData environmentData) {
        settingsTab.setEnvironmentData(environmentData);
    }

    public void reportInfo(String title, String message) {
        if (trayIcon != null)
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        else
            showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void reportWarning(String title, String message) {
        if (trayIcon != null)
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.WARNING);
        else
            showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public void reportError(String title, String message) {
        if (trayIcon != null)
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.ERROR);
        else
            showMessageDialog(this, message, "Error occured", JOptionPane.ERROR_MESSAGE);
    }

    public void refresh() {
        updatesTab.refresh();
        for (ProgramTabContentContainer programTab : programsTabs)
            programTab.refresh();
    }

    public UpdateInformationPanel getUpdateInformationPanel(Update update) {
        return updatesTab.getUpdateInformationPanel(update);
    }

    public void setExitEnabled(boolean exitEnabled) {
        if (exitClient != null)
            exitClient.setEnabled(exitEnabled);
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
        if (checkUpdates != null)
            checkUpdates.setEnabled(state.isCheckUpdatesButtonEnabled());
        installUpdatesButton.setEnabled(state.isInstallUpdatesButtonEnabled());
        if (installUpdates != null)
            installUpdates.setEnabled(state.isInstallUpdatesButtonEnabled());
        cancelDownloadButton.setEnabled(state.isCancelDownloadButtonEnabled());
        if (cancelDownload != null)
            cancelDownload.setEnabled(state.isCancelDownloadButtonEnabled());
        resolve().configureWindowBehaviour(this, state);
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    public void setSystemTray(SystemTray tray) {
        PopupHelper helper = new PopupHelper(this, tray, programs);
        trayIcon = helper.getTrayIcon();
        showHideGUI = helper.getShowHideGUI();
        checkUpdates = helper.getCheckUpdates();
        installUpdates = helper.getInstallUpdates();
        cancelDownload = helper.getCancelDownload();
        exitClient = helper.getExitClient();
        programsLaunchers = helper.getProgramsLaunchers();
    }

    public void showMessage(String title, String message, TrayIcon.MessageType type) {
        if (trayIcon != null)
            trayIcon.displayMessage(title, message, type);
    }

    public void bindCheckUpdatesButton(MouseListener mouseListener, ActionListener actionListener) {
        checkUpdatesButton.addMouseListener(mouseListener);
        if (checkUpdates != null)
            checkUpdates.addActionListener(actionListener);
    }

    public void bindInstallUpdatesButton(MouseListener mouseListener, ActionListener actionListener) {
        installUpdatesButton.addMouseListener(mouseListener);
        if (installUpdates != null)
            installUpdates.addActionListener(actionListener);
    }

    public void bindCancelDownloadButton(MouseListener mouseListener, ActionListener actionListener) {
        cancelDownloadButton.addMouseListener(mouseListener);
        if (cancelDownload != null)
            cancelDownload.addActionListener(actionListener);
    }

    public void bindProgramLauncher(Program program, ActionListener listener) {
        if (programsLaunchers != null && programsLaunchers.containsKey(program))
            programsLaunchers.get(program).addActionListener(listener);
    }

    private void initialize() {
        try {
            setIconImage(ImageIO.read(Resources.class
                    .getResourceAsStream(GuiConfiguration.appIconURL)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeWindow();
        initializeTabs();
        initializeControlPanel();

        resolve().initializeTrayIfPossible(this);
        setStatus(EWindowStatus.UNINITIALIZED);
        setProgressBarInactive();
    }

    private void initializeWindow() {
        try {
            UIManager.setLookAndFeel(GuiConfiguration.LOOK_AND_FEEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setBounds(GuiConfiguration.WINDOW_BOUNDS);
        setMinimumSize(GuiConfiguration.WINDOW_MIN_SIZE);
        setTitle(GuiConfiguration.WINDOW_TITLE);

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[] { 0, 0 };
        gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
        gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
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
                if (showHideGUI != null)
                    showHideGUI.setLabel("Hide");
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                if (showHideGUI != null)
                    showHideGUI.setLabel("Show");
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

        updatesTab = new UpdatesTabContentContainer(programs);
        tabbedPane.addTab("Updates", new JScrollPane(updatesTab));

        settingsTab = new SettingsTabContentContainer();
        tabbedPane.add("Settings", settingsTab);

        for (Program program : programs) {
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

        installUpdatesButton = new JButton("Install updates");
        controlPanel.add(installUpdatesButton, "5, 2, fill, fill");

        cancelDownloadButton = new JButton("Cancell download");
        controlPanel.add(cancelDownloadButton, "7, 2, fill, fill");

        statusLabel = new JLabel("Welcome to AutoUpdater\r\n");
        controlPanel.add(statusLabel, "1, 4, 7, 1");

        progressBar = new JProgressBar();
        controlPanel.add(progressBar, "1, 6, 7, 1, fill, fill");
    }
}
