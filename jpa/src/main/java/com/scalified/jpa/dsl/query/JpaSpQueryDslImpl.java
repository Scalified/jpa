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

package com.scalified.jpa.dsl.query;

import com.scalified.jpa.manager.JpaManager;
import com.scalified.jpa.sp.SpQuery;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link JpaSpQueryDsl} implementation
 *
 * @author shell
 * @since 2018-08-18
 */
public class JpaSpQueryDslImpl<T> implements JpaSpQueryDsl<T> {

	/**
	 * An underlying {@link JpaManager}
	 */
	private final JpaManager manager;

	/**
	 * {@link SpQuery} used to execute stored procedure
	 */
	private final SpQuery<T> query;

	/**
	 * Creates {@link JpaSpQueryDsl} instance
	 *
	 * @param manager an underlying {@link JpaManager}
	 * @param query   {@link SpQuery} used to execute stored procedure
	 */
	public JpaSpQueryDslImpl(JpaManager manager, SpQuery<T> query) {
		this.manager = manager;
		this.query = query;
	}

	/**
	 * Returns a list of all results produced by stored procedure execution
	 *
	 * @return a list of all results
	 */
	@Override
	public List<T> list() {
		return manager.query(query);
	}

	/**
	 * Returns a set of all results produced by stored procedure execution
	 *
	 * @return a set of all results
	 */
	@Override
	public Set<T> set() {
		return new LinkedHashSet<>(list());
	}

	/**
	 * Returns the first result produced by stored procedure execution
	 *
	 * @return the first result
	 */
	@Override
	public Optional<T> first() {
		return list().stream()
				.findFirst();
	}

}
