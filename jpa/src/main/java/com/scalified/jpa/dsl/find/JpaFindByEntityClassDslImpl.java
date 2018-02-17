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

package com.scalified.jpa.dsl.find;

import com.scalified.jpa.manager.JpaManager;

/**
 * A {@link JpaFindByEntityClassDsl} implementation
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaFindByEntityClassDslImpl<T> implements JpaFindByEntityClassDsl<T> {

	/**
	 * An underlying {@link JpaManager}
	 */
	private final JpaManager manager;

	/**
	 * An entity class
	 */
	private final Class<T> entityClass;

	/**
	 * Creates {@link JpaFindByEntityClassDslImpl} instance
	 *
	 * @param manager     an underlying {@link JpaManager}
	 * @param entityClass an entity class
	 */
	public JpaFindByEntityClassDslImpl(JpaManager manager, Class<T> entityClass) {
		this.manager = manager;
		this.entityClass = entityClass;
	}

	/**
	 * Returns the entity found by its specified <b>key</b>
	 *
	 * @param primaryKey a primary key of an entity
	 * @param <K>        type of a primary key of an entity
	 * @return entity found by its specified <b>key</b>
	 */
	@Override
	public <K> T one(K primaryKey) {
		return manager.find(entityClass, primaryKey);
	}

}
