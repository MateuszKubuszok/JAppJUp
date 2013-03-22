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

import static net.jsdpu.logger.Level.parse;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Formatter used for displaying log by Logger.
 */
public class PrettyFormatter extends Formatter {
    private final DateFormat formatter;
    private String lastClass;
    private final Map<String, String> lastMethodForClass;

    /**
     * Initiates formatter.
     */
    public PrettyFormatter() {
        formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS z");
        lastClass = null;
        lastMethodForClass = new HashMap<String, String>();
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        Level level = parse(record.getLevel());
        String className = record.getSourceClassName();
        String methodName = record.getSourceMethodName();
        Date date = new Date(record.getMillis());
        String message = record.getMessage();
        Throwable throwable = record.getThrown();

        appendClass(builder, className);
        appendMethod(builder, className, methodName);
        appendMessage(builder, level, date, message);
        appendException(builder, throwable);

        lastClass = className;
        lastMethodForClass.put(className, methodName);

        return builder.toString();
    }

    /**
     * Adds class to log.
     * 
     * @param builder StringBuilder
     * @param className class to add
     */
    private void appendClass(StringBuilder builder, String className) {
        if (lastClass == null || !lastClass.equals(className))
            builder.append(className).append(":\n");
    }

    /**
     * Adds method to log.
     * 
     * @param builder StringBuilder
     * @param className class name
     * @param methodName method to add
     */
    private void appendMethod(StringBuilder builder, String className, String methodName) {
        if (lastClass == null || !lastClass.equals(className)
                || !lastMethodForClass.containsKey(className)
                || !lastMethodForClass.get(className).equals(methodName)) {
            builder.append('\t').append(methodName).append('\n');
        }
    }

    /**
     * Adds message to log.
     * 
     * @param builder StringBuilder
     * @param level log level
     * @param date current date
     * @param message message content
     */
    private void appendMessage(StringBuilder builder, Level level, Date date, String message) {
        builder.append("\t\t[").append(level).append("] ").append(formatter.format(date))
                .append(":\n");
        builder.append("\t\t").append(message).append('\n');
    }

    /**
     * Adds exception to log.
     * 
     * @param builder StringBuilder
     * @param throwable exception to add
     */
    private void appendException(StringBuilder builder, Throwable throwable) {
        if (throwable != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(bos);
            throwable.printStackTrace(pw);

            for (String s : new String(bos.toByteArray()).split("\n"))
                builder.append("\t\t").append(s).append('\n');
        }
    }
}
