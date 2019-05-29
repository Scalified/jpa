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

package com.scalified.jpa.dsl.from;

import com.scalified.jpa.function.ExpressionFunction;
import com.scalified.jpa.manager.JpaManager;

/**
 * A {@link JpaFromDsl} implementation
 *
 * @author shell
 * @since 2018-02-06
 */
public class JpaFromDslImpl<T> implements JpaFromDsl<T> {

	/**
	 * An underlying {@link JpaManager}
	 */
	private final JpaManager manager;

	/**
	 * An entity class
	 */
	private final Class<T> entityClass;

	/**
	 * Creates {@link JpaFromDslImpl} instance
	 *
	 * @param manager     an underlying {@link JpaManager}
	 * @param entityClass an entity class
	 */
	public JpaFromDslImpl(JpaManager manager, Class<T> entityClass) {
		this.manager = manager;
		this.entityClass = entityClass;
	}

	/**
	 * Returns count of all entities with previously defined class
	 *
	 * @return count of all entities with previously defined class
	 */
	@Override
	public long count() {
		return manager.count(entityClass);
	}

	/**
	 * Returns count of entities with previously defined class, filtered by
	 * the specified expression {@code function}
	 *
	 * @param function an expression {@code function} to filter entities
	 * @return count of entities with previously defined class, filtered by
	 * the specified expression {@code function}
	 */
	@Override
	public long count(ExpressionFunction<T> function) {
		return manager.count(entityClass, function);
	}

}
