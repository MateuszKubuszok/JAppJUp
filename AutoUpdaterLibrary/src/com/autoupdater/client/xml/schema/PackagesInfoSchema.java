/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package com.autoupdater.client.xml.schema;

/**
 * Describes XML schema of updates information document.
 * 
 * Used by PackagesInfoXMLParser.
 * 
 * @see com.autoupdater.client.xml.parsers.PackagesInfoParser
 */
public class PackagesInfoSchema {
    public static final String programs = "programs";

    public static final class Programs {
        public static final String program = "program";
        public static final String program_ = programs + "/" + program;

        public static final class Program {
            public static final String programName = "name";
            public static final String programName_ = program_ + "/" + programName;

            public static final String _package = "package";
            public static final String _package_ = program_ + "/" + _package;

            public static final class Package {
                public static final String name = "name";
                public static final String name_ = _package_ + "/" + name;

                public static final String id = "id";
                public static final String id_ = _package_ + "/" + id;
            }
        }
    }
}
