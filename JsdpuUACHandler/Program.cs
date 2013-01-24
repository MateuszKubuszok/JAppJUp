using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.IO.Pipes;
using System.Linq;

namespace UACHandler
{
    class Executor
    {
        private static int identifier = 0;

        static void Main(string[] commands)
        {
            ProcessStartInfo psInfo = new ProcessStartInfo();
            psInfo.FileName = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "UACPerformer.exe");
            psInfo.Arguments = parseArguments(commands);
            psInfo.CreateNoWindow = true;
            psInfo.UseShellExecute = true;
            psInfo.WindowStyle = ProcessWindowStyle.Hidden;

            try
            {
                Process process = Process.Start(psInfo);

                do
                {

                    NamedPipeServerStream outServer = new NamedPipeServerStream(getIdentifier() + ".out", PipeDirection.In);
                    outServer.WaitForConnection();
                    StreamReader outReader = new StreamReader(outServer);
                    Console.Out.Write(outReader.ReadToEnd());
                    outReader.Close();
                    outServer.Close();

                    NamedPipeServerStream errServer = new NamedPipeServerStream(getIdentifier() + ".err", PipeDirection.In);
                    errServer.WaitForConnection();
                    StreamReader errReader = new StreamReader(errServer);
                    Console.Error.Write(errReader.ReadToEnd());
                    errReader.Close();
                    errServer.Close();

                    System.Threading.Thread.Sleep(500);
                }
                while (!process.HasExited);
            }
            catch (Win32Exception ex)
            {
                Console.Error.WriteLine(ex.Message);
            }
        }

        static int getIdentifier() {
            Random random = new Random();
            while (identifier == 0)
                identifier = random.Next(Int16.MaxValue);
            return identifier;
        }

        static string parseArguments(string[] commands) {
            List<string> args = new List<string>();
            args.Add(getIdentifier().ToString());
            foreach (string command in commands) {
                args.Add("\"" + command.Replace('"', '\"') + "\"");
            }
            return String.Join(" ", args.ToArray());
        }
    }
}
