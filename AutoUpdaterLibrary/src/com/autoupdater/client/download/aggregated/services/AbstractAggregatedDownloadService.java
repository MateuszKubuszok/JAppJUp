package com.autoupdater.client.download.aggregated.services;

import static com.autoupdater.client.download.ConnectionConfiguration.DEFAULT_MAX_PARALLEL_DOWNLOADS_NUMBER;
import static java.lang.Thread.sleep;
import static net.jsdpu.logger.Logger.getLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.jsdpu.logger.Logger;

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
    private static final Logger logger = getLogger(AbstractAggregatedDownloadService.class);

    private EDownloadStatus state;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final List<Future<?>> queuedFutures;
    private boolean cancelled = false;

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
        this(DEFAULT_MAX_PARALLEL_DOWNLOADS_NUMBER);
    }

    /**
     * Creates new AbstractAggregatedDownloadService instance.
     * 
     * @param maxParallelDownloadsNumber
     *            defines maximal parallel downloads number
     */
    public AbstractAggregatedDownloadService(int maxParallelDownloadsNumber) {
        super();
        queuedFutures = new ArrayList<Future<?>>();
        state = EDownloadStatus.HASNT_STARTED;
        threadPoolExecutor = new ThreadPoolExecutor(maxParallelDownloadsNumber,
                maxParallelDownloadsNumber, 2L, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * Starts all services at once, and begins to listen when they are all
     * finished.
     */
    public void start() {
        logger.info("Starts download queue");

        if (getServices() != null && !getServices().isEmpty())
            for (final Service service : getServices())
                queuedFutures.add(threadPoolExecutor.submit(new ServiceRunnable(service)));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    threadPoolExecutor.shutdown();
                    setState(EDownloadStatus.IN_PROCESS);
                    threadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
                    if (cancelled) {
                        setState(EDownloadStatus.CANCELLED);
                        logger.warning("Queue download cancelled");
                    } else {
                        setState(EDownloadStatus.PROCESSED);
                        logger.info("Queue download finished");
                    }
                } catch (InterruptedException e) {
                    setState(EDownloadStatus.CANCELLED);
                    logger.warning("Queue download cancelled");
                }
            }
        }).start();
    }

    /**
     * Cancel all downloads.
     */
    public void cancel() {
        for (Future<?> future : queuedFutures)
            future.cancel(true);
        cancelled = true;
    }

    /**
     * Makes current thread wait for all services to finish.
     */
    public void joinThread() {
        try {
            while (!getState().isDownloadFinished())
                sleep(10);
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
     * @return result of a download (defined by implementation)
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

    /**
     * Runnable executing single download.
     */
    private class ServiceRunnable implements Runnable {
        private final Service service;

        public ServiceRunnable(Service service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.start();
            try {
                service.joinThread();
            } catch (InterruptedException e) {
            }
        }
    }
}
