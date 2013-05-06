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
        this._package.setUpdates(_package.getUpdates());
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

    public PackageBuilder setUpdates(SortedSet<Update> update) {
        _package.setUpdates(update);
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
