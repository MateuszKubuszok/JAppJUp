using System;
using System.Diagnostics;
using System.ComponentModel;

namespace UACPerformer {
    class Executor {
        private static System.IO.StreamWriter outputFile;

        static void Main(string[] commands) {
            if (commands.Length == 0)
                return;

            openOutput(commands[0]);

            for (int i = 1; i < commands.Length; i++)
                parseCommand(commands[i]);

            closeOutput();
        }

        private static void openOutput(String fileName) {
            System.IO.File.Delete(System.IO.Path.GetTempPath() + fileName);
            outputFile = new System.IO.StreamWriter(System.IO.Path.GetTempPath() + fileName);
        }

        private static void closeOutput() {
            outputFile.Close();
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

            try
            {
                Process process = Process.Start(psInfo);
                outputFile.Write(process.StandardOutput.ReadToEnd());
                outputFile.Write(process.StandardError.ReadToEnd());
                process.WaitForExit();
            }
            catch (Win32Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
    }
}