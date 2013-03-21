﻿/*
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
using System.ComponentModel;

namespace UACHandler
{
    class HandleOut
    {
        public static void Connection(IAsyncResult asyncResult)
        {
            ConnectionState state = asyncResult.AsyncState as ConnectionState;
            try
            {
                state.Server.EndWaitForConnection(asyncResult);
                state.Stream.BeginRead(state.Buffer, 0, state.Buffer.Length, Read, state);
            }
            catch (Win32Exception ex)
            {
                Console.Error.WriteLine(ex.Message);
                Environment.ExitCode = -1;
            }
        }

        private static void Read(IAsyncResult asyncResult)
        {
            ConnectionState state = asyncResult.AsyncState as ConnectionState;
            int readAmount = 0;
            try
            {
                readAmount = state.Stream.EndRead(asyncResult);
            }
            catch (Win32Exception)
            {
            }
            if (readAmount > 0)
                state.Server.BeginWrite(state.Buffer, 0, readAmount, Write, state);
            else
                state.Server.Close();
        }

        private static void Write(IAsyncResult asyncResult)
        {
            ConnectionState state = asyncResult.AsyncState as ConnectionState;
            try
            {
                state.Server.EndWrite(asyncResult);
            }
            catch (Win32Exception)
            {
            }
            state.Stream.BeginRead(state.Buffer, 0, state.Buffer.Length, Read, state);
        }
    }
}