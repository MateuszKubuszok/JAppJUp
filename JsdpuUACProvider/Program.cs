using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.IO.Pipes;

/**
 * 
 */
namespace UACPerformer {
    class Executor {
        private static string identifier;

        static void Main(string[] commands) {
            if (commands.Length == 0)
            {
                return;
            }

            identifier = commands[0];

            for (int i = 1; i < commands.Length; i++)
            {
                parseCommand(commands[i]);
            }
        }

        private static void parseCommand(String command) {
            ArgumentsHandler argumentsHandler = new ArgumentsHandler(command);

            if (argumentsHandler.ParseArguments())
                runCommand(argumentsHandler);
        }

        private static void runCommand(ArgumentsHandler argumentsHandler) {
            ProcessStartInfo psInfo = new ProcessStartInfo();
            psInfo.FileName = argumentsHandler.Program;
            psInfo.Arguments = argumentsHandler.Arguments;

            psInfo.UseShellExecute = false;
            psInfo.RedirectStandardOutput = true;
            psInfo.RedirectStandardError = true;
            psInfo.CreateNoWindow = true;
            psInfo.WindowStyle = ProcessWindowStyle.Hidden;

            try
            {
                Process process = Process.Start(psInfo);

                NamedPipeClientStream outClient = new NamedPipeClientStream(".", identifier + ".out", PipeDirection.Out);
                outClient.Connect();
                StreamWriter outWriter = new StreamWriter(outClient);
                outWriter.Write(process.StandardOutput.ReadToEnd());
                outWriter.Close();
                outClient.Close();

                NamedPipeClientStream errClient = new NamedPipeClientStream(".", identifier + ".err", PipeDirection.Out);
                errClient.Connect();
                StreamWriter errWriter = new StreamWriter(errClient);
                errWriter.Write(process.StandardError.ReadToEnd());
                errWriter.Close();
                errClient.Close();
            }
            catch (Win32Exception ex)
            {
                Console.Error.WriteLine(ex.Message);
            }
        }
    }

    internal class ArgumentsHandler
    {
        private String command;
        private String program;
        private String arguments;

        public ArgumentsHandler(string command)
        {
            this.command = command;
        }

        public String Program
        {
            get { return program; }
        }

        public String Arguments
        {
            get { return arguments; }
        }

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