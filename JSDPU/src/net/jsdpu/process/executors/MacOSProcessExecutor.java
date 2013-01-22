package net.jsdpu.process.executors;

import java.util.List;

/**
 * Implementation of AbstractProcessExecutor for Mac OS family systems.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class MacOSProcessExecutor extends AbstractProcessExecutor {
    @Override
    protected List<String[]> rootCommand(List<String[]> commands) {
        throw new RuntimeException("MacOSProcessExecutor is not yet implemented!");
    }
}
