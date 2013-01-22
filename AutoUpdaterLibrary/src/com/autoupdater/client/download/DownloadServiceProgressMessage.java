package com.autoupdater.client.download;

import java.text.DecimalFormat;

import com.autoupdater.client.utils.services.ObservableService;

/**
 * Specialized version of DownloadServiceMessage that pass also information
 * about progress of download.
 */
public class DownloadServiceProgressMessage extends DownloadServiceMessage {
    private final long currentAmount;
    private final long overallAmount;
    private final long elapsedTime;
    private final long bytesRead;
    private String message;

    /**
     * Creates an instance of DownloadServiceMessage.
     * 
     * @param originalService
     *            original sender of the message
     * @param currentAmount
     *            amount of data that was downloaded up till now
     * @param overallAmount
     *            amount of overall data that should be downloaded
     * @param elapsedTime
     *            time since last progress update
     * @param bytesRead
     *            bytes read since last update
     */
    public DownloadServiceProgressMessage(
            ObservableService<DownloadServiceMessage> originalService, long currentAmount,
            long overallAmount, long elapsedTime, long bytesRead) {
        super(originalService, null);
        this.currentAmount = currentAmount;
        this.overallAmount = overallAmount;
        this.elapsedTime = elapsedTime;
        this.bytesRead = bytesRead;
    }

    /**
     * Returns amount of already downloaded data.
     * 
     * @return amount of already downloaded data
     */
    public long getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Returns amount of overall data that should be downloaded.
     * 
     * @return amount of overall data that should be downloaded
     */
    public long getOverallAmount() {
        return overallAmount;
    }

    @Override
    public String getMessage() {
        return message != null ? message : (message = calculateSpeed());
    }

    @Override
    public DownloadServiceProgressMessage getProgressMessage() {
        return this;
    }

    /**
     * Calculates current average download speed and returns it as String.
     * 
     * @return average download speed
     */
    private String calculateSpeed() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        double size = (bytesRead) / 1024.0;
        double time = (elapsedTime) / 1000.0;
        double kilobytesPerSecond = size / time;

        if (kilobytesPerSecond / 1024.0 > 1)
            return decimalFormat.format(kilobytesPerSecond / 1024.0) + " MB/s";
        return decimalFormat.format(kilobytesPerSecond) + " kB/s";
    }
}
