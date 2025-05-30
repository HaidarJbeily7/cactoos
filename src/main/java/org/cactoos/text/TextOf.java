/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Iterator;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * TextOf
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.12
 * @checkstyle ClassFanOutComplexityCheck (1000 lines)
 */
public final class TextOf extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param input The Input
     */
    public TextOf(final Input input) {
        this(new BytesOf(input));
    }

    /**
     * Ctor.
     * @param url The URL
     * @since 0.16
     */
    public TextOf(final URL url) {
        this(new InputOf(url));
    }

    /**
     * Ctor.
     * @param uri The URI
     * @since 0.16
     */
    public TextOf(final URI uri) {
        this(new InputOf(uri));
    }

    /**
     * Ctor.
     * @param path The Input
     * @since 0.13
     */
    public TextOf(final Path path) {
        this(new InputOf(path));
    }

    /**
     * Ctor.
     * @param file The Input
     * @since 0.13
     */
    public TextOf(final File file) {
        this(new InputOf(file));
    }

    /**
     * Ctor.
     *
     * @param input The input
     * @param max Max length of the buffer for reading
     */
    public TextOf(final Input input, final int max) {
        this(input, max, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param input The Input
     * @param cset The Charset
     */
    public TextOf(final Input input, final Charset cset) {
        this(new BytesOf(input), cset);
    }

    /**
     * Ctor.
     *
     * @param input The Input
     * @param cset The Charset
     */
    public TextOf(final Input input, final String cset) {
        this(new BytesOf(input), cset);
    }

    /**
     * Ctor.
     *
     * @param input The input
     * @param max Max length of the buffer for reading
     * @param cset The Charset
     */
    public TextOf(final Input input, final int max, final Charset cset) {
        this(new BytesOf(input, max), cset);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     */
    public TextOf(final Reader rdr) {
        this(new BytesOf(rdr));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param cset Charset
     */
    public TextOf(final Reader rdr, final Charset cset) {
        this(new BytesOf(rdr, cset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param max Buffer size
     * @param cset Charset
     */
    public TextOf(final Reader rdr, final int max, final Charset cset) {
        this(new BytesOf(rdr, cset, max));
    }

    /**
     * Ctor.
     *
     * @param str The CharSequence
     */
    public TextOf(final CharSequence str) {
        this(new BytesOf(str));
    }

    /**
     * Ctor.
     *
     * @param str The CharSequence
     * @param cset The Charset
     */
    public TextOf(final CharSequence str, final Charset cset) {
        this(new BytesOf(str, cset), cset);
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     */
    public TextOf(final char... chars) {
        this(new BytesOf(chars));
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     * @param cset The charset
     */
    public TextOf(final char[] chars, final Charset cset) {
        this(new BytesOf(chars, cset));
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     */
    public TextOf(final Throwable error) {
        this(new BytesOf(error));
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     * @param charset Charset
     * @since 0.29
     */
    public TextOf(final Throwable error, final Charset charset) {
        this(new BytesOf(error, charset));
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     * @param charset Charset
     * @since 0.29
     */
    public TextOf(final Throwable error, final CharSequence charset) {
        this(new BytesOf(error, charset));
    }

    /**
     * Ctor.
     * @param strace The stacktrace to serialize
     * @since 0.29
     */
    public TextOf(final StackTraceElement... strace) {
        this(new BytesOf(strace));
    }

    /**
     * Ctor.
     * @param strace The stacktrace to serialize
     * @param charset Charset
     * @since 0.29
     */
    public TextOf(final StackTraceElement[] strace, final Charset charset) {
        this(new BytesOf(strace, charset));
    }

    /**
     * Ctor.
     * @param strace The stacktrace to serialize
     * @param charset Charset
     * @since 0.29
     */
    public TextOf(final StackTraceElement[] strace,
        final CharSequence charset) {
        this(new BytesOf(strace, charset));
    }

    /**
     * Ctor.
     *
     * @param bytes The array of bytes
     */
    public TextOf(final byte... bytes) {
        this(new BytesOf(bytes));
    }

    /**
     * Ctor.
     *
     * @param bytes The Bytes
     */
    public TextOf(final Bytes bytes) {
        this(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param bytes The Bytes
     * @param cset The Charset
     */
    public TextOf(final Bytes bytes, final Charset cset) {
        this(
            new TextOfScalar(() -> new String(bytes.asBytes(), cset))
        );
    }

    /**
     * Ctor.
     *
     * @param bytes The Bytes
     * @param cset The Charset
     */
    public TextOf(final Bytes bytes, final String cset) {
        this(
            new TextOfScalar(() -> new String(bytes.asBytes(), cset))
        );
    }

    /**
     * Ctor.
     *
     * @param input The String
     */
    public TextOf(final String input) {
        this(input, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param iterator The iterable to convert to string
     */
    public TextOf(final Iterator<Character> iterator) {
        this(new IterableOf<>(iterator));
    }

    /**
     * Ctor.
     *
     * @param iterable The iterable to convert to string
     */
    public TextOf(final Iterable<Character> iterable) {
        super(
            new Concatenated(
                new Mapped<>(
                    TextOf::new,
                    iterable
                )
            )
        );
    }

    /**
     * Ctor.
     *
     * @param input The InputStream where the text is read from
     * @since 0.21
     */
    public TextOf(final InputStream input) {
        this(new InputOf(new InputStreamReader(input, StandardCharsets.UTF_8)));
    }

    /**
     * Ctor.
     *
     * @param scalar The Scalar of String
     */
    public TextOf(final Scalar<? extends CharSequence> scalar) {
        this(new TextOfScalar(scalar));
    }

    /**
     * Ctor.
     *
     * @param text Text
     */
    private TextOf(final Text text) {
        super(text);
    }
}
