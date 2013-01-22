package com.autoupdater.gui.tabs.updates;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.models.VersionNumber;
import com.autoupdater.gui.mocks.MockModels;

public class UpdatesTabContentContainer extends JPanel {
    private final SortedSet<Program> programs;
    private final Map<Update, UpdateInformationPanel> updateInformationPanels;

    public UpdatesTabContentContainer() {
        programs = MockModels.getInstalledPrograms();
        updateInformationPanels = new HashMap<Update, UpdateInformationPanel>();
        refresh();
    }

    public UpdatesTabContentContainer(SortedSet<Program> programs) {
        this.programs = programs;
        updateInformationPanels = new HashMap<Update, UpdateInformationPanel>();
        refresh();
    }

    public UpdateInformationPanel getUpdateInformationPanel(Update update) {
        return updateInformationPanels.get(update);
    }

    public void refresh() {
        initialize(programs);
        repaint();
    }

    private void initialize(SortedSet<Program> programs) {
        removeAll();
        updateInformationPanels.clear();

        initializeLayout(programs);
        initializeHeader();
        int lastAddedRow = 0;
        for (Program program : programs)
            lastAddedRow = initializeProgramRowAndPackagesRows(program, lastAddedRow);
    }

    private void initializeLayout(SortedSet<Program> programs) {
        int addedRows = programs.size();
        for (Program program : programs)
            addedRows += program.getPackages().size();

        GridBagLayout gridBagLayout = new GridBagLayout();

        gridBagLayout.columnWidths = new int[] { 15, 180, 120, 120, 155 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };

        gridBagLayout.rowHeights = new int[addedRows];
        gridBagLayout.rowWeights = new double[addedRows + 1];
        gridBagLayout.rowWeights[addedRows] = 1.0;

        setLayout(gridBagLayout);
    }

    public void initializeHeader() {
        Insets headerInsets = new Insets(0, 0, 0, 0);

        JLabel programPackageLabel = new JLabel("Program/Package");
        GridBagConstraints gbcProgramPackageLabel = new GridBagConstraints();
        gbcProgramPackageLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcProgramPackageLabel.gridwidth = 2;
        gbcProgramPackageLabel.insets = headerInsets;
        gbcProgramPackageLabel.gridx = 0;
        gbcProgramPackageLabel.gridy = 0;
        add(programPackageLabel, gbcProgramPackageLabel);

        JLabel installedVersionLabel = new JLabel("Installed version");
        GridBagConstraints gbcInstalledVersionLabel = new GridBagConstraints();
        gbcInstalledVersionLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcInstalledVersionLabel.insets = headerInsets;
        gbcInstalledVersionLabel.gridx = 2;
        gbcInstalledVersionLabel.gridy = 0;
        add(installedVersionLabel, gbcInstalledVersionLabel);

        JLabel newestVersionLabel = new JLabel("Newest version");
        GridBagConstraints gbcNewestVersionLabel = new GridBagConstraints();
        gbcNewestVersionLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcNewestVersionLabel.insets = headerInsets;
        gbcNewestVersionLabel.gridx = 3;
        gbcNewestVersionLabel.gridy = 0;
        add(newestVersionLabel, gbcNewestVersionLabel);
    }

    private int initializeProgramRowAndPackagesRows(Program program, int lastAddedRow) {
        Insets programRowInsets = new Insets(5, 0, 0, 0);

        JLabel programLabel = new JLabel(program.getName());
        GridBagConstraints gbcProgramLabel = new GridBagConstraints();
        gbcProgramLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcProgramLabel.gridwidth = 2;
        gbcProgramLabel.insets = programRowInsets;
        gbcProgramLabel.gridx = 0;
        gbcProgramLabel.gridy = ++lastAddedRow;
        add(programLabel, gbcProgramLabel);

        for (com.autoupdater.client.models.Package _package : program.getPackages())
            lastAddedRow = initializePackageRow(_package, lastAddedRow);

        return lastAddedRow;
    }

    private int initializePackageRow(Package _package, int lastAddedRow) {
        Update update = _package.getUpdate();

        Insets packageRowInsets = new Insets(0, 0, 0, 0);

        JLabel packageLabel = new JLabel(_package.getName());
        GridBagConstraints gbcPackageLabel = new GridBagConstraints();
        gbcPackageLabel.insets = packageRowInsets;
        gbcPackageLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcPackageLabel.gridx = 1;
        gbcPackageLabel.gridy = ++lastAddedRow;
        add(packageLabel, gbcPackageLabel);

        JLabel installedVersionNumberLabel = new JLabel(VersionNumber.UNVERSIONED.equals(_package
                .getVersionNumber()) ? "not installed" : _package.getVersionNumber().toString());
        GridBagConstraints gbcInstalledVersionNumberLabel = new GridBagConstraints();
        gbcInstalledVersionNumberLabel.insets = packageRowInsets;
        gbcInstalledVersionNumberLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcInstalledVersionNumberLabel.gridx = 2;
        gbcInstalledVersionNumberLabel.gridy = lastAddedRow;
        add(installedVersionNumberLabel, gbcInstalledVersionNumberLabel);

        JLabel newestVersionNumberLabel = new JLabel(update != null ? update.getVersionNumber()
                .toString() : "uknown");
        GridBagConstraints gbcNewestVersionNumberLabel = new GridBagConstraints();
        gbcNewestVersionNumberLabel.insets = packageRowInsets;
        gbcNewestVersionNumberLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcNewestVersionNumberLabel.gridx = 3;
        gbcNewestVersionNumberLabel.gridy = lastAddedRow;
        add(newestVersionNumberLabel, gbcNewestVersionNumberLabel);

        JComponent statusLabel;
        if (update != null) {
            statusLabel = new UpdateInformationPanel(update, this);
            updateInformationPanels.put(update, (UpdateInformationPanel) statusLabel);
        } else
            statusLabel = new JLabel("");
        GridBagConstraints gbcStatusLabel = new GridBagConstraints();
        gbcStatusLabel.insets = packageRowInsets;
        gbcStatusLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcStatusLabel.gridx = 4;
        gbcStatusLabel.gridy = lastAddedRow;
        add(statusLabel, gbcStatusLabel);

        return lastAddedRow;
    }
}
