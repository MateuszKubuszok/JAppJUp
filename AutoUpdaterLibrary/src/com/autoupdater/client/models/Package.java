package com.autoupdater.client.models;

import static com.autoupdater.client.models.VersionNumber.UNVERSIONED;
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
public class Package implements IModel<Package> {
    private String name;
    private String id;
    private Program program;
    private Update update;
    private SortedSet<ChangelogEntry> changelog;
    private VersionNumber versionNumber;

    Package() {
        name = "";
        id = "";
        versionNumber = VersionNumber.UNVERSIONED;
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
        this.changelog = changelog != null ? changelog : new TreeSet<ChangelogEntry>();
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

    /**
     * Returns Package's version number.
     * 
     * @return Package's version number
     */
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
     * Returns Package's Update.
     * 
     * @return Package's Update
     */
    public Update getUpdate() {
        return update;
    }

    /**
     * Sets Package's Update.
     * 
     * @param update
     *            Package's Update
     */
    public void setUpdate(Update update) {
        this.update = update;
        if (update != null)
            update.setPackage(this);
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
    public String toString() {
        return name
                + (versionNumber != null && !versionNumber.equals(UNVERSIONED) ? " "
                        + versionNumber : "");
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
