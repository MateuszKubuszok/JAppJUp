package com.autoupdater.gui.settings.editor;

import static com.autoupdater.gui.mocks.MockModels.getProgramsSettings;
import static java.awt.BorderLayout.*;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.environment.settings.ProgramSettingsBuilder;
import com.autoupdater.gui.config.GuiConfiguration;
import com.google.common.base.Joiner;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class EditorWindow extends JFrame {
    private final EnvironmentData environmentData;
    private final SortedSet<ProgramSettings> programsSettings;

    private JPanel contentPane;
    private JTable table;
    private JPanel buttons;

    /**
     * @wbp.parser.constructor
     */
    public EditorWindow() {
        environmentData = null;
        programsSettings = getProgramsSettings();
        initialize();
    }

    public EditorWindow(EnvironmentData environmentData) {
        this.environmentData = environmentData;
        this.programsSettings = environmentData.getProgramsSettings();
        initialize();
    }

    private void initialize() {
        try {
            setLookAndFeel(GuiConfiguration.LOOK_AND_FEEL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Edit installed programs' settings");

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        setBounds(100, 100, 600, 450);
        setMinimumSize(getSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton addProgram = new JButton("Add Program");
        addProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createAddProgramWindow();
            }
        });
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (environmentData != null)
                    try {
                        environmentData.save();
                    } catch (ClientEnvironmentException | IOException e) {
                        e.printStackTrace();
                    }
            }
        });
        buttons = new JPanel(new BorderLayout());
        buttons.add(addProgram, BorderLayout.WEST);
        buttons.add(save, BorderLayout.EAST);

        initializeTable();

        setVisible(true);
    }

    private void initializeTable() {
        contentPane.removeAll();

        List<String[]> settings = new ArrayList<String[]>();
        for (ProgramSettings programSettings : programsSettings) {
            settings.add(new String[] { programSettings.getProgramName(),
                    programSettings.getPathToProgramDirectory(),
                    programSettings.getServerAddress(), programSettings.getProgramExecutableName(),
                    programSettings.getPathToProgram(),
                    Boolean.toString(programSettings.isDevelopmentVersion()) });
        }
        table = new JTable(settings.toArray(new String[0][5]),
                new String[] { "Program Name", "Directory", "Server", "Executable name",
                        "Running command", "Development version" });

        addTable();
        addButtons();
    }

    private void addTable() {
        contentPane.add(new JScrollPane(table), CENTER);
    }

    private void addButtons() {
        contentPane.add(buttons, SOUTH);
    }

    private void createAddProgramWindow() {
        final JTextField programNameText = new JTextField();
        final JFileChooser programDirectoryFile = new JFileChooser();
        programDirectoryFile.setDialogTitle("Select installation directory");
        programDirectoryFile.setFileSelectionMode(DIRECTORIES_ONLY);
        programDirectoryFile.setAcceptAllFileFilterUsed(false);
        final JTextField programExecutableText = new JTextField();
        final JTextField commandText = new JTextField();
        final JTextField serverAddressText = new JTextField();
        final JCheckBox developmentVersionCheck = new JCheckBox();
        final JButton add = new JButton("Add");

        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(
                "right:pref, 3dlu, 250dlu, 7dlu"));
        builder.append(new JLabel("Program's name:"), programNameText);
        builder.append(new JLabel("Installation dir:"), programDirectoryFile);
        builder.append(new JLabel("Executable (for kill):"), programExecutableText);
        builder.append(new JLabel("Run command:"), commandText);
        builder.append(new JLabel("Respository:"), serverAddressText);
        builder.append(new JLabel("Development version:"), developmentVersionCheck);
        builder.append(new JLabel(""), add);

        final JFrame addedSettings = new JFrame("Add new program");
        addedSettings.setContentPane(new JScrollPane(builder.getPanel()));
        addedSettings.setBounds(50, 50, 600, 400);
        addedSettings.setResizable(false);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = programNameText.getText();
                String pathToDir = programDirectoryFile.getSelectedFile() != null ? programDirectoryFile
                        .getSelectedFile().getAbsolutePath() : null;
                String command = commandText.getText();
                String executable = programExecutableText.getText();
                String address = serverAddressText.getText();
                boolean development = developmentVersionCheck.isSelected();

                // check errors
                List<String> errors = new ArrayList<String>();
                if (name == null || name.isEmpty())
                    errors.add("Program's name cannot be empty!");
                if (pathToDir == null || pathToDir.isEmpty())
                    errors.add("Installation directory cannot be empty!");
                if (command == null || command.isEmpty())
                    errors.add("Run command cannot be empty!");
                if (executable == null || executable.isEmpty())
                    errors.add("Executable's name cannot be empty!");
                if (address == null || address.isEmpty())
                    errors.add("Repository's address cannot be empty!");

                if (!errors.isEmpty()) {
                    reportError(addedSettings, errors);
                    return;
                }

                ProgramSettingsBuilder psBuilder = ProgramSettingsBuilder.builder();
                psBuilder.setProgramName(name).setPathToProgramDirectory(pathToDir)
                        .setProgramExecutableName(executable).setPathToProgram(command)
                        .setServerAddress(address).setDevelopmentVersion(development);
                ProgramSettings settings = psBuilder.build();

                if (programsSettings.contains(settings)) {
                    errors.add("Settings with such name, installation dir and reposotory already exists!");
                    reportError(addedSettings.getContentPane(), errors);
                    return;
                }

                programsSettings.add(settings);
                addedSettings.dispose();
                initializeTable();
                table.repaint();
                revalidate();
                repaint();
            }
        });

        addedSettings.setVisible(true);
    }

    private void reportError(Container container, List<String> errors) {
        showMessageDialog(container, Joiner.on("\n").join(errors), "Wrong input",
                JOptionPane.ERROR_MESSAGE);
    }
}
