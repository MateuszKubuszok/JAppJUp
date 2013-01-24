using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.IO.Pipes;
using System.Threading;

/// <summary>
/// Performs commands passed from UACHandler with elevation..
/// </summary>
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
        /// Currently run process .
        /// </summary>
        private static Process currentProcess;
        /// <summary>
        /// Whether output from current process was already written.
        /// </summary>
        private static bool outWritten;
        /// <summary>
        /// Whether error from current process was already written.
        /// </summary>
        private static bool errWritten;

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
            {
                outWriter.WriteLine("D:" + commands[i]);
                parseCommand(commands[i]);
            }

            outWriter.Close();
            outClient.Close();
            errWriter.Close();
            errClient.Close();
            Environment.Exit(0);
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
            ProcessStartInfo psInfo = new ProcessStartInfo();
            psInfo.FileName = argumentsHandler.Program;
            psInfo.Arguments = argumentsHandler.Arguments;
            //outWriter.WriteLine("P:" + argumentsHandler.Program);
            //outWriter.WriteLine("A:" + argumentsHandler.Arguments);

            psInfo.UseShellExecute = false;
            psInfo.RedirectStandardOutput = true;
            psInfo.RedirectStandardError = true;
            psInfo.CreateNoWindow = true;
            psInfo.WindowStyle = ProcessWindowStyle.Hidden;

            try
            {
                currentProcess = Process.Start(psInfo);
                outWritten = false;
                errWritten = false;

                new Thread(new ThreadStart(handleOut)).Start();
                new Thread(new ThreadStart(handleErr)).Start();

                while (!outWritten || !errWritten)
                    Thread.Sleep(1);
            }
            catch (Win32Exception ex)
            {
                Console.Error.WriteLine(ex.Message);
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
            outWritten = true;
        }

        /// <summary>
        /// Function passed into new thread used for redirecting errors.
        /// </summary>
        private static void handleErr()
        {
            redirect(currentProcess.StandardError, errWriter);
            errWritten = true;
        }
    }

    /// <summary>
    /// Handles arguments passed from Main(string[])
    /// </summary>
    internal class ArgumentsHandler
    {
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
            string[] argumentsArray = command.Split(' ');
            if (argumentsArray.Length > 0)
            {
                program = argumentsArray[0];

                List<String> argumentsList = new List<String>(argumentsArray);
                argumentsList.RemoveAt(0);
                arguments = String.Join(" ", argumentsList.ToArray());
            }

            return !String.IsNullOrEmpty(program);
        }
    }
}