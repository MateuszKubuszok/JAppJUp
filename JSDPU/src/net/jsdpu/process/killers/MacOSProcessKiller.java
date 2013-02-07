package net.jsdpu.process.killers;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.IOException;

import net.jsdpu.logger.Logger;

/**
 * Implementation of ProcessKillerInterface used for killing process in Mac OS
 * family systems.
 * 
 * @see net.jsdpu.process.killers.IProcessKiller
 */
public class MacOSProcessKiller implements IProcessKiller {
    private static final Logger logger = getLogger(MacOSProcessKiller.class);

    /**
     * Not yet implemented!
     * 
     * @TODO write actual procedure
     */
    @Override
    public void killProcess(String programName) throws IOException, InterruptedException {
        logger.error("MacOSProcessKiller is not yet implemented! (exceptio thrown)");
        throw new RuntimeException("MacOSProcessKiller is not yet implemented!");
    }
}
