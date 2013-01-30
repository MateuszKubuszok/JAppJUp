package net.jsdpu.process.executors;

import static java.lang.Integer.valueOf;
import static java.lang.System.getProperty;
import static net.jsdpu.process.executors.Commands.convertSingleCommand;
import static net.jsdpu.process.executors.Commands.joinArguments;
import static net.jsdpu.process.executors.Commands.secureSingleCommand;
import static net.jsdpu.process.executors.Commands.wrapArgument;
import static net.jsdpu.resources.Resources.getUACHandlerPath;
import static net.jsdpu.resources.Resources.uninstallWindowsWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AbstractProcessExecutor for Windows family systems.
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class WindowsProcessExecutor extends AbstractProcessExecutor {
    /**
     * Lowest major version of Windows that require use of UAC handling.
     * 
     * <p>
     * Vista - first to use it was 6.0, 7 was 6.1 - thus checking whether major
     * version is greater or equal to 6 is sufficient.
     * </p>
     */
    private final int windowsVistaMajorVersion = 6;

    @Override
    protected List<String[]> rootCommand(List<String[]> commands) {
        if (isVistaOrLater()) {
            String uacHandlerPath = getUACHandlerPath();
            List<String> command = new ArrayList<String>();
            command.add(uacHandlerPath);
            for (String[] subCommand : commands)
                command.add(wrapArgument(joinArguments(secureSingleCommand(subCommand))));
            return convertSingleCommand(command);
        }
        // Windows systems prior to Vista didn't require process elevation
        return commands;
    }

    /**
     * Returns whether current system is Windows Vista or newer.
     * 
     * @return true is Vista or later, false otherwise
     */
    public boolean isVistaOrLater() {
        String major = getProperty("os.version").split("\\.")[0];
        try {
            return valueOf(major) >= windowsVistaMajorVersion || true;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    @Override
    protected void finalize() {
        uninstallWindowsWrapper();
    }
}
