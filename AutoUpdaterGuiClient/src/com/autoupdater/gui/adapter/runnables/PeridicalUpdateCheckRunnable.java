package com.autoupdater.gui.adapter.runnables;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.*;

import java.util.Date;

import com.autoupdater.gui.adapter.helpers.ClientOperations;

public class PeridicalUpdateCheckRunnable implements Runnable {
    private final static int MINUTES_BETWEEN_EACH_UPDATE_CHECK = 10;

    private final ClientOperations clientOperations;

    public PeridicalUpdateCheckRunnable(ClientOperations clientOperations) {
        this.clientOperations = clientOperations;
    }

    @Override
    public void run() {
        clientOperations.checkUpdates(false);

        while (!clientOperations.isCheckingUpdatesPerodically()) {
            if (shouldCheckUpdates())
                clientOperations.checkUpdates(false);

            try {
                waitOneMinute();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private boolean shouldCheckUpdates() {
        long lastCheck = MILLISECONDS
                .toMinutes(clientOperations.getLastUpdateCheckTime().getTime());
        long now = MILLISECONDS.toMinutes(new Date().getTime());
        return abs(lastCheck - now) >= MINUTES_BETWEEN_EACH_UPDATE_CHECK;
    }

    private void waitOneMinute() throws InterruptedException {
        sleep(MINUTES.toMillis(1));
    }
}