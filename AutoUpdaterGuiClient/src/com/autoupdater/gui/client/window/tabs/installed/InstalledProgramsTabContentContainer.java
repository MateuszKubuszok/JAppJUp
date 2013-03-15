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
