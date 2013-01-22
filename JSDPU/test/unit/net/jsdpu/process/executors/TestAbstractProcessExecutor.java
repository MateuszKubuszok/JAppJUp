package net.jsdpu.process.executors;

import static org.fest.assertions.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

public class TestAbstractProcessExecutor {
    @Test
    public void testExecute() {
        try {
            // given
            AbstractProcessExecutor executor = Mocks.abstractProcessExecutor();
            List<String[]> commands = Mocks.mockCommands();

            // when
            ExecutionQueueReader reader = executor.execute(commands);
            List<ProcessBuilder> processBuilders = getProcessBuilders(reader);

            // then
            assertThat(reader).as(
                    "execute(List<String[]>) should create ExecutionQueueReader properly")
                    .isNotNull();
            assertThat(processBuilders)
                    .as("execute(List<String[]>) should initiate ExecutionQueueReader properly")
                    .isNotNull().hasSize(1);
        } catch (IOException | IllegalArgumentException | IllegalAccessException
                | NoSuchFieldException | SecurityException e) {
            fail("No exception should be thrown, when commands defined properly");
        }
    }

    @Test
    public void testExecuteRoot() {
        try {
            // given
            AbstractProcessExecutor executor = Mocks.abstractProcessExecutor();
            List<String[]> commands = Mocks.mockCommands();

            // when
            ExecutionQueueReader reader = executor.executeRoot(commands);
            List<ProcessBuilder> processBuilders = getProcessBuilders(reader);

            // then
            assertThat(reader).as(
                    "execute(String,List<String[]>) should create ExecutionQueueReader properly")
                    .isNotNull();
            assertThat(processBuilders)
                    .as("execute(String,List<String[]>) should initiate ExecutionQueueReader properly")
                    .isNotNull().hasSize(1);
        } catch (IOException | IllegalArgumentException | IllegalAccessException
                | NoSuchFieldException | SecurityException e) {
            fail("No exception should be thrown, when commands defined properly");
        }
    }

    @Test
    public void testExecuteChoice() {
        try {
            // given
            AbstractProcessExecutor executor = Mocks.abstractProcessExecutor();
            List<String[]> commands = Mocks.mockCommands();

            // when
            ExecutionQueueReader reader = executor.execute(commands, true);
            List<ProcessBuilder> processBuilders = getProcessBuilders(reader);

            // then
            assertThat(reader)
                    .as("execute(String,List<String[]>,boolean) should create ExecutionQueueReader properly")
                    .isNotNull();
            assertThat(processBuilders)
                    .as("execute(String,List<String[]>,boolean) should initiate ExecutionQueueReader properly")
                    .isNotNull().hasSize(1);
        } catch (IOException | IllegalArgumentException | IllegalAccessException
                | NoSuchFieldException | SecurityException e) {
            fail("No exception should be thrown, when commands defined properly");
        }
    }

    @Test
    public void testExecuteCommands() {
        try {
            // given
            AbstractProcessExecutor executor = Mocks.abstractProcessExecutor();
            List<String[]> commands = Mocks.mockCommands();
            Method executeCommands = AbstractProcessExecutor.class.getDeclaredMethod(
                    "executeCommands", List.class);
            executeCommands.setAccessible(true);

            // when
            ExecutionQueueReader reader = (ExecutionQueueReader) executeCommands.invoke(executor,
                    commands);
            List<ProcessBuilder> processBuilders = getProcessBuilders(reader);

            // then
            assertThat(reader)
                    .as("execute(String,List<String[]>,boolean) should create ExecutionQueueReader properly")
                    .isNotNull();
            assertThat(processBuilders)
                    .as("execute(String,List<String[]>,boolean) should initiate ExecutionQueueReader properly")
                    .isNotNull().hasSize(1);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                | SecurityException | InvocationTargetException | NoSuchMethodException e) {
            fail("No exception should be thrown, when commands defined properly");
        }
    }

    @SuppressWarnings("unchecked")
    private List<ProcessBuilder> getProcessBuilders(ExecutionQueueReader reader)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        Field processQueue = ExecutionQueueReader.class.getDeclaredField("processQueue");
        processQueue.setAccessible(true);
        ProcessQueue queue = (ProcessQueue) processQueue.get(reader);
        Field processBuilders = ProcessQueue.class.getDeclaredField("processBuilders");
        processBuilders.setAccessible(true);
        return (List<ProcessBuilder>) processBuilders.get(queue);
    }
}
