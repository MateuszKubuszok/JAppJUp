package net.jsdpu.process.executors;

import static net.jsdpu.process.executors.Commands.convertSingleCommand;
import static net.jsdpu.process.executors.MultiCaller.prepareCommand;
import static org.fest.assertions.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestLinuxProcessExecutor {
    @Test
    public void testRootCommand() {
        try {
            // given
            LinuxProcessExecutor executor = new LinuxProcessExecutor();
            List<String[]> command = convertSingleCommand("java", "-jar", "Some Installer.jar");
            Method rootCommand = LinuxProcessExecutor.class.getDeclaredMethod("rootCommand",
                    List.class);
            rootCommand.setAccessible(true);

            // when
            @SuppressWarnings("unchecked")
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
        command.add("pkexec");
        command.addAll(prepareCommand(commands));
        return convertSingleCommand(command).get(0);
    }
}
