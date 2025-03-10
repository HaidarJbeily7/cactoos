/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class NoNullsTest {

    @Test
    void getThrowsErrorIfNull() {
        new Assertion<>(
            "must throw error if contains null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).get(1),
            new Throws<>(
                "Item #1 of [1, null, 3] is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void setThrowsErrorIfArgumentNull() {
        new Assertion<>(
            "must throw error if set null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).set(2, null),
            new Throws<>(
                "Item can't be NULL in #set(2,T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void setThrowsErrorIfPreviousValueNull() {
        final ArrayList<Integer> list = new ArrayList<>(1);
        list.add(null);
        new Assertion<>(
            "must throw error if previous value is null",
            () -> new NoNulls<>(list).set(0, 2),
            new Throws<>(
                "Result of #set(0,T) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void addThrowsErrorIfArgumentNull() {
        new Assertion<>(
            "must throw error if add null",
            () -> {
                new NoNulls<>(new ArrayList<>(1)).add(0, null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #add(0,T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void removeThrowsErrorIfValueNull() {
        final ArrayList<Integer> list = new ArrayList<>(1);
        list.add(null);
        new Assertion<>(
            "must throw error if removed value is null",
            () -> new NoNulls<>(list).remove(0),
            new Throws<>(
                "Result of #remove(0) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void getThrowsErrorIfListIteratorNextValueIsNullValue() {
        new Assertion<>(
            "must throw error if removed value in iterator is null",
            () -> new NoNulls<>(
                new ListOf<>(null, 2, 3)
            ).listIterator().next(),
            new Throws<>(
                "Next item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void getThrowsErrorIfListIteratorPreviousValueIsNullValue() {
        final List<Integer> list = new ArrayList<>(2);
        list.add(1);
        list.add(2);
        final ListIterator<Integer> listiterator = new NoNulls<>(
            list
        ).listIterator();
        listiterator.next();
        list.set(0, null);
        new Assertion<>(
            "must throw error if previous value in iterator is null",
            listiterator::previous,
            new Throws<>(
                "Previous item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void indexOfThrowsErrorIfArgumentIsNull() {
        new Assertion<>(
            "must throw error if searched value is null",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).indexOf(null),
            new Throws<>(
                "Item can't be NULL in #indexOf(T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void indexOfTest() {
        new Assertion<>(
            "must return first index",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).indexOf(2),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void lastIndexOfThrowsErrorIfArgumentIsNull() {
        new Assertion<>(
            "must throw error if searched value is null",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).lastIndexOf(null),
            new Throws<>(
                "Item can't be NULL in #lastIndexOf(T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void lastIndexOfTest() {
        new Assertion<>(
            "must return last index",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).lastIndexOf(2),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    void sizeTest() {
        new Assertion<>(
            "must return list size",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).size(),
            new IsEqual<>(5)
        ).affirm();
    }

    @Test
    void isEmptyTrueTest() {
        new Assertion<>(
            "must return true if list is empty",
            new NoNulls<>(
                new ListOf<>()
            ).isEmpty(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void isEmptyFalseTest() {
        new Assertion<>(
            "must return false if list is not empty",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).isEmpty(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void clearTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.clear();
        new Assertion<>(
            "must be empty",
            list.isEmpty(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void containsTrueTest() {
        new Assertion<>(
            "must return true if elements is in list",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(2),
            new IsTrue()
        ).affirm();
    }

    @Test
    void containsFalseTest() {
        new Assertion<>(
            "must return false if elements is not in list",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(4),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void setValidInputTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.set(1, 5);
        new Assertion<>(
            "must update the list",
            list.toArray(),
            new IsEqual<>(
                new Integer[]{1, 5, 3}
            )
        ).affirm();
    }

    @Test
    void addValidInputTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.add(1, 5);
        new Assertion<>(
            "must update the list",
            list.toArray(),
            new IsEqual<>(
                new Integer[]{1, 5, 2, 3}
            )
        ).affirm();
    }

    @Test
    void addAllTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        new Assertion<>(
            "must return true on success",
            list.addAll(new ListOf<>(4, 5, 6)),
            new IsTrue()
        ).affirm();
        new Assertion<>(
            "must add items to the end of the list",
            list,
            Matchers.contains(1, 2, 3, 4, 5, 6)
        ).affirm();
    }

    @Test
    void addAllWithIndexTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        new Assertion<>(
            "must return true on success",
            list.addAll(1, new ListOf<>(4, 5, 6)),
            new IsTrue()
        ).affirm();
        new Assertion<>(
            "must add items to list at specified index",
            list,
            Matchers.contains(1, 4, 5, 6, 2, 3)
        ).affirm();
    }

    @Test
    void removeAllChangedTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3, 4, 5, 6)
        );
        new Assertion<>(
            "must return true if list changed",
            list.removeAll(new ListOf<>(10, 4, 2, 9, -3)),
            new IsTrue()
        ).affirm();
        new Assertion<>(
            "must remove specified items",
            list,
            Matchers.contains(1, 3, 5, 6)
        ).affirm();
    }

    @Test
    void removeAllDidNotChangeTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3, 4, 5, 6)
        );
        new Assertion<>(
            "must return false if list did not change",
            list.removeAll(new ListOf<>(10, 0, 7, 9, -3)),
            new IsEqual<>(false)
        ).affirm();
        new Assertion<>(
            "list must be the same",
            list,
            Matchers.contains(1, 2, 3, 4, 5, 6)
        ).affirm();
    }
}
