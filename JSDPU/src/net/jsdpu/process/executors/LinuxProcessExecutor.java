package net.jsdpu.process.executors;

import static net.jsdpu.process.executors.Commands.convertSingleCommand;
import static net.jsdpu.process.executors.MultiCaller.prepareCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AbstractProcessExecutor for Linux family systems.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class LinuxProcessExecutor extends AbstractProcessExecutor {
    @Override
    protected List<String[]> rootCommand(List<String[]> commands) {
        List<String> command = new ArrayList<String>();
        command.add("pkexec");
        command.addAll(prepareCommand(commands));
        return convertSingleCommand(command);
    }
}
