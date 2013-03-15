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
