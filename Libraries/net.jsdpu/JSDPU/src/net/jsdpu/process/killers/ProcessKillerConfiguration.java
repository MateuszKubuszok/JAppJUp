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
package net.jsdpu.process.killers;

/**
 * Contains settings used by ProcessKiller.
 */
public class ProcessKillerConfiguration {
    /**
     * How many attempts to do before ProcessKiller fails.
     */
    public static int HOW_MANY_ATTEMPTS_BEFORE_FAIL = 3;

    /**
     * Intervals between attempts to terminate process.
     */
    public static int HOW_MANY_SECONDS_BETWEEN_ATTEMPTS = 30;
}
