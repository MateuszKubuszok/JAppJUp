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
package com.autoupdater.server.xml.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.autoupdater.server.models.Program;

@XmlRootElement(name = "programs")
public class ProgramsXML {
    private final List<ProgramXML> programs;

    public ProgramsXML() {
        this.programs = new ArrayList<ProgramXML>();
    }

    public ProgramsXML(List<Program> programs) {
        this.programs = new ArrayList<ProgramXML>();
        if (programs != null)
            for (Program program : programs)
                this.programs.add(new ProgramXML(program));
    }

    @XmlElement(name = "program")
    public List<ProgramXML> getPrograms() {
        return programs;
    }
}
