package net.jsdpu.process.executors;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import net.jsdpu.process.executors.Commands;
import net.jsdpu.process.executors.InvalidCommandException;

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
            System.out.println("stahp");
            String[] commands = { "java -jar \"Some Installer.jar\"",
                    "\"some program\" \"Client.jar\"" };

            // when
            List<String[]> result = Commands.convertConsoleCommands(commands);

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
                .isEqualTo("\"some \\\"argument\\\"\" \"some other argument\"");
    }
}
