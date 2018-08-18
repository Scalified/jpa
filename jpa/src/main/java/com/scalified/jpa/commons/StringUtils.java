/*
 * MIT License
 *
 * Copyright (c) 2018 Scalified
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.scalified.jpa.commons;

import java.util.Iterator;
import java.util.Objects;

/**
 * Provides utility methods for {@link java.lang.String}
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class StringUtils {

	/**
	 * The empty String {@code ""}
	 */
	public static final String EMPTY = "";

	/**
	 * Joins the elements of the provided {@code Iterable} into
	 * a single String containing the provided elements
	 *
	 * <p>No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings
	 *
	 * @param iterable  the {@code Iterable} providing the values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static String join(final Iterable<?> iterable, final char separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), separator);
	}

	/**
	 * Joins the elements of the provided {@code Iterator} into
	 * a single String containing the provided elements
	 *
	 * <p>No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings
	 *
	 * @param iterator  the {@code Iterator} of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static String join(final Iterator<?> iterator, final char separator) {

		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		final Object first = iterator.next();
		if (!iterator.hasNext()) {
			return Objects.toString(first, EMPTY);
		}

		final StringBuilder buf = new StringBuilder(256);
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			buf.append(separator);
			final Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}

		return buf.toString();
	}

}
