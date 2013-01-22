package net.jsdpu.process.killers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of ProcessKillerInterface used for killing process in Linux
 * family systems.
 * 
 * @see net.jsdpu.process.killers.IProcessKiller
 */
public class LinuxProcessKiller implements IProcessKiller {
    @Override
    public void killProcess(String programName) throws IOException, InterruptedException,
            ProcessKillerException {
        int attempts = 0;

        if (!isProgramRunning(programName))
            return;

        for (attempts = 0; attempts < ProcessKillerConfiguration.HOW_MANY_ATTEMPTS_BEFORE_FAIL; attempts++) {
            for (String pid : getPID(programName))
                if (!askToDieGracefully(pid)) {
                    killAllResistants(pid);
                }

            if (!isProgramRunning(programName))
                return;

            Thread.sleep(ProcessKillerConfiguration.HOW_MANY_SECONDS_BETWEEN_ATTEMPTS * 1000);
        }

        throw new ProcessKillerException("Couldn't kill process - "
                + ProcessKillerConfiguration.HOW_MANY_ATTEMPTS_BEFORE_FAIL + " attempts failed");
    }

    /**
     * Attempts to kill process "gracefully" - by sending TERM signal.
     * 
     * <p>
     * Should make program pop
     * "Do you want to save before exit?"/"Are you sure you want to quit?"
     * dialog and then finish. Otherwise program should just die.
     * </p>
     * 
     * @param pid
     *            ID of program that should be killed
     * @return true if succeed to kill process
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted, while waiting for system
     *             dependent process
     */
    private boolean askToDieGracefully(String pid) throws IOException, InterruptedException {
        return new ProcessBuilder("kill", "-TERM", pid).start().waitFor() == 0;
    }

    /**
     * Kills process forcefully, if attempt to kill it gracefully failed.
     * 
     * @param pid
     *            ID of program that should be killed
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted, while waiting for system
     *             dependent process
     * @throws ProcessKillerException
     */
    private void killAllResistants(String pid) throws IOException, InterruptedException,
            ProcessKillerException {
        Process process = new ProcessBuilder("kill", pid).start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        int errorCode = process.waitFor();

        if (errorCode != 0) {
            String message = reader.readLine();
            throw new ProcessKillerException(
                    message != null && message.length() > 7 ? message.substring(7, message.length())
                            : "Couldn't kill process \"" + pid + "\"");
        }
    }

    /**
     * Checks whether program with given name is currently executed.
     * 
     * @param programName
     *            program that should be checked
     * @return true if program is running
     * @throws IOException
     *             thrown when error occurs in system dependent process
     * @throws InterruptedException
     *             thrown when thread is interrupted, while waiting for system
     *             dependent process
     */
    private boolean isProgramRunning(String programName) throws IOException, InterruptedException {
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
        Process process = new ProcessBuilder(new String[] { "ps", "-ef" }).start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        List<String> pids = new ArrayList<String>();

        Pattern pattern = Pattern.compile("^\\S+\\s+(\\d+).+" + Pattern.quote(programName));

        String result;
        while ((result = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(result);
            if (matcher.find())
                pids.add(matcher.group(1));
        }

        return pids;
    }
}
