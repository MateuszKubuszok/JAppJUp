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
package com.autoupdater.client.models;

import static com.autoupdater.client.environment.settings.ProgramSettingsBuilder.builder;
import static com.autoupdater.client.models.Models.*;
import static com.autoupdater.client.models.Models.EComparisionType.LOCAL_INSTALLATIONS;
import static com.autoupdater.client.utils.comparables.Comparables.compare;
import static com.google.common.base.Objects.equal;
import static java.lang.Math.pow;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.environment.settings.ProgramSettings;
import com.autoupdater.client.utils.comparables.Comparables;

/**
 * Class representing Program - either on server or installed one.
 */
public class Program implements IModel<Program> {
    private String name;
    private String pathToProgramDirectory;
    private String serverAddress;

    private final SortedSet<Package> packages;
    private final SortedSet<BugEntry> bugs;
    private boolean developmentVersion;

    Program() {
        packages = new TreeSet<Package>();
        bugs = new TreeSet<BugEntry>();
    }

    /**
     * Search through set of ProgramSettings and returns matching one if
     * possible.
     * 
     * @param programsSettings
     *            set of ProgramSettings
     * @return matching ProgramSettings if found, null otherwise
     */
    public ProgramSettings findProgramSettings(SortedSet<ProgramSettings> programsSettings) {
        return findEqual(programsSettings, builder().setProgramName(name)
                .setPathToProgramDirectory(pathToProgramDirectory).setServerAddress(serverAddress)
                .build(), LOCAL_INSTALLATIONS);
    }

    /**
     * Return's Program's name.
     * 
     * @return Program's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets Program's name.
     * 
     * @param name
     *            Program's name
     */
    void setName(String name) {
        this.name = name != null ? name : "";
    }

    /**
     * Returns path to installation's directory.
     * 
     * @return installation's directory
     */
    public String getPathToProgramDirectory() {
        return pathToProgramDirectory;
    }

    /**
     * Sets path to installation's directory.
     * 
     * @param pathToProgramDirectory
     *            installation's directory
     */
    void setPathToProgramDirectory(String pathToProgramDirectory) {
        this.pathToProgramDirectory = pathToProgramDirectory != null ? pathToProgramDirectory : "";
    }

    /**
     * Returns this Program's server's URL.
     * 
     * @return server URL
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * Sets this Program's server's URL.
     * 
     * @param serverAddress
     *            server's URL
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress != null ? serverAddress : "";

        if (this.serverAddress.endsWith("/"))
            this.serverAddress = this.serverAddress.substring(0, this.serverAddress.length() - 1);
        if (!this.serverAddress.startsWith("http://") && !this.serverAddress.startsWith("https://"))
            this.serverAddress = "http://" + this.serverAddress;
    }

    /**
     * Whether Program is for development version.
     * 
     * @return whether Program is for development version
     */
    public boolean isDevelopmentVersion() {
        return developmentVersion;
    }

    /**
     * Sets whether Program is for development version
     * 
     * @param developmentVersion
     *            whether Program is for development version
     */
    public void setDevelopmentVersion(boolean developmentVersion) {
        this.developmentVersion = developmentVersion;
    }

    /**
     * Sets whether Program is for development version
     * 
     * @param developmentVersion
     *            whether Program is for development version
     */
    public void setDevelopmentVersion(String developmentVersion) {
        this.developmentVersion = Boolean.valueOf(developmentVersion);
    }

    /**
     * Returns Program's Packages.
     * 
     * @return Program's Packages
     */
    public SortedSet<Package> getPackages() {
        return packages;
    }

    /**
     * Sets Program's Packages
     * 
     * @param packages
     *            Program's Packages
     */
    public void setPackages(SortedSet<Package> packages) {
        this.packages.clear();
        if (packages != null) {
            this.packages.addAll(packages);
            configurePackages();
        }
    }

    /**
     * Returns Program's Bugs.
     * 
     * @return Program's Bugs
     */
    public SortedSet<BugEntry> getBugs() {
        return bugs;
    }

    /**
     * Sets Program's Bugs.
     * 
     * @param bugs
     *            Program's Bugs
     */
    public void setBugs(SortedSet<BugEntry> bugs) {
        this.bugs.clear();
        if (bugs != null)
            this.bugs.addAll(bugs);
    }

    /**
     * Whether such Package belongs to Program.
     * 
     * @param _package
     *            Package
     * @return true if such Package belongs to Program
     */
    public boolean hasMember(Package _package) {
        return packages != null && packages.contains(_package);
    }

    /**
     * Whether there aren't newer Updates to be installed.
     * 
     * @return true if there are no new Updates to install
     */
    public boolean isNotOutdated() {
        for (Package _package : packages)
            if (!_package.isNotOutdated())
                return false;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Program))
            return false;
        else if (obj == this)
            return true;

        Program program = (Program) obj;
        return equal(name, program.name) && equal(serverAddress, program.serverAddress)
                && equal(pathToProgramDirectory, program.pathToProgramDirectory);
    }

    @Override
    public int hashCode() {
        return ((int) pow(name.hashCode(), 8)) + ((int) pow(serverAddress.hashCode(), 4))
                + pathToProgramDirectory.hashCode();
    }

    @Override
    public int compareTo(Program o) {
        if (o == null)
            return 1;
        else if (o == this)
            return 0;
        else if (!equal(name, o.name))
            return compare(name, o.name);
        else if (!equal(serverAddress, o.serverAddress))
            return compare(serverAddress, o.serverAddress);
        return compare(pathToProgramDirectory, o.pathToProgramDirectory);
    }

    private void configurePackages() {
        if (packages != null)
            for (Package _package : packages)
                _package.setProgram(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[Program]").append('\n');
        builder.append("Name on server:\t\t").append(name).append('\n');
        builder.append("Installation dir:\t").append(pathToProgramDirectory).append('\n');
        builder.append("Server address:\t\t").append(serverAddress).append('\n');
        builder.append("Version type:\t\t").append(developmentVersion ? "development" : "release")
                .append('\n');

        builder.append("Known bugs:").append('\n');
        for (BugEntry bug : bugs)
            builder.append(addPrefixToEachLine(bug, "\t"));

        return builder.toString();
    }

    @Override
    public Comparator<Program> getInstallationsServerPropertiesComparator() {
        return new InstallationsServerPropertiesComparator();
    }

    @Override
    public Comparator<Program> getLocalInstallationsComparator() {
        return new LocalInstallationsComparator();
    }

    @Override
    public Comparator<Program> getLocal2ServerComparator() {
        return new Local2ServerComparator();
    }

    private class InstallationsServerPropertiesComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            if (o1 == null)
                return (o2 == null ? 0 : -1);
            else if (!equal(o1.name, o2.name))
                return Comparables.compare(o1.name, o2.name);
            else if (!equal(o1.serverAddress, o2.serverAddress))
                return Comparables.compare(o1.serverAddress, o2.serverAddress);
            return Comparables.compare(o1.pathToProgramDirectory, o2.pathToProgramDirectory);
        }
    }

    private class LocalInstallationsComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            if (o1 == null)
                return (o2 == null ? 0 : -1);
            else if (!equal(o1.name, o2.name))
                return Comparables.compare(o1.name, o2.name);
            else if (!equal(o1.serverAddress, o2.serverAddress))
                return Comparables.compare(o1.serverAddress, o2.serverAddress);
            return Comparables.compare(o1.pathToProgramDirectory, o2.pathToProgramDirectory);
        }
    }

    private class Local2ServerComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return (o1 == null) ? (o2 == null ? 0 : -1) : Comparables.compare(o1.name, o2.name);
        }
    }
}
