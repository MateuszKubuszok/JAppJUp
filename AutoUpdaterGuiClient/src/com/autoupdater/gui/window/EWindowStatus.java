package com.autoupdater.gui.window;

public enum EWindowStatus {
    UNINITIALIZED(true, false, false, true), //
    IDLE(true, true, false, true), //
    FETCHING_UPDATE_INFO(false, false, false, true), //
    FETCHING_UPDATES(false, false, false, true), //
    INSTALLING_UPDATES(false, false, false, false);

    private final boolean checkUpdatesButtonEnabled;
    private final boolean installUpdatesButtonEnabled;
    private final boolean cancelDownloadButtonEnabled;
    private final boolean programAbleToFinish;

    private EWindowStatus(boolean checkUpdatesButtonEnabled, boolean installUpdatesButtonEnabled,
            boolean cancelDownloadButtonEnabled, boolean programAbleToFinish) {
        this.checkUpdatesButtonEnabled = checkUpdatesButtonEnabled;
        this.installUpdatesButtonEnabled = installUpdatesButtonEnabled;
        this.cancelDownloadButtonEnabled = cancelDownloadButtonEnabled;
        this.programAbleToFinish = programAbleToFinish;
    }

    public boolean isCheckUpdatesButtonEnabled() {
        return checkUpdatesButtonEnabled;
    }

    public boolean isInstallUpdatesButtonEnabled() {
        return installUpdatesButtonEnabled;
    }

    public boolean isCancelDownloadButtonEnabled() {
        return cancelDownloadButtonEnabled;
    }

    public boolean isProgramAbleToFinish() {
        return programAbleToFinish;
    }
}