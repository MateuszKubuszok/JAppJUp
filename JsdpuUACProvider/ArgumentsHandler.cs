using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace UACPerformer {
    internal class ArgumentsHandler {
        private String command;
        private String program;
        private String arguments;

        public ArgumentsHandler(string command) {
            this.command = command;
        }

        public String Program {
            get { return program; }
        }

        public String Arguments {
            get { return arguments; }
        }

        public bool ParseArguments() {
            string[] argumentsArray = command.Split(' ');
            program = argumentsArray[0];

            List<String> argumentsList = new List<String>(argumentsArray);
            argumentsList.RemoveAt(0);
            arguments = String.Join(" ", argumentsList);

            return !String.IsNullOrEmpty(program);
        }
    }
}