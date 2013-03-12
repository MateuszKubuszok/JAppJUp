package net.jsdpu.process.killers;

/**
 * Contains settings used by ProcessKiller.
 */
public class ProcessKillerConfiguration {
    /**
     * How many attempts to do before ProcessKiller fails.
     */
    public static int HOW_MANY_ATTEMPTS_BEFORE_FAIL = 3;

    /**
     * Intervals between attempts to terminate process.
     */
    public static int HOW_MANY_SECONDS_BETWEEN_ATTEMPTS = 30;
}
