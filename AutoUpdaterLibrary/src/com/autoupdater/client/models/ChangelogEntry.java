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

import static com.autoupdater.client.models.VersionNumber.UNVERSIONED;
import static com.autoupdater.client.utils.comparables.Comparables.compare;
import static com.google.common.base.Objects.equal;

import java.util.Comparator;

import com.autoupdater.client.utils.comparables.Comparables;
import com.google.common.base.Objects;

/**
 * Class representing single change from Package's changelog.
 */
public class ChangelogEntry implements IModel<ChangelogEntry> {
    private String changes;
    private VersionNumber versionNumber;

    ChangelogEntry() {
        changes = "";
        versionNumber = UNVERSIONED;
    }

    /**
     * Returns change's description.
     * 
     * @return change's description
     */
    public String getChanges() {
        return changes;
    }

    /**
     * Sets change's description
     * 
     * @param changes
     *            change's description
     */
    void setChanges(String changes) {
        this.changes = changes != null ? changes : "";
    }

    /**
     * Returns version number in which change was done
     * 
     * @return version number in which change was done
     */
    public VersionNumber getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets version number in which change was done
     * 
     * @param versionNumber
     *            version number in which change was done
     */
    void setVersionNumber(String versionNumber) {
        this.versionNumber = new VersionNumber(versionNumber);
    }

    /**
     * Sets version number in which change was done
     * 
     * @param versionNumber
     *            version number in which change was done
     */
    void setVersionNumber(VersionNumber versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ChangelogEntry))
            return false;
        else if (obj == this)
            return true;

        ChangelogEntry changelog = (ChangelogEntry) obj;
        return equal(changes, changelog.changes) && equal(versionNumber, changelog.versionNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(changes, versionNumber);
    }

    @Override
    public int compareTo(ChangelogEntry o) {
        if (o == null)
            return 1;
        else if (o == this)
            return 0;
        else if (!equal(versionNumber, o.versionNumber))
            return compare(versionNumber, o.versionNumber);
        return compare(changes, o.changes);
    }

    @Override
    public String toString() {
        return versionNumber + ":\n" + changes;
    }

    @Override
    public Comparator<ChangelogEntry> getInstallationsServerPropertiesComparator() {
        return new GeneralComparator();
    }

    @Override
    public Comparator<ChangelogEntry> getLocalInstallationsComparator() {
        return new GeneralComparator();
    }

    @Override
    public Comparator<ChangelogEntry> getLocal2ServerComparator() {
        return new GeneralComparator();
    }

    /**
     * Comparator for everything.
     */
    static class GeneralComparator implements Comparator<ChangelogEntry> {
        @Override
        public int compare(ChangelogEntry o1, ChangelogEntry o2) {
            if (o1 == null)
                return o2 == null ? 0 : -1;
            if (!equal(o1.versionNumber, o2.versionNumber))
                return Comparables.compare(o1.versionNumber, o2.versionNumber);
            return Comparables.compare(o1.changes, o2.changes);
        }
    }
}
