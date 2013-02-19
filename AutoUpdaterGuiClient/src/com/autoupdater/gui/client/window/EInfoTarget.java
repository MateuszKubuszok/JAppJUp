package com.autoupdater.gui.client.window;

public enum EInfoTarget {
    STATUS_BAR(true, false), TOOLTIP(false, true), ALL(true, true);

    private final boolean shouldUpdateStatusBar;
    private final boolean shouldUpdateToolTip;

    private EInfoTarget(boolean shouldUpdateStatusBar, boolean shouldUpdateToolTip) {
        this.shouldUpdateStatusBar = shouldUpdateStatusBar;
        this.shouldUpdateToolTip = shouldUpdateToolTip;
    }

    public boolean shouldUpdateStatusBar() {
        return shouldUpdateStatusBar;
    }

    public boolean shouldUpdateToolTip() {
        return shouldUpdateToolTip;
    }
}
