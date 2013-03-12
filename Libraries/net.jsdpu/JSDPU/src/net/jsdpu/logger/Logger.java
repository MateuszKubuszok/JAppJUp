package net.jsdpu.logger;

import static java.util.logging.Logger.getGlobal;
import static net.jsdpu.logger.Level.*;

import java.util.logging.Handler;

/**
 * java.util.logging.Logger Container for better readability.
 */
public class Logger {
    private static final java.util.logging.Logger topLogger = getGlobal();

    private final java.util.logging.Logger innerLogger;

    /**
     * Creates new logger for given class.
     * 
     * @param clazz
     *            class for which logger is created
     * @return logger
     */
    public static Logger getLogger(Class<?> clazz) {
        return new Logger(java.util.logging.Logger.getLogger(clazz.getName()));
    }

    /**
     * Adds handler to top handler.
     * 
     * @param handler
     *            handler to add
     */
    public static void addHandler(Handler handler) {
        topLogger.addHandler(handler);
    }

    /**
     * Sets logging level.
     * 
     * @param level
     *            severity level
     */
    public static void setLevel(Level level) {
        topLogger.setLevel(level.getOrignialLevel());
        for (Handler handler : topLogger.getHandlers())
            handler.setLevel(level.getOrignialLevel());
    }

    /**
     * Initiates logger.
     * 
     * @param innerLogger
     *            java.util.logging.Logger instance
     */
    private Logger(java.util.logging.Logger innerLogger) {
        this.innerLogger = innerLogger;
        innerLogger.setUseParentHandlers(true);
        innerLogger.setLevel(null);
    }

    /**
     * Log message with given level.
     * 
     * @param level
     *            severity level
     * @param message
     *            message to log
     */
    private void log(Level level, String message) {
        StackTraceElement ste = getStackTraceElement();
        if (ste != null)
            innerLogger.logp(level.getOrignialLevel(), ste.getClassName(), ste.getMethodName(),
                    message);
        else
            innerLogger.log(level.getOrignialLevel(), message);
    }

    /**
     * Log message with given level.
     * 
     * @param level
     *            severity level
     * @param message
     *            message to log
     * @param throwable
     *            throwable instance
     */
    private void log(Level level, String message, Throwable throwable) {
        StackTraceElement ste = getStackTraceElement();
        if (ste != null)
            innerLogger.logp(level.getOrignialLevel(), ste.getClassName(), ste.getMethodName(),
                    message, throwable);
        else
            innerLogger.log(level.getOrignialLevel(), message, throwable);
    }

    /**
     * Logs message on ERROR level.
     * 
     * @param message
     *            message to log
     */
    public void error(String message) {
        log(ERROR, message);
    }

    /**
     * Logs message on ERROR level.
     * 
     * @param message
     *            message to log
     * @param throwable
     *            throwable instance
     */
    public void error(String message, Throwable throwable) {
        log(ERROR, message, throwable);
    }

    /**
     * Logs message on WARNING level.
     * 
     * @param message
     *            message to log
     * @param throwable
     *            throwable instance
     */
    public void warning(String message, Throwable throwable) {
        log(WARNING, message, throwable);
    }

    /**
     * Logs message on WARNING level.
     * 
     * @param message
     *            message to log
     */
    public void warning(String message) {
        log(WARNING, message);
    }

    /**
     * Logs message on INFO level.
     * 
     * @param message
     *            message to log
     */
    public void info(String message) {
        log(INFO, message);
    }

    /**
     * Logs message on CONFIG level.
     * 
     * @param message
     *            message to log
     */
    public void config(String message) {
        log(CONFIG, message);
    }

    /**
     * Logs message on DEBUG level.
     * 
     * @param message
     *            message to log
     */
    public void debug(String message) {
        log(DEBUG, message);
    }

    /**
     * Logs message on TRACE level.
     * 
     * @param message
     *            message to log
     */
    public void trace(String message) {
        log(TRACE, message);
    }

    /**
     * Logs message on DETAILED_TRACE level.
     * 
     * @param message
     *            message to log
     */
    public void detailedTrace(String message) {
        log(DETAILED_TRACE, message);
    }

    private StackTraceElement getStackTraceElement() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (trace.length >= 5)
            return trace[4];
        return null;
    }
}
