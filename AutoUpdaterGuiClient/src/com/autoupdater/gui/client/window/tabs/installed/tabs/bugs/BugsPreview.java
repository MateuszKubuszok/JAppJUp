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
package com.autoupdater.gui.client.window.tabs.installed.tabs.bugs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.autoupdater.client.models.BugEntry;
import com.autoupdater.client.models.Program;
import com.autoupdater.gui.mocks.MockModels;

public class BugsPreview extends JPanel {
    private Program program;
    private JTextArea bugsTextArea;

    public BugsPreview() {
        initialize(MockModels.getInstalledProgram());
    }

    public BugsPreview(Program program) {
        initialize(program);
    }

    public void refresh() {
        String content = "";
        if (program.getBugs() != null)
            for (BugEntry entry : program.getBugs())
                if (entry != null) {
                    content += entry.getDescription() + "\n";
                    content += "\n";
                }
        bugsTextArea.setText(content);
    }

    private void initialize(Program program) {
        this.program = program;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 5, 430, 5, 0 };
        gridBagLayout.rowHeights = new int[] { 5, 280, 5, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        bugsTextArea = new JTextArea();
        bugsTextArea.setEditable(false);
        GridBagConstraints gbc_changelogsTextPane = new GridBagConstraints();
        gbc_changelogsTextPane.insets = new Insets(0, 0, 5, 5);
        gbc_changelogsTextPane.fill = GridBagConstraints.BOTH;
        gbc_changelogsTextPane.gridx = 1;
        gbc_changelogsTextPane.gridy = 1;

        JScrollPane scrollPane = new JScrollPane(bugsTextArea);
        add(scrollPane, gbc_changelogsTextPane);

        refresh();
    }
}
