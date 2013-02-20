package com.autoupdater.client.utils.executions;

/**
 * Defines some kind of execution (e.g. service or Runnable) that might throw
 * Exceptions that cannot reach invoker in normal run.
 * 
 * <p>
 * Instead, after (or during) execution thrownException variable can be set, for
 * later obtaining of rethrowing.
 * </p>
 */
public interface ExecutionWithErrors {
    /**
     * Returns exception thrown during execution of installation.
     * 
     * @return Throwable if any exception thrown or null
     */
    public Throwable getThrownException();

    /**
     * 
     * @param throwable
     */
    void setThrownException(Throwable throwable);

    /**
     * Throws exception if any error occurred.
     * 
     * @throws Throwable
     *             thrown if exception occurred during run()
     */
    public void throwExceptionIfErrorOccured() throws Throwable;
}
