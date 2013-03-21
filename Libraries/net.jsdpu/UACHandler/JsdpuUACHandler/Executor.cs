/*
  Copyright 2012-2013 Mateusz Kubuszok
 
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at 
  
  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
using System;
using System.Diagnostics;
using System.IO;
using System.IO.Pipes;

namespace UACHandler
{
    class Executor
    {
        private static int identifier = 0;

        static void Main(string[] commands)
        {
            if (commands.Length == 0)
                return;

            ProcessStartInfo psInfo = CreateProcessStartInfo(commands);
            ExecuteProcess(psInfo);
        }

        private static int Identifier
        {
            get { return identifier; }
        }

        private static ProcessStartInfo CreateProcessStartInfo(string[] commands) {
            ProcessStartInfo psInfo = new ProcessStartInfo();

            psInfo.FileName = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "UACPerformer.exe");
            psInfo.Arguments = Identifier + " " + ArgumentHandler.parseArguments(commands);
            psInfo.CreateNoWindow = true;
            psInfo.UseShellExecute = true;
            psInfo.WindowStyle = ProcessWindowStyle.Hidden;

            return psInfo;
        }

        private static void ExecuteProcess(ProcessStartInfo psInfo) {
            // creates services
            InstantiateServer("output", PipeDirection.In, Console.OpenStandardOutput(80), HandleIn.Connection);
            InstantiateServer("error", PipeDirection.In, Console.OpenStandardError(80), HandleIn.Connection);
            InstantiateServer("input", PipeDirection.Out, Console.OpenStandardInput(80), HandleOut.Connection);
            // creates process that will connect to services
            Process process = Process.Start(psInfo);
            // handles exit code
            process.WaitForExit();
            Environment.ExitCode = process.ExitCode;
        }

        private static void InstantiateServer(string name, PipeDirection direction, Stream target, AsyncCallback callback)
        {
            NamedPipeServerStream server = new NamedPipeServerStream("UACHandler." + Identifier + "." + name, direction, 1, PipeTransmissionMode.Byte, PipeOptions.Asynchronous);
            server.BeginWaitForConnection(callback, new ConnectionState(server, target));
        }
    }
}
