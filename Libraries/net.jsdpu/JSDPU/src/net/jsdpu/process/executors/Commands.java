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

import static com.google.common.base.Strings.repeat;
import static java.lang.Math.max;
import static java.util.regex.Pattern.*;
import static net.jsdpu.logger.Logger.getLogger;
import static net.jsdpu.logger.LoggerUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jsdpu.logger.Logger;

import com.google.common.base.Joiner;

/**
 * Generates commands suitable either for ProcessBuilders or
 * AbstractProcessExecutor.
 * 
 * <p>
 * By console command we understand format typed in console or terminals -
 * single string with arguments separated by spaces, where spaces inside of
 * strings in quotation marks don;t split string.
 * </p>
 * 
 * <p>
 * Normal commands are program and its arguments passed as list of Strings.
 * </p>
 * 
 * <p>
 * Target format is array of String representing each command (program with its
 * arguments), or list of those representations depending on use
 * (ExecutionQueueReader/ProcessBuilder).
 * </p>
 * 
 * @see net.jsdpu.process.executors.AbstractProcessExecutor
 */
public class Commands {
    private static final Logger logger = getLogger(Commands.class);

    /**
     * Joins arguments.
     */
    private static final Joiner argJoiner = Joiner.on(" ");

    /**
     * Quotation mark for Pattern.
     */
    private static final String qm = quote("\"");
    /**
     * Slash for Pattern.
     */
    private static final String s = quote("\\");

    /**
     * Starting and ending with quotation mark with no quotation mark
     * not-escaped in the middle.
     */
    private static final Pattern singleWrapped = compile("^" + qm + "(" + s + qm + "|[^" + qm
            + "])*" + qm + "$");
    /**
     * Only beginning with not-escaped quotation mark.
     */
    private static final Pattern beginningOfGroup = compile("^" + qm);
    /**
     * Ending with not-escaped quotation mark.
     */
    private static final Pattern endOfGroup = compile("^(.*[^" + s + "])?" + qm + "$");

    /**
     * All quotation marks with optional escape slash.
     */
    private static final Pattern escapePattern = compile("(" + s + ")*" + qm);
    /**
     * Temporally replaces quotation mark during escaping.
     */
    private static final String quoteReplacement = "?*:%";

    /**
     * Static class.
     */
    private Commands() {
    }

    /**
     * Converts single command in form passed into console to a form more
     * suitable for execute methods (after aggregation).
     * 
     * <p>
     * Command converted by this method is suitable for ProcessBuilder
     * constructor, as well as for being element of list passed into
     * AbstractProcessExecutor methods.
     * </p>
     * 
     * @param command
     *            command to convert
     * @return command in form suitable for execution
     * @throws InvalidCommandException
     *             thrown when there is error in shape of command
     */
    public static String[] convertSingleConsoleCommand(String command)
            throws InvalidCommandException {
        logger.trace("Converting single console command for ProcessBuilder: " + command);

        List<String> preparedResult = new ArrayList<String>();
        String tmp = null;

        for (String currentlyCheckedString : command.split(" ")) {
            if (tmp != null) {
                tmp += " " + currentlyCheckedString;
                if (endOfGroup.matcher(currentlyCheckedString).find()) {
                    preparedResult.add(tmp.substring(0, tmp.length() - 1));
                    tmp = null;
                }
            } else {
                if (singleWrapped.matcher(currentlyCheckedString).find())
                    preparedResult.add(currentlyCheckedString.substring(1,
                            currentlyCheckedString.length() - 1));
                else if (beginningOfGroup.matcher(currentlyCheckedString).find())
                    tmp = currentlyCheckedString.substring(1);
                else if (!currentlyCheckedString.isEmpty())
                    preparedResult.add(currentlyCheckedString);
            }
        }

        if (tmp != null) {
            logger.error("Failed to convert console command - command invalid (exception thrown)");
            throw new InvalidCommandException("There is error in \"" + command + "\" command");
        }

        logger.detailedTrace("\tConverted single console command: " + preparedResult);
        return preparedResult.toArray(new String[0]);
    }

    /**
     * Converts commands in form passed into console to a form suitable for
     * execute methods.
     * 
     * @see #convertSingleConsoleCommand(String)
     * 
     * @param commands
     *            commands to convert
     * @return command in form suitable for execution
     * @throws InvalidCommandException
     *             thrown when there is error in shape of command
     */
    public static List<String[]> convertMultipleConsoleCommands(String... commands)
            throws InvalidCommandException {
        logger.trace("Converting multiple console commands: " + arrayToString(commands));
        List<String[]> results = new ArrayList<String[]>();

        for (String command : commands)
            results.add(convertSingleConsoleCommand(command));

        logger.detailedTrace("Converted multiple console commands: " + listToString(results));
        return results;
    }

    /**
     * Converts commands in form passed into console to a form suitable for
     * execute methods.
     * 
     * @see #convertMultipleConsoleCommands(String...)
     * 
     * @param commands
     *            commands to convert
     * @return command in form suitable for execution
     * @throws InvalidCommandException
     *             thrown when there is error in shape of command
     */
    public static List<String[]> convertMultipleConsoleCommands(List<String> commands)
            throws InvalidCommandException {
        return convertMultipleConsoleCommands(commands.toArray(new String[0]));
    }

    /**
     * Converts list consisted of program's name and its arguments into commands
     * list.
     * 
     * <p>
     * While lists of Strings are easier to work on, array of Strings is
     * required format for ProcessBuilder. ExecutionQueueReader used lists of
     * those arrays to initiate multiple ProcessBuilders at once.
     * </p>
     * 
     * <p>
     * This method converts list of program name and its arguments into format
     * suitable for ExecutionQueueReader creation.
     * </p>
     * 
     * @param command
     *            single command as list
     * @return list of commands
     */
    public static List<String[]> convertSingleCommand(List<String> command) {
        return convertSingleCommand(command.toArray(new String[0]));
    }

    /**
     * Converts array consisted of program's name and its arguments into
     * commands list.
     * 
     * <p>
     * This method converts array of program name and its arguments into format
     * suitable for ExecutionQueueReader creation.
     * </p>
     * 
     * @param command
     *            single command as array
     * @return list of commands
     */
    public static List<String[]> convertSingleCommand(String... command) {
        logger.trace("Convert single command for ProcessBuilder: " + arrayToString(command));
        List<String[]> result = new ArrayList<String[]>();
        result.add(command);
        logger.detailedTrace("Converted single command: { " + arrayToString(command) + " }");
        return result;
    }

    /**
     * Escapes single argument.
     * 
     * @param argument
     *            argument to escape
     * @return escaped argument
     */
    public static String escapeArgument(String argument) {
        logger.trace("Escaping argument: " + argument);
        String result = argument;

        if (result.contains("\"")) {
            Matcher matcher = escapePattern.matcher(result);
            int longestFound = 0;
            while (matcher.find())
                if (matcher.group(1) != null)
                    longestFound = max(longestFound, matcher.group(1).length());

            for (int i = longestFound; i >= 0; i--) {
                int replacementSize = (i + 1) * 2 - 1;
                String original = repeat("\\", i) + "\"";
                String replacement = repeat("\\", replacementSize) + quoteReplacement;
                result = result.replace(original, replacement);
            }
            result = result.replace(quoteReplacement, "\"");
        }

        logger.detailedTrace("Escaped argument: " + argument);
        return result;
    }

    /**
     * Wraps argument in quotation marks.
     * 
     * <p>
     * If argument contains quotation marks, they will be escaped.
     * </p>
     * 
     * @param argument
     *            program's argument (program name)
     * @return argument wrapped in quotation mark
     */
    public static String wrapArgument(String argument) {
        logger.trace("Wrapping argument: " + argument);
        if (argument.contains(" ") && !singleWrapped.matcher(argument).find())
            argument = "\"" + escapeArgument(argument) + "\"";
        logger.detailedTrace("Wrapped argument: " + argument);
        return argument;
    }

    /**
     * Wraps parameters in quotation marks for one command.
     * 
     * <p>
     * If command contains quotation marks, they will be escaped.
     * </p>
     * 
     * @param command
     *            program's command (program name and arguments)
     * @return command wrapped in quotation mark
     */
    public static String[] secureSingleCommand(String... command) {
        logger.trace("Securing command for: " + arrayToString(command));
        String[] wrappedCommand = new String[command.length];
        for (int i = 0; i < command.length; i++)
            wrappedCommand[i] = wrapArgument(command[i]);
        logger.detailedTrace("Secured command: " + arrayToString(wrappedCommand));
        return wrappedCommand;
    }

    /**
     * Wraps parameters in quotation marks for multiple command.
     * 
     * <p>
     * If command contains quotation marks, they will be escaped.
     * </p>
     * 
     * @param commands
     *            program's command (program name and arguments)
     * @return command wrapped in quotation mark
     */
    public static List<String[]> secureMultipleCommands(List<String[]> commands) {
        logger.trace("Securing commands for: " + listToString(commands));
        List<String[]> wrappedCommands = new ArrayList<String[]>();
        for (String[] command : commands)
            wrappedCommands.add(secureSingleCommand(command));
        logger.detailedTrace("Secured commands for: " + listToString(wrappedCommands));
        return wrappedCommands;
    }

    /**
     * Join arguments into one command (doesn't secure them!).
     * 
     * @param arguments
     *            arguments to join
     * @return arguments joined into one string
     */
    public static String joinArguments(String... arguments) {
        return argJoiner.join(arguments);
    }
}
