package com.autoupdater.server.xml.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.autoupdater.server.models.Bug;

@XmlRootElement(name = "bug")
public class BugXML {
    private final int programId;
    private final String description;

    public BugXML() {
        programId = 0;
        description = "";
    }

    public BugXML(Bug bug) {
        programId = bug.getProgram().getId();
        description = bug.getDescription();
    }

    @XmlAttribute(name = "programID")
    public int getProgramId() {
        return programId;
    }

    @XmlValue
    public String getDescription() {
        return description;
    }
}
