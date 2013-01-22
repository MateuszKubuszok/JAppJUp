package com.autoupdater.gui.tabs.updates;

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
                        parent.repaint();
                    }
                } else {
                    label.setText(message.getMessage());
                    if (progressBarUsed) {
                        removeAll();
                        add(label);
                        progressBarUsed = false;
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
                if (message.isInstallationFailed())
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
