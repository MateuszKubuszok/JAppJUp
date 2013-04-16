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

import java.util.Comparator;

import com.autoupdater.client.utils.comparables.Comparables;

/**
 * Class representing Program's known bug.
 */
public class BugEntry implements IModel<BugEntry> {
    private String description;

    BugEntry() {
        description = "";
    }

    /**
     * Returns bug's description.
     * 
     * @return bug's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets bug's description.
     * 
     * @param description
     *            bug's description
     */
    void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BugEntry))
            return false;
        else if (obj == this)
            return true;

        BugEntry bug = (BugEntry) obj;
        return description.equals(bug.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public int compareTo(BugEntry o) {
        if (o == null)
            return 1;
        else if (o == this)
            return 0;
        return compare(description, o.description);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public Comparator<BugEntry> getInstallationsServerPropertiesComparator() {
        return new GeneralComparator();
    }

    @Override
    public Comparator<BugEntry> getLocalInstallationsComparator() {
        return new GeneralComparator();
    }

    @Override
    public Comparator<BugEntry> getLocal2ServerComparator() {
        return new GeneralComparator();
    }

    /**
     * Comparator for everything.
     */
    static class GeneralComparator implements Comparator<BugEntry> {
        @Override
        public int compare(BugEntry o1, BugEntry o2) {
            return (o1 == null) ? (o2 == null ? 0 : -1) : Comparables.compare(o1.description,
                    o2.description);
        }
    }
}
