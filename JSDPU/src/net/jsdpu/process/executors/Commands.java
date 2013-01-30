package net.jsdpu.process.executors;

import static com.google.common.base.Strings.repeat;
import static java.lang.Math.max;
import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

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

    /**
     * Quotation mark for Pattern.
     */
    private static String qm = quote("\"");
    /**
     * Slash for Pattern.
     */
    private static String s = quote("\\");

    /**
     * Starting and ending with quotation mark with no quotation mark
     * not-escaped in the middle.
     */
    private static Pattern singleWrapped = compile("^" + qm + "(" + s + qm + "|[^" + qm + "])*"
            + qm + "$");
    /**
     * Only beginning with not-escaped quotation mark.
     */
    private static Pattern beginningOfGroup = compile("^" + qm);
    /**
     * Ending with not-escaped quotation mark.
     */
    private static Pattern endOfGroup = compile("^(.*[^" + s + "])?" + qm + "$");
    /**
     * All quotation marks with optional escape slash.
     */
    private static Pattern escapePattern = compile("(" + s + ")*" + qm);
    /**
     * Temporally replaces quotation mark during escaping.
     */
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

        if (tmp != null)
            throw new InvalidCommandException("There is error in \"" + command + "\" command");

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
        List<String[]> results = new ArrayList<String[]>();

        for (String command : commands)
            results.add(convertSingleConsoleCommand(command));

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
        List<String[]> result = new ArrayList<String[]>();
        result.add(command);
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
        if (!argument.contains("\""))
            return argument;

        String result = argument;
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

        return result.replace(quoteReplacement, "\"");
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
        if (argument.contains(" ") && !singleWrapped.matcher(argument).find())
            return "\"" + escapeArgument(argument) + "\"";
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
        String[] wrappedCommand = new String[command.length];
        for (int i = 0; i < command.length; i++)
            wrappedCommand[i] = wrapArgument(command[i]);
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
        List<String[]> wrappedCommands = new ArrayList<String[]>();
        for (String[] command : commands)
            wrappedCommands.add(secureSingleCommand(command));
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
