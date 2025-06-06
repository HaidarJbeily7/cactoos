/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * A decorator of {@link Map} that tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 0.27
 */
@SuppressWarnings("PMD.TooManyMethods")
public class NoNulls<K, V> implements Map<K, V> {

    /**
     * The map.
     */
    private final Map<K, V> map;

    /**
     * Ctor.
     * @param origin The scalar
     */
    public NoNulls(final Map<K, V> origin) {
        this.map = origin;
    }

    @Override
    public final int size() {
        return this.map.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public final boolean containsKey(final Object key) {
        if (key == null) {
            throw new IllegalStateException(
                "Key at #containsKey(K) is NULL"
            );
        }
        return this.map.containsKey(key);
    }

    @Override
    public final boolean containsValue(final Object value) {
        if (value == null) {
            throw new IllegalStateException(
                "Value at #containsValue(K) is NULL"
            );
        }
        return this.map.containsValue(value);
    }

    @Override
    public final V get(final Object key) {
        if (key == null) {
            throw new IllegalStateException(
                "Key at #get(K) is NULL"
            );
        }
        final V value = this.map.get(key);
        if (value == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Value returned by #get(%s) is NULL",
                        key
                    )
                ).asString()
            );
        }
        return value;
    }

    @Override
    public final V put(final K key, final V value) {
        if (key == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Key at #put(K,%s) is NULL",
                        value
                    )
                ).asString()
            );
        }
        if (value == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Value at #put(%s,V) is NULL", key
                    )
                ).asString()
            );
        }
        final V result = this.map.put(key, value);
        if (result == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Value returned by #put(%s,%s) is NULL",
                        key, value
                    )
                ).asString()
            );
        }
        return result;
    }

    @Override
    public final V remove(final Object key) {
        if (key == null) {
            throw new IllegalStateException(
                "Key at #remove(K) is NULL"
            );
        }
        final V result = this.map.remove(key);
        if (result == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Value returned by #remove(%s) is NULL",
                        key
                    )
                ).asString()
            );
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void putAll(final Map<? extends K, ? extends V> items) {
        this.map.putAll(new NoNulls<>((Map<K, V>) items));
    }

    @Override
    public final void clear() {
        this.map.clear();
    }

    @Override
    public final Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public final Collection<V> values() {
        return new org.cactoos.collection.NoNulls<>(this.map.values());
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }
}
