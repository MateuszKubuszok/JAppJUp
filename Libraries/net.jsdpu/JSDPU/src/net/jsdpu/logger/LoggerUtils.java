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
package net.jsdpu.logger;

import java.util.Arrays;
import java.util.List;

/**
 * Contains methods used during logging.
 */
public class LoggerUtils {
    /**
     * Static class.
     */
    private LoggerUtils() {
    }

    /**
     * Returns String representing array of Strings.
     * 
     * @param array
     *            array of Strings
     * @return String
     */
    public static String arrayToString(String[] array) {
        return Arrays.toString(array);
    }

    /**
     * Returns String representing list of arrays of Strings.
     * 
     * @param list
     *            list of arrays of Strings
     * @return String
     */
    public static String listToString(List<String[]> list) {
        StringBuilder builder = new StringBuilder().append("{ ");
        if (list.size() > 0)
            builder.append(arrayToString(list.get(0)));
        for (int i = 1; i < list.size(); i++)
            builder.append(", ").append(list.get(i));
        return builder.append(" }").toString();
    }
}
