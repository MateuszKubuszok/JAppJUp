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
package com.autoupdater.client;

/**
 * Superclass of all exceptions thrown by Client library.
 */
public class AutoUpdaterClientException extends Exception {
    /**
     * Creates instance of AutoUpdaterClientException.
     * 
     * @param message
     *            message to be passed
     */
    public AutoUpdaterClientException(String message) {
        super(message);
    }

    /**
     * Wraps {@link Throwable#addSuppressed(Throwable)} allowing chaining.
     * 
     * @param throwable
     *            suppressed throwable
     * @param resultClass
     *            expected return type
     * @return itself
     */
    @SuppressWarnings("unchecked")
    public <T extends AutoUpdaterClientException> T addSuppresed(Throwable throwable,
            Class<T> resultClass) {
        addSuppressed(throwable);
        return (T) this;
    }
}
