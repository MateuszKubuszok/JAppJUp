using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using System.ComponentModel;

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
            psInfo.UseShellExecute = true;

            try
            {
                Process process = Process.Start(psInfo);
                process.WaitForExit();
                System.IO.StreamReader inputFile = new System.IO.StreamReader(System.IO.Path.GetTempPath() + getIdentifier());
                Console.Write(inputFile.ReadToEnd());
                inputFile.Close();
                System.IO.File.Delete(System.IO.Path.GetTempPath() + getIdentifier());
            }
            catch (Win32Exception ex)
            {
                Console.WriteLine(ex.Message);
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
            return String.Join(" ", args);
        }
    }
}
