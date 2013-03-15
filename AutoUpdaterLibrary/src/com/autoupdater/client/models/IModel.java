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

import java.util.Comparator;

/**
 * Interface for classes representing models.
 * 
 * <p>
 * Models have equal(Object), compareTo(Object) and hashCode() defined in a way
 * that ensures proper order of display. Because of that default
 * Collection.compareTo(Object) method (and similar) most likely won't work
 * correctly.
 * </p>
 * 
 * <p>
 * To ensure correct results with work with Models it is required to use Models
 * methods instead.
 * </p>
 * 
 * @see com.autoupdater.client.models.Models
 * 
 * @param <T>
 *            type of class implementing interface
 */
public interface IModel<T> extends Comparable<T> {
    /**
     * Compares Models with similar server properties: server address, program's
     * name, etc.
     * 
     * <p>
     * Purpose of this Comparator is to help finding similar object during
     * initialization of Client to help them compliment data from some other
     * objects.
     * </p>
     * 
     * @return Comparator instance
     */
    public Comparator<T> getInstallationsServerPropertiesComparator();

    /**
     * Compares Models with properties that identifies their local installation:
     * path to directory, server address, etc.
     * 
     * <p>
     * Purpose of this Comparator is to help finding two matching objects, that
     * are fully initialized and can be compared through all fields that
     * distinguish them.
     * </p>
     * 
     * @return Comparator instance
     */
    public Comparator<T> getLocalInstallationsComparator();

    /**
     * Compares Models with instances indistinguishable to server: properties
     * such as installation path are ignored.
     * 
     * <p>
     * Purpose of this Comparator is to help finding some outer instance
     * (obtained through download) matching inner instance (obtained as fully
     * initialized model of local data), to compliment inner model with some
     * outer information.
     * </p>
     * 
     * @return Comparator instance
     */
    public Comparator<T> getLocal2ServerComparator();
}
