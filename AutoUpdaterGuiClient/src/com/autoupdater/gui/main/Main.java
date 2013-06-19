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
package com.autoupdater.gui.main;

import static java.awt.EventQueue.invokeLater;
import static javax.swing.JOptionPane.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import net.jsdpu.logger.LogManager;

import com.autoupdater.client.environment.ClientEnvironmentException;
import com.autoupdater.client.environment.EnvironmentDataManager;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.client.window.GuiClientWindow;
import com.autoupdater.gui.settings.editor.EditorWindow;

public class Main {
    public static void main(String[] args) {
        setUpLogger();

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

    private static void setUpLogger() {
        FileInputStream configFile = null;
        try {
            configFile = new FileInputStream("./client.logger.properties");
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (SecurityException | IOException e) {
        } finally {
            if (configFile != null)
                try {
                    configFile.close();
                } catch (IOException e) {
                }
        }
    }

    private static void showConfig(final EnvironmentDataManager edm) {
        invokeLater(new Runnable() {
            @SuppressWarnings("unused")
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
        invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final Gui2ClientAdapter gca = new Gui2ClientAdapter(edm);
                    gca.clientWindow(new GuiClientWindow(gca.environmentData()));
                } catch (Exception e) {
                    showError(e);
                }
            }
        });
    }

    private static void showError(Exception e) {
        showMessageDialog(null, e.getMessage(), "Couldn't initiate Updater", ERROR_MESSAGE);
        e.printStackTrace();
    }
}
