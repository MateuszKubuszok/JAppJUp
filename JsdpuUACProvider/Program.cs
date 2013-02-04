using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.IO.Pipes;
using System.Threading;
using System.Text.RegularExpressions;

/// <summary>
/// Performs commands passed from UACHandler with elevation..
/// </summary>
/// 
/// Returned code is a result of the last executed command.
namespace UACPerformer {
    class Executor {
        /// <summary>
        /// Identifier used for communication with UACPerformer via NamedPipes. 
        /// </summary>
        private static string identifier;

        /// <summary>
        /// Pipe to send output from process.
        /// </summary>
        private static NamedPipeClientStream outClient;
        /// <summary>
        /// Pipe to send error from process.
        /// </summary>
        private static NamedPipeClientStream errClient;
        /// <summary>
        /// Writer to send output from process.
        /// </summary>
        private static StreamWriter outWriter;
        /// <summary>
        /// Writer to send error from process.
        /// </summary>
        private static StreamWriter errWriter;

        /// <summary>
        /// Settings of a current process.
        /// </summary>
        private static ProcessStartInfo psInfo;
        /// <summary>
        /// Currently run process .
        /// </summary>
        private static Process currentProcess;
        /// <summary>
        /// Locks output (output not read).
        /// </summary>
        private static bool outRead;
        /// <summary>
        /// Locks error (output not read).
        /// </summary>
        private static bool errRead;

        /// <summary>
        /// Runs passed commands with elevation and redirects results on standard Out and standard Error.
        /// </summary>
        /// 
        /// Results will be displayed for for each program sequentially: out1, err1, out2, err2...
        /// 
        /// <param name="commands">
        /// Array of commands where each of them is a string containing command in the same form it would
        /// have if run on console directly (program name and arguments separated by spaces).
        /// 
        /// The first string though is identifier used for communication with UACHandler.exe.
        /// </param>
        static void Main(string[] commands) {
            if (commands.Length == 0)
                return;

            identifier = commands[0];
            outClient = new NamedPipeClientStream(".", identifier + ".out", PipeDirection.Out);
            outClient.Connect();
            outWriter = new StreamWriter(outClient);
            errClient = new NamedPipeClientStream(".", identifier + ".err", PipeDirection.Out);
            errClient.Connect();
            errWriter = new StreamWriter(errClient);

            for (int i = 1; i < commands.Length; i++)
                parseCommand(commands[i]);

            outWriter.Close();
            outClient.Close();
            errWriter.Close();
            errClient.Close();
        }

        /// <summary>
        /// Prepares command to run and executes it.
        /// </summary>
        /// <param name="command">
        /// command passed from Main(string[])
        /// </param>
        private static void parseCommand(String command) {
            ArgumentsHandler argumentsHandler = new ArgumentsHandler(command);

            if (argumentsHandler.ParseArguments())
                runCommand(argumentsHandler);
        }

        /// <summary>
        /// Executes command.
        /// </summary>
        /// <param name="argumentsHandler">
        /// handler with a program to run
        /// </param>
        private static void runCommand(ArgumentsHandler argumentsHandler) {
            psInfo = new ProcessStartInfo();
            psInfo.FileName = argumentsHandler.Program;
            psInfo.Arguments = argumentsHandler.Arguments;

            // ensures data will bea read from output and error
            psInfo.UseShellExecute = false;
            psInfo.RedirectStandardOutput = true;
            psInfo.RedirectStandardError = true;
            // ensures no new window would be created
            psInfo.CreateNoWindow = true;
            psInfo.WindowStyle = ProcessWindowStyle.Hidden;

            Thread thread = new Thread(new ThreadStart(execute));
            thread.Start();
            thread.Join();
        }

        /// <summary>
        /// Executes current thread.
        /// </summary>
        private static void execute()
        {
            try
            {
                currentProcess = Process.Start(psInfo);

                outRead = false;
                new Thread(new ThreadStart(handleOut)).Start();
                outRead = false;
                new Thread(new ThreadStart(handleErr)).Start();

                currentProcess.WaitForExit();
                Environment.ExitCode = currentProcess.ExitCode;

                while (!outRead || !errRead)
                    Thread.Sleep(1);
            }
            catch (Win32Exception ex)
            {
                errWriter.WriteLine(ex.Message);
                Environment.ExitCode = -1;
            }
        }

        /// <summary>
        /// Redirects output to NamedPipe.
        /// </summary>
        /// <param name="_in">
        /// incoming Console reader
        /// </param>
        /// <param name="_out">
        /// outgoing NamedPipe
        /// </param>
        private static void redirect(StreamReader _in, StreamWriter _out)
        {
            string tmp;
            while ((tmp = _in.ReadLine()) != null)
                _out.WriteLine(tmp);
        }

        /// <summary>
        /// Function passed into new thread used for redirecting output.
        /// </summary>
        private static void handleOut()
        {
            redirect(currentProcess.StandardOutput, outWriter);
            outRead = true;
        }

        /// <summary>
        /// Function passed into new thread used for redirecting errors.
        /// </summary>
        private static void handleErr()
        {
            redirect(currentProcess.StandardError, errWriter);
            errRead = true;
        }
    }

    /// <summary>
    /// Handles arguments passed from Main(string[])
    /// </summary>
    internal class ArgumentsHandler
    {
        private static Regex singleWrapped = new Regex("^" + '"' + "(" + '\\' + '"' + "|[^" + '"' + "])*" + '"' + "$");
        private static Regex beginingOfGroup = new Regex("^" + '"');
        private static Regex endOfGroup = new Regex("^(.*[^" + '\\' + "\\" + "])?" + '"' + "$");

        /// <summary>
        /// Contains original command.
        /// </summary>
        private String command;

        /// <summary>
        /// Contains program's name.
        /// </summary>
        private String program;

        /// <summary>
        /// Contains program's arguments.
        /// </summary>
        private String arguments;

        /// <summary>
        /// Initiates handler with command.
        /// </summary>
        /// <param name="command">
        /// command to parse
        /// </param>
        public ArgumentsHandler(string command)
        {
            this.command = command;
        }

        /// <summary>
        /// Returns program's name.
        /// </summary>
        public String Program
        {
            get { return program; }
        }

        /// <summary>
        /// Returns program's arguments.
        /// </summary>
        public String Arguments
        {
            get { return arguments; }
        }

        /// <summary>
        /// Parses command and returns whether there is any program to run.
        /// </summary>
        /// <returns>
        /// whether command contained any program's name
        /// </returns>
        public bool ParseArguments()
        {
            List<string> preparedResult = new List<string>();
            String tmp = null;

            foreach (string currentlyCheckedString in command.Split(' '))
            {
                if (tmp != null)
                {
                    tmp += " " + currentlyCheckedString;
                    if (endOfGroup.Match(currentlyCheckedString).Success)
                    {
                        preparedResult.Add(tmp);
                        tmp = null;
                    }
                }
                else
                {
                    if (singleWrapped.Match(currentlyCheckedString).Success)
                        preparedResult.Add(currentlyCheckedString);
                    else if (beginingOfGroup.Match(currentlyCheckedString).Success)
                        tmp = currentlyCheckedString;
                    else if (currentlyCheckedString.Length > 0)
                        preparedResult.Add(currentlyCheckedString);
                }
            }

            if (tmp != null || preparedResult.Count == 0)
                return false;

            program = preparedResult[0];
            preparedResult.RemoveAt(0);
            arguments = String.Join(" ", preparedResult.ToArray());

            return true;
        }
    }
}