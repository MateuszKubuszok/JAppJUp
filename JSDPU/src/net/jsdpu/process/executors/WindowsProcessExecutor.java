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
                command.add(joinArguments(subCommand));
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
        String major = System.getProperty("os.version").split("\\.")[0];
        try {
            return Integer.valueOf(major) >= windowsVistaMajorVersion;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void finalize() {
        uninstallWindowsWrapper();
    }
}
