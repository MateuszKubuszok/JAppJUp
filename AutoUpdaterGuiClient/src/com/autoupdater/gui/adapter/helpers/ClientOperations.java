package com.autoupdater.gui.adapter.helpers;

import static com.autoupdater.gui.client.window.EWindowStatus.*;
import static java.lang.Thread.sleep;

import java.io.IOException;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.environment.ProgramSettingsNotFoundException;
import com.autoupdater.gui.adapter.Gui2ClientAdapter;
import com.autoupdater.gui.client.window.EInfoTarget;

public class ClientOperations {
    private final Gui2ClientAdapter adapter;

    private final Thread informationUpdatingThread;
    private int minutesSinceLastUpdateCheck;
    private final static int MINUTES_BETWEEN_EACH_UPDATE_CHECK = 10;

    public ClientOperations(Gui2ClientAdapter adapter) {
        this.adapter = adapter;
        informationUpdatingThread = new Thread(new InformationUpdater());
    }

    public void startUpdateCheck() {
        informationUpdatingThread.start();
    }

    public synchronized void checkUpdates() {
        if (adapter.clientWindow() == null
                || (adapter.clientWindow().getStatus() != UNINITIALIZED && adapter.clientWindow()
                        .getStatus() != IDLE))
            return;

        adapter.windowOperations().setState(FETCHING_UPDATE_INFO);
        adapter.windowOperations().setInstallationInactive();
        minutesSinceLastUpdateCheck = 0;

        (new Thread() {
            @Override
            public void run() {
                try {
                    adapter.queryUtils().queryServerForInformation();
                } catch (DownloadResultException | IOException | ProgramSettingsNotFoundException e) {
                    adapter.windowOperations().reportError("Error occured during update checking",
                            e.getMessage(), EInfoTarget.TOOLTIP);
                    adapter.windowOperations().setState(
                            adapter.dataStorage().isInitiated() ? IDLE : UNINITIALIZED);
                }
            }
        }).start();
    }

    public void cleanTemp() {
        adapter.client().cleanTemp();
    }

    public int getMinutesSinceLastUpdateCheck() {
        return minutesSinceLastUpdateCheck;
    }

    public void setMinutesSinceLastUpdateCheck(int minutesSinceLastUpdateCheck) {
        this.minutesSinceLastUpdateCheck = minutesSinceLastUpdateCheck;
    }

    private class InformationUpdater implements Runnable {
        @Override
        public void run() {
            checkUpdates();

            while (!informationUpdatingThread.isInterrupted()) {
                if (minutesSinceLastUpdateCheck >= MINUTES_BETWEEN_EACH_UPDATE_CHECK)
                    checkUpdates();

                try {
                    waitOneMinute();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        private void waitOneMinute() throws InterruptedException {
            sleep(60 * 1000);
        }
    }
}
