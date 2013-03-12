package net.jsdpu.process.killers;

import static java.util.regex.Pattern.*;
import static net.jsdpu.logger.Logger.getLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        List<String> command = new ArrayList<String>();
        command.add("taskkill");
        for (String pid : getPID(programName)) {
            command.add("/PID");
            command.add(pid);
        }

        return new ProcessBuilder(command.toArray(new String[command.size()])).start().waitFor() == 0;
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

    /**
     * Obtains all ID of process that are running program with given name
     * 
     * @param programName
     *            name of program's to kill
     * @return list of process' IDs
     * @throws IOException
     *             thrown when thread is interrupted, while waiting for system
     *             dependent process
     */
    private List<String> getPID(String programName) throws IOException {
        logger.detailedTrace("Obtaining PIDs for " + programName);
        Process process = new ProcessBuilder("wmic", "Path", "win32_process", "Where",
                commandLike(programName), "Get", "Caption,", "ProcessId").start();

        BufferedReader outputReader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));

        List<String> pids = new ArrayList<String>();

        Pattern pattern = compile("^\\S+\\s+(\\d+).+" + quote(programName));

        String outputMessage;
        while ((outputMessage = outputReader.readLine()) != null)
            if (!outputMessage.isEmpty() && !outputMessage.startsWith("Caption")
                    && !outputMessage.startsWith("WMIC.exe")) {
                Matcher matcher = pattern.matcher(outputMessage);
                if (matcher.find())
                    pids.add(matcher.group(1));
            }

        return pids;
    }
}
