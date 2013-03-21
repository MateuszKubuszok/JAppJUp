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
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace UACPerformer
{
    class ArgumentHandler
    {
        /// <summary>
        /// Quotation mark for Regex.
        /// </summary>
        private static string qm = Regex.Escape("\"");
        /// <summary>
        /// Slash mark for Regex.
        /// </summary>
        private static string s = Regex.Escape("\\");
        /// <summary>
        /// Starting and ending with quotation mark with no quotation mark not-escaped in the middle.
        /// </summary>
        private static Regex singleWrapped = new Regex("^" + qm + "(" + s + qm + "|[^" + qm + "])*" + qm + "$");
        /// <summary>
        /// Pattern used for escaping quotations and slashes.
        /// </summary>
        private static Regex escapePattern = new Regex("(" + s + ")*" + qm);
        /// <summary>
        /// Temporary replacement t for a quotatin mark during escaping.
        /// </summary>
        private static string quoteReplacement = "?*:%";

        /// <summary>
        /// Prepares command that will run UACPerformer.exe.
        /// </summary>
        /// <param name="commands">
        /// commands passed from Main(string[])
        /// </param>
        /// <returns>
        /// command to perform
        /// </returns>
        public static string parseArguments(List<string> arguments)
        {
            List<string> args = new List<string>();
            foreach (string argument in arguments)
                args.Add(wrapCommand(argument));
            return string.Join(" ", args.ToArray());
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
        private static string wrapCommand(string command)
        {
            if (command.Contains(" ") && !singleWrapped.Match(command).Success)
                return '"' + escapeCommand(command) + '"';
            return command;
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
            string result = command;
            int longestFound = 0;
            Match matcher = escapePattern.Match(command);
            while (matcher.Success)
            {
                if (matcher.Groups[1].Length > longestFound)
                    longestFound = matcher.Groups[1].Length;
                matcher = matcher.NextMatch();
            }

            for (int i = longestFound; i >= 0; i--)
            {
                int replacementSize = (i + 1) * 2 - 1;
                string original = new string('\\', i) + '"';
                string replacement = new string('\\', replacementSize) + quoteReplacement;
                result = result.Replace(original, replacement);
            }

            return result.Replace(quoteReplacement, "\"");
        }
    }
}