package com.autoupdater.client.utils.comparables;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.autoupdater.client.utils.comparables.Comparables;

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
