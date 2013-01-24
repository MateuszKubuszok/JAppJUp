package net.jsdpu.process.executors;

import static net.jsdpu.process.executors.Commands.secureSingleCommand;
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
            command.add(secureSingleCommand("java", "-jar", "Some Installer.jar"));
            Method rootCommand = WindowsProcessExecutor.class.getDeclaredMethod("rootCommand",
                    List.class);
            rootCommand.setAccessible(true);

            // when
            @SuppressWarnings("unchecked")
            List<String[]> result = (List<String[]>) rootCommand.invoke(executor, command);

            // then
            assertThat(result).as("rootCommand() should return root command").isNotNull()
                    .hasSize(1);
            assertThat(result.get(0))
                    .as("rootCommand() should return correct root command")
                    .isNotNull()
                    .isEqualTo(
                            executor.isVistaOrLater() ? new String[] { getUACHandlerPath(),
                                    "java -jar \"Some Installer.jar\"" } : command
                                    .toArray(new String[0]));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            fail("No exception should be thrown");
        }
    }
}
