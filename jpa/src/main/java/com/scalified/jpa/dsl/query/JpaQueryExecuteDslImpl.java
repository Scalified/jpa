/*
 * MIT License
 *
 * Copyright (c) 2022 Scalified
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
 */

package com.scalified.jpa.dsl.query;

import com.scalified.jpa.manager.JpaManager;

/**
 * @author shell
 * @since 2022-04-09
 */
public class JpaQueryExecuteDslImpl implements JpaQueryExecuteDsl {

	/**
	 * An underlying {@link JpaManager}
	 */
	private final JpaManager manager;

	/**
	 * Raw SQL query
	 */
	private final String sql;

	/**
	 * Creates {@link JpaQueryExecuteDslImpl} instance
	 *
	 * @param manager an underlying {@link JpaManager}
	 * @param sql     raw SQL query
	 */
	public JpaQueryExecuteDslImpl(JpaManager manager, String sql) {
		this.manager = manager;
		this.sql = sql;
	}

	/**
	 * Executes query and returns the number of entities updated or deleted
	 *
	 * @return number of entities updated or deleted
	 */
	@Override
	public int execute() {
		return manager.query(sql);
	}

}
