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

import java.util.List;

/**
 * A {@link JpaQueryDsl} implementation for raw SQL queries
 *
 * @author shell
 * @since 2018-08-18
 */
public class JpaQueryDslImpl<T> implements JpaQueryDsl<T> {

	/**
	 * An underlying {@link JpaManager}
	 */
	private final JpaManager manager;

	/**
	 * Raw SQL query
	 */
	private final String sql;

	/**
	 * Type of the result entities
	 */
	private final Class<T> entityClass;

	/**
	 * Creates {@link JpaQueryDslImpl} instance
	 *
	 * @param manager     an underlying {@link JpaManager}
	 * @param sql         raw SQL query
	 * @param entityClass class of the result entities
	 */
	public JpaQueryDslImpl(JpaManager manager, String sql, Class<T> entityClass) {
		this.manager = manager;
		this.sql = sql;
		this.entityClass = entityClass;
	}

	/**
	 * Returns a list of all results produced by stored procedure execution
	 *
	 * @return a list of all results
	 */
	@Override
	public List<T> list() {
		return manager.query(sql, entityClass);
	}

}
