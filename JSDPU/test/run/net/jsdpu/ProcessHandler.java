package net.jsdpu;

import static java.lang.System.*;
import static net.jsdpu.process.executors.Commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.IProcessExecutor;
import net.jsdpu.process.executors.InvalidCommandException;
import net.jsdpu.process.killers.IProcessKiller;
import net.jsdpu.process.killers.ProcessKillerException;

public class ProcessHandler {
    final static EOperatingSystem operatingSystem = EOperatingSystem.current();
    final static IProcessExecutor processExecutor = operatingSystem.getProcessExecutor();
    final static IProcessKiller processKiller = operatingSystem.getProcessKiller();
    private static ExecutionQueueReader resultReader;

    public static void main(String[] args) throws IOException {
        out.println("For testing ProcessExecutor type:");
        out.println("[prog arg1 arg2...][enter][prog2 arg1 arg2...][enter]...[execute][enter]");
        out.println("\t - to execute program(s) with given arguments");
        out.println("[prog arg1 arg2...][enter][prog2 arg1 arg2...][enter]...[sudo execute][enter]");
        out.println("\t - to execute program(s) with given arguments and elevation");
        out.println("[program][enter][argu1][enter]...[exec][enter]");
        out.println("\t - to execute program with given arguments");
        out.println("[program][enter][argu1][enter]...[sudo exec][enter]");
        out.println("\t - to execute program with given arguments with elevation");
        out.println("For testing ProcessKiller type:");
        out.println("[process name][enter]...[kill][enter] - to kill process with given name");
        out.println("reset - to reset state");
        out.println("exit - to quit tester");

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        List<String> commands = new ArrayList<String>();
        while (true) {
            String command = reader.readLine();

            if (command.equals("sudo execute"))
                try {
                    resultReader = processExecutor
                            .executeRoot(convertMultipleConsoleCommands(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
            else if (command.equals("execute"))
                try {
                    resultReader = processExecutor
                            .execute(convertMultipleConsoleCommands(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
            else if (command.equals("exec"))
                try {
                    resultReader = processExecutor.execute(convertSingleCommand(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
            else if (command.equals("sudo exec"))
                try {
                    resultReader = processExecutor.executeRoot(convertSingleCommand(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
            else if (command.equals("kill")) {
                for (String processName : commands)
                    try {
                        processKiller.killProcess(processName);
                    } catch (ProcessKillerException | InterruptedException e) {
                        e.printStackTrace();
                    }
                out.println("----------------");
            } else if (command.equals("reset")) {
                commands.clear();
                out.println("----------------");
            } else if (command.equals("exit"))
                return;
            else
                commands.add(command);
        }
    }

    private static void displayResults() throws InvalidCommandException {
        try {
            String line;
            while ((line = resultReader.getNextOutput()) != null)
                out.println("\t" + line);
        } finally {
            out.println("----------------");
        }
    }
}