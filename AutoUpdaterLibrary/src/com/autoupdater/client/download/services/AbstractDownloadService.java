package com.autoupdater.client.download.services;

import static java.lang.Thread.State.NEW;
import static net.jsdpu.logger.Logger.getLogger;

import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Set;

import net.jsdpu.logger.Logger;

import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.DownloadServiceProgressMessage;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.events.IDownloadListener;
import com.autoupdater.client.download.runnables.AbstractDownloadRunnable;
import com.autoupdater.client.utils.executions.ExecutionWithErrors;
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
        ObservableService<DownloadServiceMessage> implements IObserver<DownloadServiceMessage>,
        ExecutionWithErrors {
    private static final Logger logger = getLogger(AbstractDownloadService.class);

    private Set<IDownloadListener> listeners;

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
        this(connection, null);
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
        this.connection = connection;
        this.fileDestinationPath = fileDestinationPath;
        runnable = getRunnable();
        runnable.addObserver(this);
        listeners = new HashSet<IDownloadListener>();
        downloadThread = new Thread(runnable);
    }

    /**
     * Starts download thread.
     */
    public synchronized void start() {
        downloadThread.start();
        logger.debug("Ordered start of download from: " + getConnection().getURL());
    }

    /**
     * Checks whether or not thread has already started.
     * 
     * @return whether or not thread has started
     */
    public boolean hasStarted() {
        return !downloadThread.getState().equals(NEW);
    }

    /**
     * Cancels download.
     */
    public void cancel() {
        downloadThread.interrupt();
        logger.debug("Ordered cancellation of download from: " + getConnection().getURL());
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
     * Returns status of download.
     * 
     * @see com.autoupdater.client.download.EDownloadStatus
     * 
     * @return state of download
     */
    public EDownloadStatus getStatus() {
        return runnable.getStatus();
    }

    @Override
    public Throwable getThrownException() {
        return runnable.getThrownException();
    }

    @Override
    public void setThrownException(Throwable throwable) {
        runnable.setThrownException(throwable);
    }

    @Override
    public void throwExceptionIfErrorOccured() throws Throwable {
        runnable.throwExceptionIfErrorOccured();
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

    /**
     * Adds listener to set of subscribers.
     * 
     * @param listener
     *            download listener
     */
    public void addListener(IDownloadListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes listener from set of subscribers.
     * 
     * @param listener
     *            download listener
     */
    public void removeListener(IDownloadListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void update(ObservableService<DownloadServiceMessage> observable,
            DownloadServiceMessage message) {
        if (Objects.equal(observable, runnable)) {
            hasChanged();
            notifyObservers(message);
            notifyListeners(message);
        }
    }

    /**
     * Returns runnable instance. Used for object initialization.
     * 
     * @return DownloadService instance
     */
    protected abstract AbstractDownloadRunnable<Result> getRunnable();

    /**
     * Notifies all listeners about changes.
     * 
     * @param message
     *            message that should be processed for listeners
     */
    private void notifyListeners(DownloadServiceMessage message) {
        DownloadEventImpl event = null;
        if (message.getProgressMessage() != null) {
            DownloadServiceProgressMessage progressMessage = message.getProgressMessage();
            double progress = ((double) progressMessage.getCurrentAmount() / (double) progressMessage
                    .getOverallAmount());
            event = new DownloadEventImpl(this, message.getMessage(), progress);
        } else
            event = new DownloadEventImpl(this, message.getMessage());

        switch (getStatus()) {
        default:
        case HASNT_STARTED:
            break;
        case CONNECTED:
            for (IDownloadListener listener : listeners)
                listener.downloadStarted(event);
            break;
        case IN_PROCESS:
            for (IDownloadListener listener : listeners)
                listener.downloadStarted(event);
            break;
        case COMPLETED:
            for (IDownloadListener listener : listeners)
                listener.downloadCompleted(event);
            break;
        case PROCESSED:
            for (IDownloadListener listener : listeners) {
                listener.downloadProcessed(event);
                listener.downloadFinished(event);
            }
            break;
        case FAILED:
            for (IDownloadListener listener : listeners) {
                listener.downloadFailed(event);
                listener.downloadFinished(event);
            }
            break;
        case CANCELLED:
            for (IDownloadListener listener : listeners) {
                listener.downloadCancelled(event);
                listener.downloadFinished(event);
            }
            break;
        }
    }
}
