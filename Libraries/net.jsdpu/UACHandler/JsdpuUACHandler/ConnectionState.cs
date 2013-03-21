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
using System.IO;
using System.IO.Pipes;

namespace UACHandler
{
    class ConnectionState
    {
        private NamedPipeServerStream server;

        private Stream stream;

        private byte[] buffer;

        public ConnectionState(NamedPipeServerStream server, Stream target)
        {
            this.server = server;
            this.stream = target;
            buffer = new byte[128];
        }

        public byte[] Buffer
        {
            get { return buffer; }
        }

        public NamedPipeServerStream Server
        {
            get { return server; }
        }

        public Stream Stream
        {
            get { return stream; }
        }
    }
}