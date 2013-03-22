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
package net.jsdpu.process.independent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates process detached from JVM on Windows.
 */
public class WindowsIndependentProcessBuilder extends AbstractIndependentProcessBuilder {
    @Override
    public void start() throws IOException {
        List<String> windowsDetachedCommand = new ArrayList<String>();
        windowsDetachedCommand.add("cmd");
        windowsDetachedCommand.add("/k");
        windowsDetachedCommand.add("start");
        windowsDetachedCommand.add("");
        windowsDetachedCommand.addAll(getCommand());
        getBuilder().command(windowsDetachedCommand);
        getBuilder().start();
        // TODO: figure out how to made it work
    }
}
