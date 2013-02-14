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
