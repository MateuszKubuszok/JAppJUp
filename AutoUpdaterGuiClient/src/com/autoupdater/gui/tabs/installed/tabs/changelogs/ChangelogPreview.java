package com.autoupdater.gui.tabs.installed.tabs.changelogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.autoupdater.client.models.ChangelogEntry;
import com.autoupdater.client.models.Package;
import com.autoupdater.gui.mocks.MockModels;

public class ChangelogPreview extends JPanel {
    private Package _package;
    private JTextArea changelogsTextArea;

    public ChangelogPreview() {
        initialize(MockModels.getInstalledPackage());
    }

    public ChangelogPreview(Package _package) {
        initialize(_package);
    }

    public void refresh() {
        String content = "";
        if (_package.getChangelog() != null)
            for (ChangelogEntry entry : _package.getChangelog())
                if (entry != null) {
                    content += entry.getVersionNumber() + "\n";
                    content += entry.getChanges() + "\n";
                    content += "\n";
                }
        changelogsTextArea.setText(content);
    }

    private void initialize(Package _package) {
        this._package = _package;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 5, 430, 5, 0 };
        gridBagLayout.rowHeights = new int[] { 5, 280, 5, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        changelogsTextArea = new JTextArea();
        changelogsTextArea.setEditable(false);
        GridBagConstraints gbc_changelogsTextPane = new GridBagConstraints();
        gbc_changelogsTextPane.insets = new Insets(0, 0, 5, 5);
        gbc_changelogsTextPane.fill = GridBagConstraints.BOTH;
        gbc_changelogsTextPane.gridx = 1;
        gbc_changelogsTextPane.gridy = 1;
        add(changelogsTextArea, gbc_changelogsTextPane);

        refresh();
    }
}
