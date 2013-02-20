package com.autoupdater.client.utils.executions;

/**
 * Defines Runnable that also throws during run() call.
 */
public interface RunnableWithErrors extends Runnable, ExecutionWithErrors {
}
