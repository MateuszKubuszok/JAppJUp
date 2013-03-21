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
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.IO.Pipes;

namespace UACPerformer
{
    class Executor {
        private static string identifier;

        public static string Identifier
        {
            get { return Executor.identifier; }
            set { Executor.identifier = value; }
        }

        static void Main(string[] commands)
        {
            if (commands.Length == 0)
                return;

            ProcessStartInfo psInfo = ParseArguments(commands);
            ExecuteProcess(psInfo);
        }

        private static ProcessStartInfo ParseArguments(string[] command)
        {
            Identifier = command[0];

            List<string> arguments = new List<string>(command);
            arguments.RemoveAt(0);
            string program = arguments[0];
            arguments.RemoveAt(0);

            ProcessStartInfo psInfo = new ProcessStartInfo();

            psInfo.FileName = program;
            psInfo.Arguments = ArgumentHandler.parseArguments(arguments);

            // ensures data will bea read from output and error
            psInfo.UseShellExecute = false;
            psInfo.RedirectStandardOutput = true;
            psInfo.RedirectStandardError = true;
            psInfo.RedirectStandardInput = true;
            // ensures no new window would be created
            psInfo.CreateNoWindow = true;
            psInfo.WindowStyle = ProcessWindowStyle.Hidden;

            return psInfo;
        }

        private static void ExecuteProcess(ProcessStartInfo psInfo)
        {
            // connects to parental process via services
            NamedPipeClientStream outputClient = InstantiateClient("output", PipeDirection.Out);
            NamedPipeClientStream errorClient = InstantiateClient("error", PipeDirection.Out);
            NamedPipeClientStream inputClient = InstantiateClient("input", PipeDirection.In);
            // instantiate elevated process and redirects streams to services
            Process process = Process.Start(psInfo);
            HandleOut.Initiate(outputClient, process.StandardOutput.BaseStream);
            HandleOut.Initiate(errorClient, process.StandardError.BaseStream);
            HandleIn.Initiate(inputClient, process.StandardInput.BaseStream);
            // handles exit code
            process.WaitForExit();
            Environment.ExitCode = process.ExitCode;
        }

        private static NamedPipeClientStream InstantiateClient(string name, PipeDirection direction)
        {
            NamedPipeClientStream client = new NamedPipeClientStream(".", "UACHandler." + Identifier + "." + name, direction);
            client.Connect();
            return client;
        }
    }
}