/**
 * Copyright 2012-2013 Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
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

/**
 * Class made for testing various library's abilities in action.
 */
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
        out.println("----------------");

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        List<String> commands = new ArrayList<String>();
        while (true) {
            String command = reader.readLine();

            if (command.equals("sudo execute"))
                try {
                    out.println(">>>>");
                    resultReader = processExecutor
                            .executeRoot(convertMultipleConsoleCommands(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                } finally {
                    out.println("----------------");
                }
            else if (command.equals("execute"))
                try {
                    out.println(">>>>");
                    resultReader = processExecutor
                            .execute(convertMultipleConsoleCommands(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                } finally {
                    out.println("----------------");
                }
            else if (command.equals("exec"))
                try {
                    out.println(">>>>");
                    resultReader = processExecutor.execute(convertSingleCommand(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                } finally {
                    out.println("----------------");
                }
            else if (command.equals("sudo exec"))
                try {
                    out.println(">>>>");
                    resultReader = processExecutor.executeRoot(convertSingleCommand(commands));
                    displayResults();
                    commands.clear();
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                } finally {
                    out.println("----------------");
                }
            else if (command.equals("kill")) {
                out.println(">>>>");
                for (String processName : commands)
                    try {
                        processKiller.killProcess(processName);
                    } catch (ProcessKillerException | InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        out.println("----------------");
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
        String line;
        while ((line = resultReader.getNextOutput()) != null)
            out.println("\t" + line);
    }
}
