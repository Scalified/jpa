/*
 * MIT License
 *
 * Copyright (c) 2019 Scalified
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

package com.scalified.jpa.repository;

import com.scalified.jpa.Jpa;

/**
 * An abstract repository to extend by concrete repositories
 *
 * <p>
 * Declares common repositories methods
 *
 * @author shell
 * @since 2019-08-17
 */
public abstract class AbstractRepository<T> {

	/**
	 * An underlying {@link Jpa} instance
	 */
	protected final Jpa jpa;

	/**
	 * Creates this instance
	 *
	 * @param jpa underlying {@link Jpa} instance
	 */
	protected AbstractRepository(Jpa jpa) {
		this.jpa = jpa;
	}

	/**
	 * Adds an entity object by calling {@code JPA} insert operation
	 *
	 * <p>
	 * Returns the added entity object
	 *
	 * @param entity an entity object to add
	 * @return added entity object
	 */
	public T add(T entity) {
		return jpa.entity(entity).insert();
	}

	/**
	 * Replaces an entity object by calling {@code JPA} update operation
	 *
	 * <p>
	 * Returns the replaced entity object
	 *
	 * @param entity an entity object to replace
	 * @return replaced entity object
	 */
	public T replace(T entity) {
		return jpa.entity(entity).update();
	}

	/**
	 * Removes an entity object by calling {@code JPA} delete operation
	 *
	 * @param entity an entity object to remove
	 */
	public void remove(T entity) {
		jpa.entity(entity).delete();
	}

}
