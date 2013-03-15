/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
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
package com.autoupdater.client.utils.executions;

/**
 * Defines some kind of execution (e.g. service or Runnable) that might throw
 * Exceptions that cannot reach invoker in normal run.
 * 
 * <p>
 * Instead, after (or during) execution thrownException variable can be set, for
 * later obtaining of rethrowing.
 * </p>
 */
public interface ExecutionWithErrors {
    /**
     * Returns exception thrown during execution of installation.
     * 
     * @return Throwable if any exception thrown or null
     */
    public Throwable getThrownException();

    /**
     * Exception, that occurred during execution.
     * 
     * @param throwable
     *            Exception that occurred
     */
    void setThrownException(Throwable throwable);

    /**
     * Throws exception if any error occurred.
     * 
     * @throws Throwable
     *             thrown if exception occurred during run()
     */
    public void throwExceptionIfErrorOccured() throws Throwable;
}
