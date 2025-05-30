/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import org.cactoos.BiFunc;
import org.cactoos.scalar.IoChecked;

/**
 * Func that doesn't throw checked {@link Exception}, but throws
 * {@link IOException} instead.
 *
 * <p>There is no thread-safety guarantee.
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @since 0.13
 */
public final class IoCheckedBiFunc<X, Y, Z> implements BiFunc<X, Y, Z> {

    /**
     * Original func.
     */
    private final BiFunc<X, Y, Z> func;

    /**
     * Ctor.
     * @param fnc Encapsulated func
     */
    public IoCheckedBiFunc(final BiFunc<X, Y, Z> fnc) {
        this.func = fnc;
    }

    @Override
    public Z apply(final X first, final Y second) throws IOException {
        return new IoChecked<>(
            () -> this.func.apply(first, second)
        ).value();
    }

}
