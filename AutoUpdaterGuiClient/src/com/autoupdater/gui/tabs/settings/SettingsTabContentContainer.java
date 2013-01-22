package com.autoupdater.gui.tabs.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentData;
import com.autoupdater.gui.mocks.MockModels;

public class SettingsTabContentContainer extends JPanel {
    private EnvironmentData environmentData;

    private JTextField proxyAddress;
    private JTextField proxyPort;

    public SettingsTabContentContainer() {
        initialize();
        this.environmentData = MockModels.getEnvironmentData();
    }

    public SettingsTabContentContainer(EnvironmentData environmentData) {
        initialize();
        this.environmentData = environmentData;
    }

    public void setEnvironmentData(EnvironmentData environmentData) {
        this.environmentData = environmentData;
        reset();
    }

    private void save() {
        environmentData.getClientSettings().setProxyAddress(proxyAddress.getText());
        environmentData.getClientSettings().setProxyPort(proxyPort.getText());
        try {
            environmentData.save();
        } catch (ClientEnvironmentException | IOException e) {
        }
    }

    private void reset() {
        proxyAddress.setText(environmentData.getClientSettings().getProxyAddress());
        proxyPort.setText(String.valueOf(environmentData.getClientSettings().getProxyPort()));
    }

    private void initialize() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 54, 0, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JPanel proxySettings = new JPanel();
        proxySettings.setBorder(new TitledBorder(null, "Proxy settings", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        GridBagConstraints gbc_proxySettings = new GridBagConstraints();
        gbc_proxySettings.gridwidth = 3;
        gbc_proxySettings.insets = new Insets(0, 0, 5, 5);
        gbc_proxySettings.fill = GridBagConstraints.BOTH;
        gbc_proxySettings.gridx = 1;
        gbc_proxySettings.gridy = 1;
        add(proxySettings, gbc_proxySettings);
        GridBagLayout gbl_proxySettings = new GridBagLayout();
        gbl_proxySettings.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_proxySettings.rowHeights = new int[] { 0, 0, 0 };
        gbl_proxySettings.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
        gbl_proxySettings.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        proxySettings.setLayout(gbl_proxySettings);

        JLabel proxyAddressLabel = new JLabel("Address");
        GridBagConstraints gbc_proxyAddressLabel = new GridBagConstraints();
        gbc_proxyAddressLabel.anchor = GridBagConstraints.EAST;
        gbc_proxyAddressLabel.insets = new Insets(0, 0, 5, 5);
        gbc_proxyAddressLabel.gridx = 0;
        gbc_proxyAddressLabel.gridy = 0;
        proxySettings.add(proxyAddressLabel, gbc_proxyAddressLabel);

        proxyAddress = new JTextField();
        GridBagConstraints gbc_proxyAddress = new GridBagConstraints();
        gbc_proxyAddress.gridwidth = 2;
        gbc_proxyAddress.insets = new Insets(0, 0, 5, 5);
        gbc_proxyAddress.fill = GridBagConstraints.HORIZONTAL;
        gbc_proxyAddress.gridx = 1;
        gbc_proxyAddress.gridy = 0;
        proxySettings.add(proxyAddress, gbc_proxyAddress);
        proxyAddress.setColumns(10);

        JLabel proxyPortLabel = new JLabel("Port");
        GridBagConstraints gbc_proxyPortLabel = new GridBagConstraints();
        gbc_proxyPortLabel.insets = new Insets(0, 0, 0, 5);
        gbc_proxyPortLabel.anchor = GridBagConstraints.EAST;
        gbc_proxyPortLabel.gridx = 0;
        gbc_proxyPortLabel.gridy = 1;
        proxySettings.add(proxyPortLabel, gbc_proxyPortLabel);

        proxyPort = new JTextField();
        GridBagConstraints gbc_proxyPort = new GridBagConstraints();
        gbc_proxyPort.insets = new Insets(0, 0, 0, 5);
        gbc_proxyPort.anchor = GridBagConstraints.WEST;
        gbc_proxyPort.gridx = 1;
        gbc_proxyPort.gridy = 1;
        proxySettings.add(proxyPort, gbc_proxyPort);
        proxyPort.setColumns(10);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        GridBagConstraints gbc_saveButton = new GridBagConstraints();
        gbc_saveButton.insets = new Insets(0, 0, 0, 5);
        gbc_saveButton.gridx = 2;
        gbc_saveButton.gridy = 2;
        add(saveButton, gbc_saveButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        GridBagConstraints gbc_resetButton = new GridBagConstraints();
        gbc_resetButton.insets = new Insets(0, 0, 0, 5);
        gbc_resetButton.gridx = 3;
        gbc_resetButton.gridy = 2;
        add(resetButton, gbc_resetButton);
    }
}
