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
using System.ComponentModel;
using System.IO;
using System.IO.Pipes;

namespace UACPerformer
{
    class HandleIn
    {
        public static void Initiate(NamedPipeClientStream client, Stream stream)
        {
            ConnectionState state = new ConnectionState(client, stream);
            state.Client.BeginRead(state.Buffer, 0, state.Buffer.Length, Read, state);
        }

        private static void Read(IAsyncResult asyncResult)
        {
            ConnectionState state = asyncResult.AsyncState as ConnectionState;
            int readAmount = 0;
            try
            {
                readAmount = state.Client.EndRead(asyncResult);
            }
            catch (Win32Exception)
            {
            }
            if (readAmount > 0)
                state.Stream.BeginWrite(state.Buffer, 0, readAmount, Write, state);
            else
                state.Stream.Close();
        }

        private static void Write(IAsyncResult asyncResult)
        {
            ConnectionState state = asyncResult.AsyncState as ConnectionState;
            try
            {
                state.Stream.EndWrite(asyncResult);
            }
            catch (Win32Exception)
            {
            }
            state.Client.BeginRead(state.Buffer, 0, state.Buffer.Length, Read, state);
        }
    }
}