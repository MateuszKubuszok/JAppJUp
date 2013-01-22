package net.jsdpu.process.executors;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

public class TestExecutionQueueReader {
    @Test
    public void testGetNextOutput() {
        try {
            // given
            ProcessQueue processQueue = Mocks.processQueue("line1\n\nline2", "error1\nerror2",
                    "\nline3\nline4");
            ExecutionQueueReader reader = new ExecutionQueueReader(processQueue);

            // when
            String output1 = reader.getNextOutput();
            String output2 = reader.getNextOutput();
            String output3 = reader.getNextOutput();
            String output4 = reader.getNextOutput();
            String output5 = reader.getNextOutput();
            String output6 = reader.getNextOutput();
            String output7 = reader.getNextOutput();

            // then
            assertThat(output1).as("getNextOutput() should return correct values in correct order")
                    .isNotNull().isEqualTo("line1");
            assertThat(output2).as("getNextOutput() should return correct values in correct order")
                    .isNotNull().isEqualTo("line2");
            assertThat(output3).as("getNextOutput() should return correct values in correct order")
                    .isNotNull().isEqualTo("error1");
            assertThat(output4).as("getNextOutput() should return correct values in correct order")
                    .isNotNull().isEqualTo("error2");
            assertThat(output5).as("getNextOutput() should return correct values in correct order")
                    .isNotNull().isEqualTo("line3");
            assertThat(output6).as("getNextOutput() should return correct values in correct order")
                    .isNotNull().isEqualTo("line4");
            assertThat(output7).as("getNextOutput() should return correct values in correct order")
                    .isNull();
        } catch (InvalidCommandException e) {
            fail("Not exception should be thrown");
        }
    }

    @Test
    public void testRewind() {
        try {
            // given
            ProcessQueue processQueue = Mocks.processQueue("line1\n\nline2", "error1\nerror2",
                    "\nline3\nline4");
            ExecutionQueueReader reader = new ExecutionQueueReader(processQueue);

            // when
            reader.rewind();
        } catch (InvalidCommandException e) {
            fail("Not exception should be thrown");
        }
    }
}
