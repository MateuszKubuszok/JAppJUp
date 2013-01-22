package com.autoupdater.server.xml.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.autoupdater.server.models.Package;

@XmlRootElement(name = "package")
public class PackageXML {
    private final int id;

    private final String name;

    public PackageXML() {
        id = 0;
        name = "";
    }

    public PackageXML(Package _package) {
        id = _package.getId();
        name = _package.getName();
    }

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }
}
