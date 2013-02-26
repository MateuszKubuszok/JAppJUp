package com.autoupdater.gui.adapter.helpers;

import static com.autoupdater.gui.client.window.EInfoTarget.*;
import static com.autoupdater.gui.client.window.EWindowStatus.*;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.*;

import java.io.IOException;
import java.util.Date;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;

public class ClientOperations {
    private final Gui2ClientAdapter adapter;

    private final Thread informationUpdatingThread;
    private Date lastUpdateCheckTime;
    private final static int MINUTES_BETWEEN_EACH_UPDATE_CHECK = 10;

    public ClientOperations(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
        informationUpdatingThread = new Thread(new InformationUpdater());
    }

    public void startUpdateCheck() {
        informationUpdatingThread.start();
    }

    public synchronized void checkUpdates(final boolean onDemand) {
        if (onDemand)
            adapter.windowOperations().reportInfo("Checking updates", "Checking updates on demand",
                    TOOLTIP);

        if (adapter.clientWindow() == null
                || (adapter.clientWindow().getStatus() != UNINITIALIZED && adapter.clientWindow()
                        .getStatus() != IDLE))
            return;

        adapter.windowOperations().setState(FETCHING_UPDATE_INFO);
        adapter.windowOperations().setInstallationInactive();
        lastUpdateCheckTime = new Date();

        (new Thread() {
            @Override
            public void run() {
                try {
                    adapter.queryUtils().queryServerForInformation().join();
                    if (onDemand)
                        adapter.windowOperations().reportInfo("Checking updates",
                                "Updates fetched", TOOLTIP);
                } catch (DownloadResultException | IOException | ProgramSettingsNotFoundException
                        | InterruptedException e) {
                    adapter.windowOperations().reportError("Error occured during update checking",
                            e.getMessage(), ALL);
                    adapter.windowOperations().setState(
                            adapter.dataStorage().isInitiated() ? IDLE : UNINITIALIZED);
                }
            }
        }).start();
    }

    public void cleanTemp() {
        adapter.client().cleanTemp();
    }

    private class InformationUpdater implements Runnable {
        @Override
        public void run() {
            checkUpdates(false);

            while (!informationUpdatingThread.isInterrupted()) {
                if (shouldCheckUpdates())
                    checkUpdates(false);

                try {
                    waitOneMinute();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        private boolean shouldCheckUpdates() {
            long lastCheck = MILLISECONDS.toMinutes(lastUpdateCheckTime.getTime());
            long now = MILLISECONDS.toMinutes(new Date().getTime());
            return abs(lastCheck - now) >= MINUTES_BETWEEN_EACH_UPDATE_CHECK;
        }

        private void waitOneMinute() throws InterruptedException {
            sleep(MINUTES.toMillis(1));
        }
    }
}
