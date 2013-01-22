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
