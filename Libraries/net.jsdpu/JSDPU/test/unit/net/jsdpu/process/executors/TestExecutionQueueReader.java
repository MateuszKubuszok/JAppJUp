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
