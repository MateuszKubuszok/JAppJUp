package net.jsdpu.process.executors;

import static net.jsdpu.logger.Logger.getLogger;

import java.util.List;

import net.jsdpu.logger.Logger;

/**
 * Implementation of AbstractProcessExecutor for Mac OS family systems.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class MacOSProcessExecutor extends AbstractProcessExecutor {
    private static final Logger logger = getLogger(MacOSProcessExecutor.class);

    @Override
    protected List<String[]> rootCommand(List<String[]> commands) {
        logger.error("MacOSProcessExecutor is not yet implemented! (exception thrown)");
        throw new RuntimeException("MacOSProcessExecutor is not yet implemented!");
    }
}
