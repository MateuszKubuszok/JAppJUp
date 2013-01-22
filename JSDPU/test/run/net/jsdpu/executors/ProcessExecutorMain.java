package net.jsdpu.executors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.jsdpu.EOperatingSystem;
import net.jsdpu.process.executors.Commands;
import net.jsdpu.process.executors.ExecutionQueueReader;
import net.jsdpu.process.executors.IProcessExecutor;
import net.jsdpu.process.executors.InvalidCommandException;

public class ProcessExecutorMain {
    final static EOperatingSystem operatingSystem = EOperatingSystem.current();
    final static IProcessExecutor processExecutor = operatingSystem.getProcessExecutor();
    private static ExecutionQueueReader resultReader;

    public static void main(String[] args) throws IOException {
        System.out.println("For testing ProcessExecutor type:");
        System.out.println("[prog arg1 arg2...][enter][prog2 arg1 arg2...][enter]...[exec][enter]");
        System.out.println("\t - to execute program(s) with given arguments");
        System.out.println("[program][enter][argu1][enter]...[execute][enter]");
        System.out.println("\t - to execute program with given arguments");
        System.out.println("[program][enter][argu1][enter]...[sudo execute][enter]");
        System.out.println("\t - to execute program with given arguments with elevation");
        System.out.println("exit - to quit tester");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        List<String> commands = new ArrayList<String>();
        while (true) {
            String command = reader.readLine();

            if (command.equals("exec")) {
                try {
                    resultReader = processExecutor.execute(Commands.convertSingleCommand(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
            } else if (command.equals("execute")) {
                try {
                    resultReader = processExecutor.execute(Commands.convertConsoleCommands(commands
                            .toArray(new String[0])));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
            } else if (command.equals("sudo execute")) {
                try {
                    resultReader = processExecutor.executeRoot(Commands
                            .convertConsoleCommands(commands.toArray(new String[0])));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
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
                System.out.println("\t" + line);
        } finally {
            System.out.println("----------------");
        }
    }
}
