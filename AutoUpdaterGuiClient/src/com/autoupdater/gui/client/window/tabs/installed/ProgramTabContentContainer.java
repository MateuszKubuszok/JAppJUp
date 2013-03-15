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
package com.autoupdater.gui.client.window.tabs.installed;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.autoupdater.client.models.Program;
import com.autoupdater.gui.client.window.tabs.installed.tabs.bugs.BugsPreview;
import com.autoupdater.gui.client.window.tabs.installed.tabs.changelogs.ChangelogsPreview;
import com.autoupdater.gui.client.window.tabs.installed.tabs.information.InformationPreview;
import com.autoupdater.gui.mocks.MockModels;

public class ProgramTabContentContainer extends JPanel {
    private InformationPreview informationPreview;
    private ChangelogsPreview changelogsPreview;
    private BugsPreview bugsPreview;

    public ProgramTabContentContainer() {
        initialize(MockModels.getInstalledProgram());
    }

    public ProgramTabContentContainer(Program program) {
        initialize(program);
    }

    public void refresh() {
        informationPreview.refresh();
        changelogsPreview.refresh();
        bugsPreview.refresh();
    }

    private void initialize(Program program) {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 616, 0 };
        gridBagLayout.rowHeights = new int[] { 389, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
        gbc_tabbedPane.fill = GridBagConstraints.BOTH;
        gbc_tabbedPane.gridx = 0;
        gbc_tabbedPane.gridy = 0;
        add(tabbedPane, gbc_tabbedPane);

        informationPreview = new InformationPreview(program);
        tabbedPane.add("Information", informationPreview);

        changelogsPreview = new ChangelogsPreview(program);
        tabbedPane.add("Changelogs", changelogsPreview);

        bugsPreview = new BugsPreview(program);
        tabbedPane.add("Known bugs", bugsPreview);
    }
}
