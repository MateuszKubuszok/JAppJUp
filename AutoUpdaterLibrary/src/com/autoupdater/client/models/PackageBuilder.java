package com.autoupdater.client.models;

import java.util.SortedSet;

/**
 * Builder that creates Package instances.
 * 
 * @see com.autoupdater.client.models.Package
 */
public class PackageBuilder {
    private final Package _package;

    private PackageBuilder() {
        _package = new Package();
    }

    /**
     * Creates new PackageBuilder.
     * 
     * @return PackageBuilder
     */
    public static PackageBuilder builder() {
        return new PackageBuilder();
    }

    public PackageBuilder copy(Package _package) {
        this._package.setID(_package.getID());
        this._package.setChangelog(_package.getChangelog());
        this._package.setName(_package.getName());
        this._package.setProgram(_package.getProgram());
        this._package.setUpdate(_package.getUpdate());
        this._package.setVersionNumber(_package.getVersionNumber());
        return this;
    }

    public PackageBuilder setName(String name) {
        _package.setName(name);
        return this;
    }

    public PackageBuilder setID(String id) {
        _package.setID(id);
        return this;
    }

    public PackageBuilder setProgram(Program program) {
        _package.setProgram(program);
        return this;
    }

    public PackageBuilder setUpdate(Update update) {
        _package.setUpdate(update);
        return this;
    }

    public PackageBuilder setChangelog(SortedSet<ChangelogEntry> changelog) {
        _package.setChangelog(changelog);
        return this;
    }

    public PackageBuilder setVersionNumber(String versionNumber) {
        _package.setVersionNumber(versionNumber);
        return this;
    }

    public PackageBuilder setVersionNumber(VersionNumber versionNumber) {
        _package.setVersionNumber(versionNumber);
        return this;
    }

    /**
     * Builds Package.
     * 
     * @return Package
     */
    public Package build() {
        return _package;
    }
}
