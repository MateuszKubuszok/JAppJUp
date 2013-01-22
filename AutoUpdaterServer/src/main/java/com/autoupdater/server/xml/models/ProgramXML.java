package com.autoupdater.server.xml.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.autoupdater.server.models.Package;
import com.autoupdater.server.models.Program;

@XmlRootElement(name = "program")
public class ProgramXML {
    private final String name;

    private final List<PackageXML> packages;

    public ProgramXML() {
        name = "";
        packages = new ArrayList<PackageXML>();
    }

    public ProgramXML(String name, List<Package> packages) {
        this.name = name;
        this.packages = new ArrayList<PackageXML>();
        for (Package _package : packages)
            this.packages.add(new PackageXML(_package));
    }

    public ProgramXML(Program program) {
        this.name = program.getName();
        this.packages = new ArrayList<PackageXML>();
        for (Package _package : program.getPackages())
            this.packages.add(new PackageXML(_package));
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "package")
    public List<PackageXML> getPackages() {
        return packages;
    }
}
