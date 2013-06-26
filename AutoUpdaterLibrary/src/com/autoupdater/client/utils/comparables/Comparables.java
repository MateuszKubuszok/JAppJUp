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
package com.autoupdater.client.utils.comparables;

/**
 * Contains methods useful in work with Comparable objects.
 */
public final class Comparables {
    /**
     * Static class.
     */
    private Comparables() {
    }

    /**
     * Null-safe comparison of one Comparable object against another.
     * <p>
     * Asserts that null is "the smallest" possible value, and as such during
     * sort should be placed first, before any other object:
     * </p>
     * 
     * <pre>
     * // obj, obj2 - not null
     * // nullObj - null
     * Comparables.compare(obj, obj2); // equals to obj.compareTo(obj2)
     * Comparables.compare(obj, obj); // == 0
     * Comparables.compare(nullObj, nullObj); // == 0
     * Comparables.compare(nullObj, obj); // &lt; 0
     * Comparables.compare(obj, nullObj); // &gt; 0
     * </pre>
     * 
     * <p>
     * Also makes assumption that objects are of the same type and have properly
     * implemented Comparable interface. Namely that both are of type: T
     * implements Comparable&lt;T&gt;.
     * </p>
     * 
     * @param obj1
     *            first of two objects to compare
     * @param obj2
     *            second of two objects to compare
     * @return 0 if objects are equal, negative if first is "smaller", positive
     *         otherwise
     */
    public static <A extends Comparable<A>, B extends A> int compare(A obj1, B obj2) {
        if (obj1 == null) {
            return obj2 == null ? 0 : -1;
        } else if (obj2 == null)
            return 1;
        return obj1.compareTo(obj2);
    }
}
