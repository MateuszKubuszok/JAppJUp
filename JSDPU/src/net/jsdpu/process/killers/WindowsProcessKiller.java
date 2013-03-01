package net.jsdpu.process.killers;

import static net.jsdpu.logger.Logger.getLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.jsdpu.logger.Logger;

/**
 * Implementation of ProcessKillerInterface used for killing process in Windows
 * family systems.
 * 
 * @see net.jsdpu.process.killers.IProcessKiller
 */
public class WindowsProcessKiller extends AbstractProcessKiller {
    private static final Logger logger = getLogger(WindowsProcessKiller.class);

    @Override
    protected boolean askToDieGracefully(String programName) throws IOException,
            InterruptedException {
        logger.detailedTrace("Attempt to gracefully kill " + programName);
        return new ProcessBuilder("wmic", "Path", "win32_process", "Where",
                commandLike(programName), "Call", "Terminate").start().waitFor() == 0;
    }

    @Override
    protected void killAllResistants(String programName) throws IOException, InterruptedException {
        logger.detailedTrace("Attempt to forcefully kill " + programName);
        Process process = new ProcessBuilder("wmic", "Path", "win32_process", "Where",
                commandLike(programName), "Call", "Terminate").start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        int errorCode = process.waitFor();

        if (errorCode != 0) {
            String message = reader.readLine();
            logger.error("Failed to forcefully kill " + programName + ": " + message);
        }
    }

    @Override
    protected boolean isProgramRunning(String programName) throws IOException, InterruptedException {
        logger.detailedTrace("Obtaining information about running instances of " + programName);

        Process process = new ProcessBuilder("wmic", "Path", "win32_process", "Where",
                commandLike(programName)).start();

        BufferedReader outputReader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));

        String outputMessage;
        while ((outputMessage = outputReader.readLine()) != null) {
            if (!outputMessage.isEmpty() && !outputMessage.startsWith("Caption")
                    && !outputMessage.startsWith("WMIC.exe"))
                return true;
        }

        return false;
    }

    /**
     * Creates "CommandLine Like '%programName%'" argument.
     * 
     * @param programName
     *            program name to wrap
     * @return argument for WMIC
     */
    private String commandLike(String programName) {
        if (programName.endsWith(".jar"))
            return "CommandLine Like '%-jar " + programName + "%'";
        return "CommandLine Like '%" + programName + "%'";
    }
}
