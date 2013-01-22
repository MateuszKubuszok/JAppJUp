package com.autoupdater.gui.main;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JOptionPane;

import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentDataManager;
import com.autoupdater.gui.adapters.Gui2ClientAdapter;
import com.autoupdater.gui.settings.editor.EditorWindow;
import com.autoupdater.gui.window.GuiClientWindow;


public class Main {
    public static void main(String[] args) {
        EnvironmentDataManager edm = new EnvironmentDataManager();

        if (!ensureConfigExists(edm))
            return;

        if (Arrays.asList(args).contains("--config"))
            showConfig(edm);
        else
            showClient(edm);
    }

    private static boolean ensureConfigExists(EnvironmentDataManager edm) {
        try {
            edm.getEnvironmentData();
        } catch (ClientEnvironmentException | IOException e) {
            try {
                edm.createDefaultSettings().save();
            } catch (ClientEnvironmentException | IOException e1) {
                showError(e1);
                return false;
            }
        }

        return true;
    }

    private static void showConfig(final EnvironmentDataManager edm) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new EditorWindow(edm.getEnvironmentData());
                } catch (ClientEnvironmentException | IOException e) {
                    showError(e);
                }
            }
        });
    }

    private static void showClient(final EnvironmentDataManager edm) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final Gui2ClientAdapter gca = new Gui2ClientAdapter(edm);
                    gca.setClientWindow(new GuiClientWindow(gca.getProgramsThatShouldBeDisplayed()));
                } catch (Exception e) {
                    showError(e);
                }
            }
        });
    }

    private static void showError(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Couldn't initiate Updater",
                JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
