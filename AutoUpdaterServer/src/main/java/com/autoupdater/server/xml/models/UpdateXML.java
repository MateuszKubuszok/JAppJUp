package com.autoupdater.server.xml.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.autoupdater.server.models.Update;

@XmlRootElement(name = "update")
public class UpdateXML {
    private final int id;
    private final String packageName;
    private final int packageId;
    private final String version;
    private final String developmentVersion;
    private final String changelog;
    private final String updateType;
    private final String originalName;
    private final String relativePath;
    private final String updaterCommand;

    public UpdateXML() {
        id = 0;
        packageId = 0;
        packageName = "";
        version = "";
        developmentVersion = "";
        changelog = "";
        updateType = "";
        originalName = "";
        relativePath = "";
        updaterCommand = "";
    }

    public UpdateXML(Update update) {
        id = update.getId();
        packageName = update.getThePackage().getName();
        packageId = update.getThePackage().getId();
        version = update.getVersion();
        developmentVersion = update.isDevelopmentVersion() ? "true" : "false";
        changelog = update.getChangelog();
        updateType = update.getUpdateType();
        originalName = update.getFileName();
        relativePath = update.getRelativePath();
        updaterCommand = update.getUpdaterCommand();
    }

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    @XmlAttribute(name = "packageName")
    public String getPackageName() {
        return packageName;
    }

    @XmlAttribute(name = "packageId")
    public int getPackageId() {
        return packageId;
    }

    @XmlAttribute(name = "developmentVersion")
    public String getDevelopmentVersion() {
        return developmentVersion;
    }

    @XmlAttribute(name = "version")
    public String getVersion() {
        return version;
    }

    @XmlAttribute(name = "type")
    public String getUpdateType() {
        return updateType;
    }

    @XmlValue
    public String getChangelog() {
        return changelog;
    }

    @XmlAttribute(name = "originalName")
	public String getOriginalName() {
		return originalName;
	}

    @XmlAttribute(name = "relativePath")
	public String getRelativePath() {
		return relativePath;
	}

    @XmlAttribute(name = "command")
	public String getUpdaterCommand() {
		return updaterCommand;
	}
}
