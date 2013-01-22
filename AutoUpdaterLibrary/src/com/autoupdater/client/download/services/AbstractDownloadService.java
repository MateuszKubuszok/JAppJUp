package com.autoupdater.client.download.services;

import java.lang.Thread.State;
import java.net.HttpURLConnection;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;
import com.google.common.base.Objects;

/**
 * Superclass of all DownloadServices.
 * 
 * <p>
 * Results can be obtained by getResult() as soon as state (getState()) changes
 * to PROCESSED.
 * </p>
 * 
 * <p>
 * Current state of download can be monitored, since DownloadService is
 * ObservableService. Messages are passed as DownloadServiceMessages. If
 * download state is IN_PROGRESS, massages are passed as
 * DownloadServiceProgressMessages.
 * </p>
 * 
 * <p>
 * If download fails or is cancelled it's state is changed to FAILED or
 * CANCELLED respectively.
 * </p>
 * 
 * @see com.autoupdater.client.download.services.PackagesInfoDownloadService
 * @see com.autoupdater.client.download.services.UpdateInfoDownloadService
 * @see com.autoupdater.client.download.services.ChangelogInfoDownloadService
 * @see com.autoupdater.client.download.services.FileDownloadService
 * @see com.autoupdater.client.download.DownloadServiceMessage
 * @see com.autoupdater.client.download.DownloadServiceProgressMessage
 * 
 * @param <Result>
 *            type of result returned by download service
 */
public abstract class AbstractDownloadService<Result> extends
        ObservableService<DownloadServiceMessage> implements IObserver<DownloadServiceMessage> {
    private AbstractDownloadRunnable<Result> runnable;
    private Thread downloadThread;
    private HttpURLConnection connection;
    private String fileDestinationPath;

    /**
     * Creates instance of AbstractDownloadService.
     * 
     * <p>
     * Used by some implementations.
     * <p>
     * 
     * @see com.autoupdater.client.download.services.PackagesInfoDownloadService
     * @see com.autoupdater.client.download.services.UpdateInfoDownloadService
     * @see com.autoupdater.client.download.services.ChangelogInfoDownloadService
     * 
     * @param connection
     *            connection used for obtain data
     */
    public AbstractDownloadService(HttpURLConnection connection) {
        initialize(connection, null);
    }

    /**
     * Creates instance of AbstractDownloadService.
     * 
     * <p>
     * Used by some implementations.
     * <p>
     * 
     * @see com.autoupdater.client.download.services.FileDownloadService
     * 
     * @param connection
     *            connection used for obtain data
     * @param fileDestinationPath
     *            path to file where result should be stored
     */
    public AbstractDownloadService(HttpURLConnection connection, String fileDestinationPath) {
        initialize(connection, fileDestinationPath);
    }

    /**
     * Starts download thread.
     */
    public synchronized void start() {
        downloadThread.start();
    }

    /**
     * Checks whether or not thread has already started.
     * 
     * @return whether or not thread has started
     */
    public boolean hasStarted() {
        return !downloadThread.getState().equals(State.NEW);
    }

    /**
     * Cancels download.
     */
    public void cancel() {
        downloadThread.interrupt();
    }

    /**
     * Obtains content length.
     * 
     * @see com.autoupdater.client.download.runnables.AbstractDownloadRunnable#getContentLength()
     * 
     * @return content's length in bytes if value available, -1 otherwise
     */
    public long getContentLength() {
        return runnable.getContentLength();
    }

    /**
     * Returns connection used by service.
     * 
     * @return connection used by service
     */
    public HttpURLConnection getConnection() {
        return connection;
    }

    /**
     * Returns file destination path if available.
     * 
     * @return file destination path if available, null otherwise
     */
    public String getFileDestinationPath() {
        return fileDestinationPath;
    }

    /**
     * Obtains result of download if available.
     * 
     * @return result of download
     * @throws DownloadResultException
     *             thrown if download is incorrect state, in particular, if it
     *             wasn't finished or was cancelled
     */
    public Result getResult() throws DownloadResultException {
        return runnable.getResult();
    }

    /**
     * Returns state of download.
     * 
     * @see com.autoupdater.client.download.EDownloadStatus
     * 
     * @return state of download
     */
    public EDownloadStatus getState() {
        return runnable.getState();
    }

    /**
     * Makes current thread wait for download thread to finish.
     * 
     * @throws InterruptedException
     *             thrown if thread was interrupted, e.g. when it was cancelled
     */
    public void joinThread() throws InterruptedException {
        downloadThread.join();
    }

    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        if (Objects.equal(observable, runnable)) {
            hasChanged();
            notifyObservers(message);
        }
    }

    /**
     * Returns runnable instance. Used for object initialization.
     * 
     * @return DownloadService instance
     */
    protected abstract AbstractDownloadRunnable<Result> getRunnable();

    /**
     * Initializes DownloadService.
     * 
     * <p>
     * Used by constructors.
     * </p>
     * 
     * @param connection
     * @param fileDestinationPath
     */
    private void initialize(HttpURLConnection connection, String fileDestinationPath) {
        this.connection = connection;
        this.fileDestinationPath = fileDestinationPath;
        runnable = getRunnable();
        runnable.addObserver(this);
        downloadThread = new Thread(runnable);
    }
}
