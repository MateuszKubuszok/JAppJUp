package com.autoupdater.gui.tabs.installed.tabs.changelogs;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.gui.mocks.MockModels;

public class ChangelogsPreview extends JPanel {
    private final Program program;
    private JTabbedPane content;

    public ChangelogsPreview() {
        program = MockModels.getInstalledProgram();
        initialize();
    }

    public ChangelogsPreview(Program program) {
        this.program = program;
        initialize();
    }

    public void refresh() {
        content.removeAll();

        for (Package _package : program.getPackages()) {
            ChangelogPreview changelogTab = new ChangelogPreview(_package);
            String title = _package.getName().length() <= 30 ? _package.getName() : (_package
                    .getName().substring(0, 26) + "...");
            content.addTab(title, changelogTab);
        }

        content.repaint();
    }

    private void initialize() {
        setLayout(new CardLayout(0, 0));

        content = new JTabbedPane(JTabbedPane.LEFT);
        add(content, "packagesTabbedPane");

        refresh();
    }
}
