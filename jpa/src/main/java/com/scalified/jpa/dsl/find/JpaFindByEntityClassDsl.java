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

import com.scalified.jpa.function.ResultFunction;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * <b>DSL</b> for finding entities using previously defined entity class
 *
 * @author shell
 * @since 2018-02-06
 */
public interface JpaFindByEntityClassDsl<T> {

	/**
	 * Returns the entity found by its specified {@code key}
	 *
	 * @param primaryKey a primary key of an entity
	 * @param <K>        type of a primary key of an entity
	 * @return entity found by its specified {@code key}
	 */
	<K> T one(K primaryKey);

	/**
	 * Returns a list of all found entities by their class
	 *
	 * @return a list of all found entities
	 */
	List<T> list();

	/**
	 * Returns a set of all found entities by their class
	 *
	 * @return a set of all found entities
	 */
	Set<T> set();

	/**
	 * Returns some generic result by entity class after applying the
	 * specified {@code resultFunction}
	 *
	 * @param resultFunction a function to apply on result
	 * @param <R>            type of the generic result
	 * @return some generic result
	 */
	<R> R some(ResultFunction<T, R> resultFunction);

	/**
	 * Returns a stream of all found entities by their class
	 *
	 * @return a stream of all found entities
	 */
	Stream<T> stream();

	/**
	 * Returns a stream of all found entities by their class and the specified
	 * {@code chunkSize}
	 *
	 * @param chunkSize size of a chunk
	 * @return a stream of all found entities
	 */
	Stream<T> stream(int chunkSize);

	/**
	 * Returns the first found entity by class
	 *
	 * @return the first found entity
	 */
	Optional<T> first();

}
