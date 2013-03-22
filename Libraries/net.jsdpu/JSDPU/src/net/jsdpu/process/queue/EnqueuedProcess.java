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
package net.jsdpu.process.queue;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Representation of enqueued process. Since it isn't represented as an actual
 * Process in current VM, but exists in remotely run child VM, it has to be
 * controlled via wrapper.
 */
public interface EnqueuedProcess {
    /**
     * @see java.lang.Process#exitValue()
     */
    @SuppressWarnings("javadoc")
    public int getExitValue();

    /**
     * @see java.lang.Process#getInputStream()
     */
    @SuppressWarnings("javadoc")
    public InputStream getInputStream();

    /**
     * @see java.lang.Process#getErrorStream()
     */
    @SuppressWarnings("javadoc")
    public InputStream getErrorStream();

    /**
     * @see java.lang.Process#getOutputStream()
     */
    @SuppressWarnings("javadoc")
    public OutputStream getOutputStream();

    /**
     * @see java.lang.Process#waitFor()
     */
    @SuppressWarnings("javadoc")
    public int waitFor();
}
