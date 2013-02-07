package net.jsdpu.process.executors;

import static java.util.Arrays.asList;
import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.process.executors.Commands.convertSingleCommand;
import static net.jsdpu.process.executors.MultiCaller.prepareCommand;

import java.util.ArrayList;
import java.util.List;

import net.jsdpu.logger.Logger;

/**
 * Implementation of AbstractProcessExecutor for Linux family systems.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class LinuxProcessExecutor extends AbstractProcessExecutor {
    private final static Logger logger = getLogger(LinuxProcessExecutor.class);

    @Override
    protected List<String[]> rootCommand(List<String[]> commands) {
        logger.trace("Preparing root command for: " + commands);
        List<String> command = new ArrayList<String>();
        command.add("pkexec");
        command.addAll(asList(prepareCommand(commands)));
        logger.detailedTrace("Root command: " + command);
        return convertSingleCommand(command);
    }
}
