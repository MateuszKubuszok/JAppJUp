package com.autoupdater.server.xml.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.autoupdater.server.models.Bug;

@XmlRootElement(name = "bugs")
public class BugsXML {
    private final List<BugXML> bugs;

    public BugsXML() {
        bugs = new ArrayList<BugXML>();
    }

    public BugsXML(List<Bug> bugs) {
        this.bugs = new ArrayList<BugXML>();
        for (Bug bug : bugs)
            this.bugs.add(new BugXML(bug));
    }

    @XmlElement(name = "bug")
    public List<BugXML> getBugs() {
        return bugs;
    }
}
