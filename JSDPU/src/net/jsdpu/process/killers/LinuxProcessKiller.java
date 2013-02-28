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
 * Implementation of ProcessKillerInterface used for killing process in Linux
 * family systems.
 * 
 * @see net.jsdpu.process.killers.IProcessKiller
 */
public class LinuxProcessKiller extends AbstractProcessKiller {
    private static final Logger logger = getLogger(LinuxProcessKiller.class);

    @Override
    public void killProcess(String programName) throws IOException, InterruptedException,
            ProcessKillerException {
        for (String pid : getPID(programName))
            super.killProcess(pid);
    }

    @Override
    protected boolean askToDieGracefully(String pid) throws IOException, InterruptedException {
        logger.detailedTrace("Attempt to gracefully kill " + pid);
        return new ProcessBuilder("kill", "-TERM", pid).start().waitFor() == 0;
    }

    @Override
    protected void killAllResistants(String pid) throws IOException, InterruptedException {
        logger.detailedTrace("Attempt to forcefully kill " + pid);
        Process process = new ProcessBuilder("kill", pid).start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        int errorCode = process.waitFor();

        if (errorCode != 0) {
            String message = reader.readLine();
            logger.error("Failed to forcefully kill " + pid + ":" + message);
        }
    }

    @Override
    protected boolean isProgramRunning(String programName) throws IOException, InterruptedException {
        logger.detailedTrace("Obtaining information about running instances of " + programName);
        Process process = new ProcessBuilder("ps", "-ef").start();

        BufferedReader outputReader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));

        process.waitFor();

        String outputMessage;
        while ((outputMessage = outputReader.readLine()) != null) {
            if (outputMessage.contains(programName))
                return true;
        }

        return false;
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
        Process process = new ProcessBuilder(new String[] { "ps", "-ef" }).start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        List<String> pids = new ArrayList<String>();

        Pattern pattern = compile("^\\S+\\s+(\\d+).+" + quote(programName));

        String result;
        while ((result = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(result);
            if (matcher.find())
                pids.add(matcher.group(1));
        }

        return pids;
    }
}
