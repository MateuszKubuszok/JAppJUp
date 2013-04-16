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

import static java.io.File.separator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Contains methods used by library in work with Models, especially with
 * collections of Models.
 */
public class Models {
    private Models() {
    }

    /**
     * Adds Model to collection if it doesn't contain instance equal in a given
     * way.
     * 
     * @param increasedCollection
     *            collection to increase
     * @param addedModel
     *            added Model
     * @param comparisonType
     *            type of comparison
     * @return true if object was added
     */
    public static <M extends IModel<M>> boolean add(Collection<M> increasedCollection,
            M addedModel, EComparisionType comparisonType) {
        if (!contains(increasedCollection, addedModel, comparisonType))
            return increasedCollection.add(addedModel);
        return false;
    }

    /**
     * Adds all those Models that aren't present in collection, where presence
     * is understood as equal in a defined way.
     * 
     * @param increasedCollection
     *            collection to increase
     * @param addedCollection
     *            added collection
     * @param comparisonType
     *            type of comparison
     * @return increased collection
     */
    public static <M extends IModel<M>> Collection<M> addAll(Collection<M> increasedCollection,
            Collection<M> addedCollection, EComparisionType comparisonType) {
        for (M addedModel : addedCollection)
            add(increasedCollection, addedModel, comparisonType);
        return increasedCollection;
    }

    /**
     * Compares two object in a given way.
     * 
     * @param o1
     *            Model to compare
     * @param o2
     *            Model to compare
     * @param comparisonType
     *            type of comparison
     * @return result of comparison
     */
    public static <M extends IModel<M>> int compare(M o1, M o2, EComparisionType comparisonType) {
        return comparisonType.getComparator(o1).compare(o1, o2);
    }

    /**
     * Checks whether there is Model equal to given in a defined way.
     * 
     * @param checkedCollection
     *            collection to search
     * @param modelInstance
     *            compared instance
     * @param comparisonType
     *            type of comparison
     * @return whether in collection is object that can be considered equal to
     *         given
     */
    public static <M extends IModel<M>> boolean contains(Collection<M> checkedCollection,
            M modelInstance, EComparisionType comparisonType) {
        Comparator<M> comparator = comparisonType.getComparator(modelInstance);
        for (M checkedModel : checkedCollection)
            if (comparator.compare(modelInstance, checkedModel) == 0)
                return true;

        return false;
    }

    /**
     * Checks whether or not 2 instances of some Model are equal in a defined
     * way.
     * 
     * @param obj1
     *            Model to compare
     * @param obj2
     *            Model to compare
     * @param comparisonType
     *            type of comparison
     * @return whether object are equal in given way
     */
    public static <M extends IModel<M>> boolean equal(M obj1, M obj2,
            EComparisionType comparisonType) {
        return (obj1 == null) ? (obj2 == null) : (comparisonType.getComparator(obj1).compare(obj1,
                obj2) == 0);
    }

    /**
     * Finds Model instance equal to given in a defined way.
     * 
     * @param checkedCollection
     *            collection to search
     * @param patternModelInstance
     *            compared instance
     * @param comparisonType
     *            type of comparison
     * @return first found model instance that can be considered "equal", or
     *         null if none found
     */
    public static <M extends IModel<M>> M findEqual(Collection<M> checkedCollection,
            M patternModelInstance, EComparisionType comparisonType) {
        if (patternModelInstance != null && checkedCollection != null) {
            Comparator<M> comparator = comparisonType.getComparator(patternModelInstance);

            for (M checkedInstance : checkedCollection)
                if (comparator.compare(patternModelInstance, checkedInstance) == 0)
                    return checkedInstance;
        }

        return null;
    }

    /**
     * Returns relative path with slashes/backslashes changed to File.separator
     * and with separators removed from beginning and end of path.
     * 
     * @param relativePath
     *            relative path in unsecured format
     * @return standardized relative path
     */
    public static String secureRelativePath(String relativePath) {
        String toRemove = separator.equals("/") ? "\\" : "/";
        while (relativePath.contains(toRemove))
            relativePath = relativePath.replace(toRemove, separator);
        if (relativePath.startsWith(separator))
            relativePath = relativePath.substring(1);
        if (relativePath.endsWith(separator))
            relativePath = relativePath.substring(0, relativePath.length() - 1);
        return relativePath;
    }

    /**
     * Adds prefix to each line of toString() of an object.
     * 
     * @param object
     *            Object which toString() should be enriched with prefixes
     * @param prefix
     *            prefix to add to each line
     * @return result
     */
    public static String addPrefixToEachLine(Object object, String prefix) {
        return addPrefixToEachLine(object.toString(), prefix);
    }

    /**
     * Adds prefix to each line of given String.
     * 
     * @param lines
     *            String that should be enriched with prefixes
     * @param prefix
     *            prefix to add to each line
     * @return result
     */
    public static String addPrefixToEachLine(String lines, String prefix) {
        StringBuilder builder = new StringBuilder();

        for (String line : lines.split("\n"))
            builder.append(prefix).append(line).append('\n');

        return builder.toString();
    }

    /**
     * Defines type of comparison made by queries.
     * 
     * @see com.autoupdater.client.models.IModel
     */
    public static enum EComparisionType {
        /**
         * Compares Models with same "server properties": name, server address,
         * etc.
         * 
         * <p>
         * It is mainly used for matching ProgramSettings with Programs.
         * </p>
         * 
         * @see com.autoupdater.client.models.IModel#getInstallationsServerPropertiesComparator()
         */
        INSTALLATIONS_SERVER_PROPERTIES(new ComparatorResolver() {
            @Override
            public <M extends IModel<M>> Comparator<M> getComparator(M modelInstance) {
                return modelInstance.getInstallationsServerPropertiesComparator();
            }
        }),

        /**
         * Compares Models with the same local installation properties: name,
         * development version, installation path., etc.
         * 
         * <p>
         * It is mainly used for matching Models describing the same
         * installation.
         * </p>
         * 
         * @see com.autoupdater.client.models.IModel#getLocalInstallationsComparator()
         */
        LOCAL_INSTALLATIONS(new ComparatorResolver() {
            @Override
            public <M extends IModel<M>> Comparator<M> getComparator(M modelInstance) {
                return modelInstance.getLocalInstallationsComparator();
            }
        }),

        /**
         * Compares Models with same data identifying server resources: server
         * address,
         * 
         * <p>
         * it is used mainly for matching local resources which use the same
         * server resources.
         * </p>
         * 
         * @see com.autoupdater.client.models.IModel#getLocal2ServerComparator()
         */
        LOCAL_TO_SERVER(new ComparatorResolver() {
            @Override
            public <M extends IModel<M>> Comparator<M> getComparator(M modelInstance) {
                return modelInstance.getLocal2ServerComparator();
            }
        });

        private final ComparatorResolver resolver;

        private EComparisionType(ComparatorResolver resolver) {
            this.resolver = resolver;
        }

        /**
         * Returns appropriate comparator for given Model.
         * 
         * @param modelInstance
         *            Model's instance
         * @return comparator
         */
        private <M extends IModel<M>> Comparator<M> getComparator(M modelInstance) {
            return resolver.getComparator(modelInstance);
        }

        /**
         * Resolves comparator for given Model.
         */
        private interface ComparatorResolver {
            /**
             * Returns comparator.
             * 
             * @param modelInstance
             *            Model's instance
             * @return comparator
             */
            public <M extends IModel<M>> Comparator<M> getComparator(M modelInstance);
        }
    }
}
