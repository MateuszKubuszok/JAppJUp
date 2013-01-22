package com.autoupdater.client.download.aggregated.services;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.autoupdater.client.download.ConnectionConfiguration;
import com.autoupdater.client.download.DownloadResultException;
import com.autoupdater.client.download.DownloadServiceMessage;
import com.autoupdater.client.download.EDownloadStatus;
import com.autoupdater.client.download.aggregated.notifiers.AbstractAggregatedDownloadNotifier;
import com.autoupdater.client.download.services.AbstractDownloadService;
import com.autoupdater.client.utils.aggregated.services.AbstractAggregatedService;

/**
 * Superclass of all AggeregatedDownloadServices.
 * 
 * <p>
 * AggregatedDownloadService should use several DownloadServices, aggregate
 * their result and return it as finished "product" ready for use.
 * </p>
 * 
 * <p>
 * As extension of AbstractAggregatedService it creates Notifier, that can be
 * used for obtaining information about progress of processing all services.
 * </p>
 * 
 * @see com.autoupdater.client.utils.aggregated.services.AbstractAggregatedService
 * @see com.autoupdater.client.download.aggregated.services.PackagesInfoAggregatedDownloadService
 * @see com.autoupdater.client.download.aggregated.services.UpdateInfoAggregatedDownloadService
 * @see com.autoupdater.client.download.aggregated.services.ChangelogInfoAggregatedDownloadService
 * @see com.autoupdater.client.download.aggregated.services.FileAggregatedDownloadService
 * 
 * @param <Service>
 *            type of service that will be aggregated - should extend
 *            AbstractDownloadService&lt;Result&gt;
 * @param <Notifier>
 *            type of notifier that will be passing messages - should extend
 *            AbstractAggregatedDownloadNotifier&lt;AdditionalMessage&gt;
 * @param <Result>
 *            type of result returned by service
 * @param <AggregatedResult>
 *            type of result returned by aggregated service
 * @param <AdditionalMessage>
 *            additional message passed with service
 */
public abstract class AbstractAggregatedDownloadService<Service extends AbstractDownloadService<Result>, Notifier extends AbstractAggregatedDownloadNotifier<AdditionalMessage>, Result, AggregatedResult, AdditionalMessage>
        extends
        AbstractAggregatedService<Service, Notifier, DownloadServiceMessage, DownloadServiceMessage, AdditionalMessage> {
    private EDownloadStatus state;
    private final ThreadPoolExecutor threadPoolExecutor;

    /**
     * Creates new AbstractAggregatedDownloadService instance.
     * 
     * <p>
     * Will have maximal parallel downloads number set to
     * ConnectionConfiguration.DEFAULT_MAX_PARALLEL_DOWNLOADS_NUMBER.
     * </p>
     * 
     * @see com.autoupdater.client.download.ConnectionConfiguration#DEFAULT_MAX_PARALLEL_DOWNLOADS_NUMBER
     */
    public AbstractAggregatedDownloadService() {
        super();
        int maxParallelDownloadsNumber = ConnectionConfiguration.DEFAULT_MAX_PARALLEL_DOWNLOADS_NUMBER;
        state = EDownloadStatus.HASNT_STARTED;
        threadPoolExecutor = new ThreadPoolExecutor(maxParallelDownloadsNumber,
                maxParallelDownloadsNumber + 1, 2L, TimeUnit.HOURS,
                new LinkedBlockingQueue<Runnable>());
    }

    /**
     * Creates new AbstractAggregatedDownloadService instance.
     * 
     * @param maxParallelDownloadsNumber
     *            defines maximal parallel downloads number
     */
    public AbstractAggregatedDownloadService(int maxParallelDownloadsNumber) {
        super();
        state = EDownloadStatus.HASNT_STARTED;
        threadPoolExecutor = new ThreadPoolExecutor(maxParallelDownloadsNumber,
                maxParallelDownloadsNumber, 2L, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * Starts all services at once, and begins to listen when they are all
     * finished.
     */
    public void start() {
        if (getServices() != null && !getServices().isEmpty())
            for (final Service service : getServices()) {
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        service.start();
                        try {
                            service.joinThread();
                        } catch (InterruptedException e) {
                        }
                    }
                });
            }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    threadPoolExecutor.shutdown();
                    setState(EDownloadStatus.IN_PROCESS);
                    threadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
                    setState(EDownloadStatus.PROCESSED);
                } catch (InterruptedException e) {
                    setState(EDownloadStatus.CANCELLED);
                }
            }
        }).start();
    }

    /**
     * Makes current thread wait for all services to finish.
     */
    public void joinThread() {
        try {
            while (!getState().isDownloadFinished())
                Thread.sleep(10);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Returns current state of processing services.
     * 
     * @return state of processing services
     */
    public EDownloadStatus getState() {
        return state;
    }

    /**
     * Result of aggregated download.
     * 
     * <p>
     * Often makes some additional processing with result of each single
     * DownloadService, which makes it necessary to call after service is
     * finished.
     * </p>
     * 
     * <p>
     * Should be called only on finished service, otherwise throws
     * DownloadResultException.
     * </p>
     * 
     * @throws DownloadResultException
     *             thrown if Service is still running
     */
    public abstract AggregatedResult getResult() throws DownloadResultException;

    /**
     * Sets current state of processing services, and notifies observers about
     * it.
     * 
     * <p>
     * Notifies all observers about changed state.
     * </p>
     * 
     * @param state
     *            new service state
     */
    private void setState(EDownloadStatus state) {
        this.state = state;
        getNotifier().hasChanged();
        getNotifier()
                .notifyObservers(new DownloadServiceMessage(getNotifier(), state.getMessage()));
    }
}
