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
package com.autoupdater.gui.client.window.tabs.installed.tabs.information;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.autoupdater.client.models.Package;
import com.autoupdater.client.models.Program;
import com.autoupdater.gui.mocks.MockModels;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class InformationPreview extends JPanel {
    private Program program;

    private JLabel programNameLabel;
    private JLabel programPathLabel;
    private JLabel serverAddressLabel;
    private JTextArea packagesLabel;

    public InformationPreview() {
        initialize(MockModels.getInstalledProgram());
    }

    public InformationPreview(Program program) {
        initialize(program);
    }

    public void refresh() {
        programNameLabel.setText(program.getName());
        programPathLabel.setText(program.getPathToProgramDirectory());
        serverAddressLabel.setText(program.getServerAddress());
        String packages = "";
        for (Package _package : program.getPackages()) {
            packages += (_package.getName() + " (" + _package.getVersionNumber() + ")" + "\n");
        }
        packagesLabel.setText(packages);
    }

    private void initialize(Program program) {
        this.program = program;

        setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("18px"),
                ColumnSpec.decode("143px"), FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("left:200px:grow"), ColumnSpec.decode("max(5dlu;default)"), },
                new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("14px"),
                        FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("14px"),
                        FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("14px"),
                        FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("235px:grow"),
                        RowSpec.decode("max(5dlu;default)"), }));

        add(new JLabel("Program name:"), "2, 2, right, top");
        programNameLabel = new JLabel();
        add(programNameLabel, "4, 2, left, top");

        add(new JLabel("Path to program directory:"), "2, 4, right, top");
        programPathLabel = new JLabel();
        add(programPathLabel, "4, 4, left, top");

        add(new JLabel("Server repo address:"), "2, 6, right, top");
        serverAddressLabel = new JLabel();
        add(serverAddressLabel, "4, 6, left, top");

        add(new JLabel("Packages:"), "2, 8, right, top");

        packagesLabel = new JTextArea();
        packagesLabel.setWrapStyleWord(true);
        packagesLabel.setLineWrap(true);
        packagesLabel.setEditable(false);
        add(packagesLabel, "4, 8, fill, fill");

        refresh();
    }
}
