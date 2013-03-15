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
package com.autoupdater.server.utils.cache;

/**
 * Interface for Cache source objects.
 * 
 * Allows obtain from source output for passed input.
 * 
 * @param <I>
 *            type of input data
 * @param <O>
 *            type of output data
 */
public interface CacheSource<I extends Comparable<I>, O> {
    /**
     * Returns output data for passed input data.
     * 
     * @param input
     *            input data
     * @return output data
     */
    public O getElement(I input);
}
