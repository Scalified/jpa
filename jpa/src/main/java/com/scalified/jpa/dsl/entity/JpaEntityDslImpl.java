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

package com.scalified.jpa.dsl.entity;

import com.scalified.jpa.manager.JpaManager;

/**
 * A {@link JpaEntityDsl} implementation
 *
 * @author shell
 * @since 2018-02-06
 */
public class JpaEntityDslImpl<T> implements JpaEntityDsl<T> {

	/**
	 * An underlying {@link JpaManager}
	 */
	private final JpaManager manager;

	/**
	 * An entity to perform operations on
	 */
	private final T entity;

	/**
	 * Creates {@link JpaEntityDslImpl} instance
	 *
	 * @param manager an underlying {@link JpaManager}
	 * @param entity  entity to perform operations on
	 */
	public JpaEntityDslImpl(JpaManager manager, T entity) {
		this.manager = manager;
		this.entity = entity;
	}

	/**
	 * Inserts the previously defined entity
	 *
	 * @return inserted entity
	 */
	@Override
	public T insert() {
		return manager.insert(entity);
	}

	/**
	 * Updates the previously defined entity
	 *
	 * @return updated entity
	 */
	@Override
	public T update() {
		return manager.update(entity);
	}

	/**
	 * Deletes the previously defined entity
	 */
	@Override
	public void delete() {
		manager.delete(entity);
	}

	/**
	 * Refreshes the state of the previously defined entity
	 */
	@Override
	public void refresh() {
		manager.refresh(entity);
	}

	/**
	 * Detaches the previously defined entity from context
	 */
	@Override
	public void detach() {
		manager.detach(entity);
	}

}
