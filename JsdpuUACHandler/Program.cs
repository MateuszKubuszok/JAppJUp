using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.IO.Pipes;
using System.Linq;
using System.Threading;
using System.Text.RegularExpressions;

/// <summary>
/// Runs commands with elevation via UACPerformer.exe.
/// </summary>
namespace UACHandler
{
    class Executor
    {
        /// <summary>
        /// Identifier used for communication with UACPerformer via NamedPipes. 
        /// </summary>
        private static int identifier = 0;

        private static bool outRead = false;
        private static bool errRead = false;

        /// <summary>
        /// Runs passed commands with elevation and redirects results on standard Out and standard Error.
        /// </summary>
        /// 
        /// Results will be displayed for for each program sequentially: out1, err1, out2, err2...
        /// 
        /// <param name="commands">
        /// Array of commands where each of them is a string containing command in the same form it would
        /// have if run on console directly (program name and arguments separated by spaces).
        /// </param>
        static void Main(string[] commands)
        {
            if (commands.Length == 0)
            {
                return;
            }

            ProcessStartInfo psInfo = new ProcessStartInfo();
            psInfo.FileName = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "UACPerformer.exe");
            psInfo.Arguments = parseArguments(commands);
            psInfo.CreateNoWindow = true;
            psInfo.UseShellExecute = true;
            psInfo.WindowStyle = ProcessWindowStyle.Hidden;

            try
            {
                new Thread(new ThreadStart(handleOut)).Start();
                new Thread(new ThreadStart(handleErr)).Start();

                Process process = Process.Start(psInfo);

                while (!outRead || !errRead)
                {
                    Thread.Sleep(1);
                }
            }
            catch (Win32Exception ex)
            {
                Console.Error.WriteLine(ex.Message);
            }

            Environment.Exit(0);
        }

        /// <summary>
        /// Identifier used for communication with UACPerformer via NamedPipes. 
        /// </summary>
        /// <returns>
        /// value of an identifier
        /// </returns>
        private static int getIdentifier() {
            Random random = new Random();
            while (identifier == 0)
            {
                identifier = random.Next(Int16.MaxValue);
            }
            return identifier;
        }

        /// <summary>
        /// Prepares command that will run UACPerformer.exe.
        /// </summary>
        /// <param name="commands">
        /// commands passed from Main(string[])
        /// </param>
        /// <returns>
        /// command to perform
        /// </returns>
        private static string parseArguments(string[] commands) {
            List<string> args = new List<string>();
            args.Add(getIdentifier().ToString());
            foreach (string command in commands)
            {
                Console.Out.WriteLine("->:"+command);
                args.Add(wrapCommand(command));
                Console.Out.WriteLine("<-:" + args.Last());
            }
            return String.Join(" ", args.ToArray());
        }

        /// <summary>
        /// Escapes command (to review).
        /// </summary>
        /// <param name="command">
        /// command to escae
        /// </param>
        /// <returns>
        /// escaped command
        /// </returns>
        private static string escapeCommand(string command)
        {
            Regex matcher = new Regex("(" + Regex.Escape("\\") + ")*" + Regex.Escape("\""));
            Match result = matcher.Match(command);

            while (result.Success)
            {
                int replacementSize = (result.Groups[1].Length + 1) * 2 - 1;
                Console.Out.WriteLine("?" + result.Groups[0].Value + ":" + result.Groups[1].Length + "->" + replacementSize);
                string replacement = new String('\\', replacementSize) + "?*:%";
                command = command.Replace(result.Groups[0].Value, replacement);
                result = matcher.Match(command);
            }

            return command.Replace("?*:%", "\"");
        }

        /// <summary>
        /// Wraps command with quotation marks.
        /// </summary>
        /// <param name="command">
        /// command to wrap
        /// </param>
        /// <returns>
        /// wrapped command
        /// </returns>
        private static string wrapCommand(String command)
        {
            return '"' + escapeCommand(command) + '"';
        }

        /// <summary>
        /// Redirects input from NamedPipe to output.
        /// </summary>
        /// <param name="_in">
        /// incoming NamedPipe
        /// </param>
        /// <param name="_out">
        /// outgoing Console writer
        /// </param>
        private static void redirect(NamedPipeServerStream _in, TextWriter _out)
        {
            StreamReader reader = new StreamReader(_in);
            string tmp;
            while ((tmp = reader.ReadLine()) != null)
            {
                _out.WriteLine(tmp);
            }
            reader.Close();
        }

        /// <summary>
        /// Function for new thread responsible for redirecting output into pipe.
        /// </summary>
        private static void handleOut() {
            NamedPipeServerStream outServer = new NamedPipeServerStream(getIdentifier() + ".out", PipeDirection.In);
            outServer.WaitForConnection();
            redirect(outServer, Console.Out);
            outServer.Close();
            outRead = true;
        }

        /// <summary>
        /// Function for new thread responsible for redirecting error into pipe.
        /// </summary>
        private static void handleErr()
        {
            NamedPipeServerStream errServer = new NamedPipeServerStream(getIdentifier() + ".err", PipeDirection.In);
            errServer.WaitForConnection();
            redirect(errServer, Console.Error);
            errServer.Close();
            errRead = true;
        }
    }
}
