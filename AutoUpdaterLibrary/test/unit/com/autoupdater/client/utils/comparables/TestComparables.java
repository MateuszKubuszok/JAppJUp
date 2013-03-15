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

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestComparables {
    @Test
    public void testCompare() {
        // given
        String nullString = null;
        String string1 = "a string";
        String string2 = "a string";
        String string3 = "z string";

        // when
        int test1 = Comparables.compare(nullString, nullString);
        int test2 = Comparables.compare(string1, string2);
        int test3 = Comparables.compare(string1, string3);
        int test4 = Comparables.compare(string3, string1);
        int test5 = Comparables.compare(nullString, string1);
        int test6 = Comparables.compare(string1, nullString);

        // then
        assertThat(test1).as("compare(Comparable,Comparable) should match 2 nulls as 0").isZero();
        assertThat(test2)
                .as("compare(Comparable,Comparable) should match 2 non nulls as first.compareTo(second)")
                .isZero();
        assertThat(test3)
                .as("compare(Comparable,Comparable) should match 2 non nulls as first.compareTo(second)")
                .isNegative();
        assertThat(test4)
                .as("compare(Comparable,Comparable) should match 2 non nulls as first.compareTo(second)")
                .isPositive();
        assertThat(test5).as(
                "compare(Comparable,Comparable) should match (null, non-null) as negative")
                .isNegative();
        assertThat(test6).as(
                "compare(Comparable,Comparable) should match (non-null, null) as positive")
                .isPositive();
    }
}
