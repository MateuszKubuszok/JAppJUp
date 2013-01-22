package com.autoupdater.server.xml.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.autoupdater.server.models.Update;

@XmlRootElement(name = "changelog")
public class ChangelogXML {
    private final String version;

    private final String changelog;

    public ChangelogXML() {
        version = "";
        changelog = "";
    }

    public ChangelogXML(String version, String changelog) {
        this.version = version;
        this.changelog = changelog;
    }

    public ChangelogXML(Update update) {
        version = update.getVersion();
        changelog = update.getChangelog();
    }

    @XmlValue
    public String getChangelog() {
        return changelog;
    }

    @XmlAttribute(name = "version")
    public String getVersion() {
        return version;
    }
}
