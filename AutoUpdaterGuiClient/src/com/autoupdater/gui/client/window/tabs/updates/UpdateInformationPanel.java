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
package com.autoupdater.gui.client.window.tabs.updates;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.DownloadServiceProgressMessage;
import com.autoupdater.client.download.services.FileDownloadService;
import com.autoupdater.client.models.EUpdateStatus;
import com.autoupdater.client.models.Update;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;

public class UpdateInformationPanel extends JPanel {
    private final JComponent parent;
    private final JProgressBar progressBar;
    private final JLabel label;
    private final Update update;
    private FileDownloadService downloadService;
    private boolean progressBarUsed;

    public UpdateInformationPanel(Update update, JComponent parent) {
        this.parent = parent;
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 1, 0 };
        gridBagLayout.rowHeights = new int[] { 1, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        this.update = update;
        label = new JLabel();

        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.NORTHWEST;
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        add(label, gbc_label);
        update.addObserver(new UpdateInstallationObserver());
    }

    public void setDownloadService(FileDownloadService downloadService) {
        this.downloadService = downloadService;
        downloadService.addObserver(new UpdateDownloadObserver());
    }

    private class UpdateDownloadObserver implements IObserver<DownloadServiceMessage> {
        @Override
        public void update(ObservableService<DownloadServiceMessage> observable,
                DownloadServiceMessage message) {
            if (observable == downloadService) {
                DownloadServiceProgressMessage progressMessage = message.getProgressMessage();
                if (progressMessage != null) {
                    progressBar.setValue((int) progressMessage.getCurrentAmount());
                    progressBar.setString(progressMessage.getMessage());
                    if (!progressBarUsed) {
                        removeAll();
                        progressBar.setMinimum(0);
                        progressBar.setMaximum((int) progressMessage.getOverallAmount());
                        add(progressBar);
                        progressBarUsed = true;
                        parent.revalidate();
                        parent.repaint();
                    }
                } else {
                    label.setText(message.getMessage());
                    if (progressBarUsed) {
                        removeAll();
                        add(label);
                        progressBarUsed = false;
                        parent.revalidate();
                        parent.repaint();
                    }
                }
                repaint();
            }
        }
    }

    private class UpdateInstallationObserver implements IObserver<EUpdateStatus> {
        @Override
        public void update(ObservableService<EUpdateStatus> observable, EUpdateStatus message) {
            if (observable == update) {
                if (message.isInstallationFailed() && !update.getStatusMessage().isEmpty())
                    label.setText(message.getMessage() + ": " + update.getStatusMessage());
                else
                    label.setText(message.getMessage());
                if (progressBarUsed) {
                    removeAll();
                    add(label);
                    progressBarUsed = false;
                    parent.repaint();
                }
                repaint();
            }
        }
    }
}
