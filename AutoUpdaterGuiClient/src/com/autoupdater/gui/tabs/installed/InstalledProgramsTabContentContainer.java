package com.autoupdater.gui.tabs.installed;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class InstalledProgramsTabContentContainer extends JPanel {

    /**
     * Create the panel.
     */
    public InstalledProgramsTabContentContainer() {
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane);
        
        JPanel panel = new JPanel();
        tabbedPane.addTab("New tab", null, panel, null);

    }

}
