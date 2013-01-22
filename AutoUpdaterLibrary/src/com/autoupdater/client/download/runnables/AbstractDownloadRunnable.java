package com.autoupdater.client.download.runnables;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.autoupdater.client.AutoUpdaterClientException;
import com.autoupdater.client.download.ConnectionConfiguration;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.DownloadServiceProgressMessage;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy;
import com.autoupdater.client.utils.services.ObservableService;

/**
 * Superclass of all DownloadRunnables.
 * 
 * <p>
 * It connects to server, download data and parse it with DownloadStrategy.
 * </p>
 * 
 * <p>
 * Because it's extends ObservableService, it can have Observer notified about
 * changes of current download state. One of those Observers is always
 * DownloadService.
 * </p>
 * 
 * @see com.autoupdater.client.download.runnables.PackagesInfoDownloadRunnable
 * @see com.autoupdater.client.download.runnables.UpdateInfoDownloadRunnable
 * @see com.autoupdater.client.download.runnables.ChangelogInfoDownloadRunnable
 * @see com.autoupdater.client.download.runnables.FileDownloadRunnable
 * @see com.autoupdater.client.download.services.AbstractDownloadService
 * @see com.autoupdater.client.download.runnables.post.download.strategies.IPostDownloadStrategy
 * 
 * @param <Result>
 *            type of returned result
 */
public abstract class AbstractDownloadRunnable<Result> extends
        ObservableService<DownloadServiceMessage> implements Runnable {
    private HttpURLConnection httpURLConnection;
    private Result result;
    private EDownloadStatus state;
    private String fileDestinationPath;
    private IPostDownloadStrategy<Result> downloadStrategy;
    private long contentLength = -1;

    /**
     * Creates AbstractDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     */
    AbstractDownloadRunnable(HttpURLConnection connection) {
        initialize(connection, null);
    }

    /**
     * Creates AbstractDownloadRunnable instance.
     * 
     * @param connection
     *            connection used for obtaining data
     * @param fileDestinationPath
     *            path to file where result should be stored
     */
    AbstractDownloadRunnable(HttpURLConnection connection, String fileDestinationPath) {
        initialize(connection, fileDestinationPath);
    }

    /**
     * Starts download process.
     */
    @Override
    public void run() {
        try {
            connectToServer();
            downloadContent();
            processDownload();
        } catch (IOException | AutoUpdaterClientException e) {
            reportError(e.getMessage());
        } catch (InterruptedException e) {
            reportCancelled();
        }
    }

    /**
     * Returns content length.
     * 
     * @return content length if available, -1 otherwise
     */
    public long getContentLength() {
        return contentLength;
    }

    /**
     * Returns file destination path.
     * 
     * @return file destination path if available, null otherwise
     */
    public String getFileDestinationPath() {
        return fileDestinationPath;
    }

    /**
     * Returns result of download.
     * 
     * @return result of download
     * @throws DownloadResultException
     *             thrown if download failed, was cancelled or isn't finished
     *             yet
     */
    public Result getResult() throws DownloadResultException {
        switch (state) {
        case HASNT_STARTED:
            throw new DownloadResultException("Cannot obtain results - download hasn't started");
        case CONNECTED:
            throw new DownloadResultException(
                    "Cannot obtain results - connection to the server was just made");
        case IN_PROCESS:
            throw new DownloadResultException("Cannot obtain results - download is in process");
        case COMPLETED:
            throw new DownloadResultException(
                    "Cannot obtain results - download is completed but hasn't been processed yet");
        case FAILED:
            throw new DownloadResultException("Cannot obtain results - download resulted in error");
        case CANCELLED:
            throw new DownloadResultException("Cannot obtain results - download cancelled");
        case PROCESSED:
            return result;
        default:
            throw new DownloadResultException("Cannot obtain results - uknown download state");
        }
    }

    /**
     * Return current state of download.
     * 
     * @return current download state
     */
    public EDownloadStatus getState() {
        return state;
    }

    /**
     * Returns DownloadStrategy used for processing result into expected format.
     * 
     * @see com.autoupdater.client.download.runnables.post.download.strategies
     * 
     * @return DownloadStorageStrategyInterface instance
     * @throws IOException
     *             thrown if IO error occurs (used in some implementation)
     */
    abstract protected IPostDownloadStrategy<Result> getPostDownloadStrategy() throws IOException;

    /**
     * Procedure connection to server.
     * 
     * @throws IOException
     *             thrown if connection error occurs
     * @throws InterruptedException
     *             thrown if thread was interrupted (cancelled)
     */
    void connectToServer() throws IOException, InterruptedException {
        reportChange("Initialized process of obtaining packages' data from server: "
                + httpURLConnection.getURL().getHost() + httpURLConnection.getURL().getPath());

        httpURLConnection.setConnectTimeout(ConnectionConfiguration.CONNECTION_TIME_OUT);
        httpURLConnection.setReadTimeout(ConnectionConfiguration.CONNECTION_TIME_OUT);
        httpURLConnection.connect();

        if (httpURLConnection.getResponseCode() / 100 != 2)
            throw new IOException("Server returned response code "
                    + httpURLConnection.getResponseCode());

        contentLength = httpURLConnection.getContentLengthLong();

        reportChange("Connected to the server", EDownloadStatus.CONNECTED);
    }

    /**
     * Procedure downloading content from server.
     * 
     * <p>
     * Has to be called after connectToServer().
     * </p>
     * 
     * @throws IOException
     *             thrown if connection error occurs
     * @throws InterruptedException
     *             thrown if thread was interrupted (cancelled)
     */
    void downloadContent() throws IOException, InterruptedException {
        if (downloadStrategy == null)
            downloadStrategy = getPostDownloadStrategy();
        InputStream in = httpURLConnection.getInputStream();

        byte[] buffer = new byte[ConnectionConfiguration.MAX_BUFFER_SIZE];
        long downloadStartTime = System.currentTimeMillis();

        reportChange(EDownloadStatus.IN_PROCESS.getMessage(), EDownloadStatus.IN_PROCESS);

        int bytesRead = 0;
        long bytesWritten = 0;
        while ((bytesRead = in.read(buffer)) != -1) {
            checkInterruption();
            downloadStrategy.write(buffer, bytesRead);
            bytesWritten += bytesRead;

            long elapsedTime = System.currentTimeMillis() - downloadStartTime;
            if (elapsedTime > 500)
                reportProgress(elapsedTime, bytesWritten);
        }
        in.close();

        reportChange(EDownloadStatus.COMPLETED.getMessage(), EDownloadStatus.COMPLETED);
    }

    /**
     * Procedure processing download using DownloadStrategy.
     * 
     * <p>
     * Has to be called after downloadContent().
     * </p>
     * 
     * @throws AutoUpdaterClientException
     *             thrown if ParserException or DownloadResultException occurred
     * @throws IOException
     *             thrown if IO error occurs (used in come implementations of
     *             DownloadStrategy)
     * @throws InterruptedException
     *             thrown if thread was interrupted (cancelled)
     */
    void processDownload() throws AutoUpdaterClientException, IOException, InterruptedException {
        if (downloadStrategy == null)
            downloadStrategy = getPostDownloadStrategy();
        result = downloadStrategy.processDownload();
        reportChange(EDownloadStatus.PROCESSED.getMessage(), EDownloadStatus.PROCESSED);
    }

    /**
     * Initializes AbstractDownloadRunnable.
     * 
     * @param connection
     *            connection used for obtaining data
     * @param fileDestinationPath
     *            path to destination file
     */
    private void initialize(HttpURLConnection connection, String fileDestinationPath) {
        this.httpURLConnection = connection;
        this.fileDestinationPath = fileDestinationPath;
        result = null;
        state = EDownloadStatus.HASNT_STARTED;
    }

    /**
     * Sent message that download was cancelled.
     */
    private void reportCancelled() {
        state = EDownloadStatus.CANCELLED;
        hasChanged();
        notifyObservers(new DownloadServiceMessage(this, EDownloadStatus.CANCELLED.getMessage()));
    }

    /**
     * Send message about download current state.
     * 
     * @param message
     *            message about change
     * @throws InterruptedException
     *             thrown if thread was interrupted (cancelled)
     */
    private void reportChange(String message) throws InterruptedException {
        checkInterruption();
        hasChanged();
        notifyObservers(new DownloadServiceMessage(this, message));
    }

    /**
     * Send message about download current state.
     * 
     * @param message
     *            message about change
     * @param state
     *            new download state
     * @throws InterruptedException
     *             thrown if thread was interrupted (cancelled)
     */
    private void reportChange(String message, EDownloadStatus state) throws InterruptedException {
        checkInterruption();
        this.state = state;
        hasChanged();
        notifyObservers(new DownloadServiceMessage(this, message));
    }

    /**
     * Send message that download failed.
     * 
     * @param message
     *            error message
     */
    private void reportError(String message) {
        state = EDownloadStatus.FAILED;
        hasChanged();
        notifyObservers(new DownloadServiceMessage(this, message, true));
    }

    /**
     * Send message about download progress.
     * 
     * @param elapsedTime
     *            elapsed time
     * @param bytesRead
     *            bytes already read
     * @throws InterruptedException
     *             thrown if thread was interrupted (cancelled)
     */
    private void reportProgress(long elapsedTime, long bytesRead) throws InterruptedException {
        checkInterruption();
        hasChanged();
        notifyObservers(new DownloadServiceProgressMessage(this, bytesRead, contentLength,
                elapsedTime, bytesRead));
    }

    /**
     * Check whether thread was interrupted (cancelled). If so throws
     * InterruptedException.
     * 
     * @throws InterruptedException
     *             thrown if thread was interrupted (cancelled)
     */
    private synchronized void checkInterruption() throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
    }
}
