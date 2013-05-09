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

import static com.autoupdater.client.utils.comparables.Comparables.compare;
import static com.google.common.base.Objects.equal;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.autoupdater.client.utils.comparables.Comparables;
import com.google.common.base.Objects;

/**
 * Class representing Program - either on server or installed one.
 */
public class Package implements IModel<Package>, IModelWithVersionNumber {
    private String name;
    private String id;
    private Program program;
    private final SortedSet<Update> updates;
    private final SortedSet<ChangelogEntry> changelog;
    private VersionNumber versionNumber;

    Package() {
        name = "";
        id = "";
        versionNumber = VersionNumber.UNVERSIONED;
        updates = new TreeSet<Update>();
        changelog = new TreeSet<ChangelogEntry>();
    }

    /**
     * Returns Program.
     * 
     * @return Program
     */
    public Program getProgram() {
        return program;
    }

    /**
     * Sets Program.
     * 
     * @param program
     *            Program
     */
    void setProgram(Program program) {
        this.program = program;
    }

    /**
     * Returns changelog.
     * 
     * @return changelog
     */
    public SortedSet<ChangelogEntry> getChangelog() {
        return changelog;
    }

    /**
     * Sets changelog.
     * 
     * @param changelog
     *            changelog
     */
    public void setChangelog(SortedSet<ChangelogEntry> changelog) {
        this.changelog.clear();
        if (changelog != null)
            this.changelog.addAll(changelog);
    }

    /**
     * Return's Package's name.
     * 
     * @return Package's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets Package's name.
     * 
     * @param name
     *            Package's name
     */
    void setName(String name) {
        this.name = name != null ? name : "";
    }

    /**
     * Returns Package's ID.
     * 
     * @return Package's ID
     */
    public String getID() {
        return id;
    }

    /**
     * Sets Package's ID.
     * 
     * @param id
     *            Package's ID
     */
    public void setID(String id) {
        this.id = id != null ? id : "";
    }

    @Override
    public VersionNumber getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets Package's version number.
     * 
     * @param versionNumber
     *            Package's version number
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = new VersionNumber(versionNumber);
    }

    /**
     * Sets Package's version number.
     * 
     * @param versionNumber
     *            Package's version number
     */
    public void setVersionNumber(VersionNumber versionNumber) {
        this.versionNumber = versionNumber != null ? versionNumber : VersionNumber.UNVERSIONED;
    }

    /**
     * Adds Update to the set.
     * 
     * @param update
     *            Update that should belong to Package
     */
    public void addUpdate(Update update) {
        updates.add(update);
        update.setPackage(this);
    }

    /**
     * Whether Package has any Updates.
     * 
     * @return true if Package has any Update
     */
    public boolean hasUpdates() {
        return !updates.isEmpty();
    }

    /**
     * Returns Package's Updates.
     * 
     * @return Package's Updates
     */
    public SortedSet<Update> getUpdates() {
        return updates;
    }

    /**
     * Sets Package's Updates.
     * 
     * @param updates
     *            new Package's Update
     */
    public void setUpdates(SortedSet<Update> updates) {
        this.updates.clear();
        if (updates != null)
            this.updates.addAll(updates);
    }

    /**
     * Whether Package can be updated with such Update.
     * 
     * @param update
     *            Update
     * @return true if Package could be updated with such Update
     */
    public boolean isUpdatedBy(Update update) {
        return update != null && Objects.equal(id, update.getPackageID());
    }

    /**
     * Whether there aren't newer updates to be installed.
     * 
     * @return true if there are no newer updates or Update has the same version
     *         number as Package
     */
    public boolean isNotOutdated() {
        return updates.isEmpty() || versionNumber.compareTo(updates.last().getVersionNumber()) >= 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Package))
            return false;
        else if (obj == this)
            return true;

        Package _package = (Package) obj;
        return equal(name, _package.name) && equal(versionNumber, _package.versionNumber)
                && equal(program, _package.program);
    }

    @Override
    public boolean equalVersions(IModelWithVersionNumber model) {
        return versionNumber.equals(model.getVersionNumber());
    }

    @Override
    public int hashCode() {
        return (name + id).hashCode();
    }

    @Override
    public int compareTo(Package o) {
        if (o == null)
            return 1;
        else if (o == this)
            return 0;
        else if (!equal(name, o.name))
            return compare(name, o.name);
        else if (!equal(versionNumber, o.versionNumber))
            return compare(versionNumber, o.versionNumber);
        return compare(program, o.program);
    }

    @Override
    public int compareVersions(IModelWithVersionNumber model) {
        return versionNumber.compareTo(model.getVersionNumber());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[Package]").append('\n');
        builder.append("Package name:\t").append(name).append('\n');
        builder.append("Version number:\t").append(versionNumber).append('\n');

        return builder.toString();
    }

    @Override
    public Comparator<Package> getInstallationsServerPropertiesComparator() {
        return new CreationHelperComparator();
    }

    @Override
    public Comparator<Package> getLocalInstallationsComparator() {
        return new InnerConsistencyComparator();
    }

    @Override
    public Comparator<Package> getLocal2ServerComparator() {
        return new OuterMatchingComparator();
    }

    private class CreationHelperComparator implements Comparator<Package> {
        @Override
        public int compare(Package o1, Package o2) {
            return (o1 == null) ? (o2 == null ? 0 : -1) : Comparables.compare(o1.name, o2.name);
        }
    }

    private class InnerConsistencyComparator implements Comparator<Package> {
        @Override
        public int compare(Package o1, Package o2) {
            if (o1 == null)
                return (o2 == null ? 0 : -1);
            if (Comparables.compare(o1.name, o2.name) != 0)
                return Comparables.compare(o1.name, o2.name);
            return Comparables.compare(o1.program, o2.program);
        }
    }

    private class OuterMatchingComparator implements Comparator<Package> {
        @Override
        public int compare(Package o1, Package o2) {
            return (o1 == null) ? (o2 == null ? 0 : -1) : Comparables.compare(o1.name, o2.name);
        }
    }
}
