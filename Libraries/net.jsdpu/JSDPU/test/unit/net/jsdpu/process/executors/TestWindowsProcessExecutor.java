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

import static java.util.Arrays.asList;
import static net.jsdpu.process.executors.Commands.convertSingleCommand;
import static net.jsdpu.process.executors.MultiCaller.prepareCommand;
import static net.jsdpu.resources.Resources.getUACHandlerPath;
import static org.fest.assertions.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestWindowsProcessExecutor {
    @Test
    public void testRootCommand() {
        try {
            // given
            WindowsProcessExecutor executor = new WindowsProcessExecutor();
            List<String[]> command = new ArrayList<String[]>();
            command.add((String[]) asList("java", "-jar", "Some Installer.jar").toArray());
            Method rootCommand = executor.getClass().getDeclaredMethod("rootCommand", List.class);
            rootCommand.setAccessible(true);

            // when
            List<String[]> result = (List<String[]>) rootCommand.invoke(executor, command);

            // then
            assertThat(result).as("rootCommand() should return root command").isNotNull()
                    .hasSize(1);
            assertThat(result.get(0)).as("rootCommand() should return correct root command")
                    .isNotNull().isEqualTo(rootCommand(command));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            fail("No exception should be thrown");
        }
    }

    private String[] rootCommand(List<String[]> commands) {
        List<String> command = new ArrayList<String>();
        command.add(getUACHandlerPath());
        command.addAll(asList(prepareCommand(commands)));
        return convertSingleCommand(command).get(0);
    }
}
