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

package com.scalified.jpa.dsl.entities;

import java.util.Collection;

/**
 * <b>DSL</b> for working with collection of entities
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JpaEntitiesDsl<T extends Collection<T>> {

	/**
	 * Inserts the previously defined collection of entities
	 *
	 * @return inserted collection of entities
	 */
	Collection<T> insert();

	/**
	 * Updates the previously defined collection of entities
	 *
	 * @return updated collection of entities
	 */
	Collection<T> update();

	/**
	 * Deletes the previously defined collection of entities
	 */
	void delete();

	/**
	 * Refreshes the state of each entity in previously defined collection
	 */
	void refresh();

	/**
	 * Detaches each entity in previously defined collection from context
	 */
	void detach();

}
