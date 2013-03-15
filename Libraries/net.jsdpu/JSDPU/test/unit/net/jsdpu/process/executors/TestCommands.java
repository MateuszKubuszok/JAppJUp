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

import static org.fest.assertions.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestCommands {
    @Test
    public void testConvertSingleConsoleCommand() {
        try {
            // given
            String command = "java -jar  \"Some Installer.jar\"";

            // when
            String[] result = Commands.convertSingleConsoleCommand(command);

            // then
            assertThat(result)
                    .as("convertSingleConsoleCommand(String[]) should properly split command")
                    .isNotNull().hasSize(3);
            assertThat(result[0])
                    .as("convertSingleConsoleCommand(String[]) should keep argument's value properly")
                    .isNotNull().isEqualTo("java");
            assertThat(result[1])
                    .as("convertSingleConsoleCommand(String[]) should keep argument's value properly")
                    .isNotNull().isEqualTo("-jar");
            assertThat(result[2])
                    .as("convertSingleConsoleCommand(String[]) should keep argument's value properly")
                    .isNotNull().isEqualTo("Some Installer.jar");
        } catch (InvalidCommandException e) {
            fail("No exception should be thrown, when command defined properly");
        }
    }

    @Test
    public void testConvertConsoleCommands() {
        try {
            // given
            String[] commands = { "java -jar \"Some Installer.jar\"",
                    "\"some program\" \"Client.jar\"" };

            // when
            List<String[]> result = Commands.convertMultipleConsoleCommands(commands);

            // then
            assertThat(result)
                    .as("convertConsoleCommands(List<String[]>) should make conversion for each command")
                    .isNotNull().hasSize(2);
            assertThat(result.get(0))
                    .as("convertConsoleCommands(List<String[]>) should convert each command properly")
                    .isNotNull().hasSize(3);
            assertThat(result.get(1))
                    .as("convertConsoleCommands(List<String[]>) should convert each command properly")
                    .isNotNull().hasSize(2);
        } catch (InvalidCommandException e) {
            fail("No exception should be thrown, when commands defined properly");
        }
    }

    @Test
    public void testConvertSingleCommandList() {
        // given
        List<String> command = new ArrayList<String>();
        command.add("java");
        command.add("-jar");
        command.add("Installer.jar");

        // when
        List<String[]> result = Commands.convertSingleCommand(command);

        // then
        assertThat(result)
                .as("convertSingleCommand(List<String[]>) should properly convert single command")
                .isNotNull().hasSize(1);
        assertThat(result.get(0))
                .as("convertSingleCommand(List<String[]>) should properly convert single command")
                .isNotNull().hasSize(3);
        assertThat(result.get(0)[0])
                .as("convertSingleCommand(List<String[]>) should keep values of arguments")
                .isNotNull().isEqualTo("java");
        assertThat(result.get(0)[1])
                .as("convertSingleCommand(List<String[]>) should keep values of arguments")
                .isNotNull().isEqualTo("-jar");
        assertThat(result.get(0)[2])
                .as("convertSingleCommand(List<String[]>) should keep values of arguments")
                .isNotNull().isEqualTo("Installer.jar");
    }

    @Test
    public void testConvertSingleCommandArray() {
        // given
        String[] command = { "java", "-jar", "Installer.jar" };

        // when
        List<String[]> result = Commands.convertSingleCommand(command);

        // then
        assertThat(result)
                .as("convertSingleCommand(List<String[]>) should properly convert single command")
                .isNotNull().hasSize(1);
        assertThat(result.get(0))
                .as("convertSingleCommand(List<String[]>) should properly convert single command")
                .isNotNull().hasSize(3);
        assertThat(result.get(0)[0])
                .as("convertSingleCommand(List<String[]>) should keep values of arguments")
                .isNotNull().isEqualTo("java");
        assertThat(result.get(0)[1])
                .as("convertSingleCommand(List<String[]>) should keep values of arguments")
                .isNotNull().isEqualTo("-jar");
        assertThat(result.get(0)[2]).isEqualTo("Installer.jar");
    }

    @Test
    public void wrapArgument() {
        // given
        String argument = "some \"argument\"";

        // when
        String result = Commands.wrapArgument(argument);

        // then
        assertThat(result).as("wrapArgument(String) should properly wrap argument").isNotNull()
                .isEqualTo("\"some \\\"argument\\\"\"");
    }

    @Test
    public void joinArguments() {
        // given
        String[] arguments = { "some \"argument\"", "some other argument" };

        // when
        String result = Commands.joinArguments(arguments);

        // then
        assertThat(result).as("joinArguments(String[]) should properly join arguments").isNotNull()
                .isEqualTo("some \"argument\" some other argument");
    }
}
