package net.jsdpu.process.executors;

import static com.google.common.base.Strings.repeat;
import static java.util.regex.Matcher.quoteReplacement;
import static java.util.regex.Pattern.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static Joiner argJoiner = Joiner.on(" ");
    private static Pattern singleWrapped = compile("^\".*\"$");
    private static Pattern beginningOfGroup = compile("^\"");
    private static Pattern endOfGroup = compile("^(.*[^/])?\"$");
    private static Pattern escapePattern = compile("(" + quote("\\") + ")*" + quote("\""));
    private static String quoteReplacement = "?*:%";

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
        boolean isString = false;
        List<String> preparedResult = new ArrayList<String>();
        String tmp = null;

        for (String currentlyCheckedString : command.split(" ")) {
            if (isString) {
                tmp += " " + currentlyCheckedString;
                if (endOfGroup.matcher(currentlyCheckedString).find()) {
                    preparedResult.add(tmp.substring(0, tmp.length() - 1));
                    tmp = null;
                    isString = false;
                }
            } else {
                if (singleWrapped.matcher(currentlyCheckedString).find())
                    preparedResult.add(currentlyCheckedString.substring(1,
                            currentlyCheckedString.length() - 1));
                else if (beginningOfGroup.matcher(currentlyCheckedString).find()) {
                    tmp = currentlyCheckedString.substring(1);
                    isString = true;
                } else if (!currentlyCheckedString.isEmpty())
                    preparedResult.add(currentlyCheckedString);
            }
        }

        if (tmp != null)
            throw new InvalidCommandException("There is error in \"" + command + "\" command");

        return preparedResult.toArray(new String[0]);
    }

    /**
     * Converts commands in form passed into console to a form suitable for
     * execute methods.
     * 
     * @see #convertConsoleCommands(String...)
     * 
     * @param commands
     *            commands to convert
     * @return command in form suitable for execution
     * @throws InvalidCommandException
     *             thrown when there is error in shape of command
     */
    public static List<String[]> convertConsoleCommands(String... commands)
            throws InvalidCommandException {
        List<String[]> results = new ArrayList<String[]>();

        for (String command : commands)
            results.add(convertSingleConsoleCommand(command));

        return results;
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
        List<String[]> result = new ArrayList<String[]>();
        result.add(command);
        return result;
    }

    public static String escapeArgument(String argument) {
        String result = argument;
        Matcher matcher;
        while ((matcher = escapePattern.matcher(result)).find()) {
            int groupSize = matcher.group(1) != null ? matcher.group(1).length() : 0;
            int replacementSize = (groupSize + 1) * 2 - 1;
            String replacement = quoteReplacement(repeat("\\", replacementSize) + quoteReplacement);
            result = matcher.replaceAll(replacement);
        }
        return result.replace(quoteReplacement, "\"");
    }

    public static String[] escapeCommand(String[] command) {
        for (int i = 0; i < command.length; i++)
            command[i] = escapeArgument(command[i]);
        return command;
    }

    public static List<String[]> escapeCommands(List<String[]> commands) {
        for (String[] command : commands)
            escapeCommand(command);
        return commands;
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
        return "\"" + escapeArgument(argument) + "\"";
    }

    public static String[] wrapCommand(String[] command) {
        for (int i = 0; i < command.length; i++)
            command[i] = wrapArgument(command[i]);
        return command;
    }

    public static List<String[]> wrapCommands(List<String[]> commands) {
        for (String[] command : commands)
            wrapCommand(command);
        return commands;
    }

    /**
     * Wraps arguments and join them into one command.
     * 
     * @see #wrapArgument(String)
     * 
     * @param arguments
     *            arguments to join
     * @return arguments wrapped and joined into one string
     */
    public static String joinArguments(String[] arguments) {
        for (int i = 0; i < arguments.length; i++)
            arguments[i] = wrapArgument(arguments[i]);
        return argJoiner.join(arguments);
    }
}
