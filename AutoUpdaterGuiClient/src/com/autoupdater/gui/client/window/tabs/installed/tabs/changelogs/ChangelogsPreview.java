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
package com.autoupdater.gui.client.window.tabs.installed.tabs.changelogs;

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
