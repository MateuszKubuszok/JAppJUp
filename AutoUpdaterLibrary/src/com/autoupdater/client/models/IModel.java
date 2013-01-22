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
