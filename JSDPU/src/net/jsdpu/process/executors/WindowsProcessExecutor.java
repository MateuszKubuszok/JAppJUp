package net.jsdpu.process.executors;

import static net.jsdpu.process.executors.Commands.*;
import static net.jsdpu.resources.Resources.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AbstractProcessExecutor for Windows family systems.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class WindowsProcessExecutor extends AbstractProcessExecutor {
    @Override
    protected List<String[]> rootCommand(List<String[]> commands) {
        String uacHandlerPath = getUACHandlerPath();
        List<String> command = new ArrayList<String>();
        command.add(wrapArgument(uacHandlerPath));
        for (String[] subCommand : commands)
            command.add(wrapArgument(joinArguments(subCommand)));
        return convertSingleCommand(command);
    }

    @Override
    protected void finalize() {
        uninstallWindowsWrapper();
    }
}
