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
package net.jsdpu.process.executors;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;

public class Mocks {
    private static AbstractProcessExecutor abstractProcessExecutor;
    private static List<String[]> mockCommands;
    private static List<String[]> mockRootCommands;

    public static AbstractProcessExecutor abstractProcessExecutor() {
        return (abstractProcessExecutor != null) ? abstractProcessExecutor
                : (new AbstractProcessExecutor() {
                    @Override
                    protected List<String[]> rootCommand(List<String[]> commands) {
                        return mockRootCommands();
                    }
                });
    }

    public static List<String[]> mockCommands() {
        return (mockCommands != null) ? mockCommands : Commands.convertSingleCommand("java",
                "-jar", "Installer.jar");
    }

    public static List<String[]> mockRootCommands() {
        return (mockRootCommands != null) ? mockRootCommands : Commands.convertSingleCommand(
                "sudo", "java", "-jar", "Installer.jar");
    }

    public static ProcessQueue processQueue(String... inputs) {
        List<String> queue = new ArrayList<String>(Arrays.asList(inputs));
        final List<Process> processList = new ArrayList<Process>();

        while (!queue.isEmpty()) {
            Process process = Mockito.mock(Process.class);
            Mockito.doReturn(new ByteArrayInputStream(queue.get(0).getBytes())).when(process)
                    .getInputStream();
            queue.remove(0);

            if (!queue.isEmpty()) {
                Mockito.doReturn(new ByteArrayInputStream(queue.get(0).getBytes())).when(process)
                        .getErrorStream();
                queue.remove(0);
            } else
                Mockito.doReturn(new ByteArrayInputStream(new byte[0])).when(process)
                        .getErrorStream();

            processList.add(process);
        }

        return new ProcessQueue() {
            @Override
            public Process getNextProcess() {
                if (processList.isEmpty())
                    return null;
                Process process = processList.get(0);
                processList.remove(0);
                return process;
            }

            @Override
            public boolean isEmpty() {
                return processList.isEmpty();
            }
        };
    }

    public static ExecutionQueueReader executionQueueReader(String... inputs) {
        return new ExecutionQueueReader(processQueue(inputs));
    }
}
