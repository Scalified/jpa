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

import com.scalified.jpa.function.CriteriaFunction;
import com.scalified.jpa.function.ResultFunction;
import com.scalified.jpa.manager.JpaManager;

import javax.persistence.TypedQuery;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A {@link JpaFindByCriteriaFunctionDsl} implementation
 *
 * @author shell
 * @since 2018-02-06
 */
public class JpaFindByCriteriaFunctionDslImpl<T> implements JpaFindByCriteriaFunctionDsl<T> {

	/**
	 * An underlying {@link JpaManager}
	 */
	private final JpaManager manager;

	/**
	 * {@link CriteriaFunction} used to find result
	 */
	private final CriteriaFunction<T> function;

	/**
	 * Creates {@link JpaFindByCriteriaFunctionDsl} instance
	 *
	 * @param manager  an underlying {@link JpaManager}
	 * @param function {@link CriteriaFunction} used to find result
	 */
	public JpaFindByCriteriaFunctionDslImpl(JpaManager manager, CriteriaFunction<T> function) {
		this.manager = manager;
		this.function = function;
	}

	/**
	 * Returns a list of all found entities using previously defined {@link CriteriaFunction}
	 *
	 * @return a list of all found entities
	 */
	@Override
	public List<T> list() {
		return manager.find(function, TypedQuery::getResultList);
	}

	/**
	 * Returns a set of all found entities using previously defined {@link CriteriaFunction}
	 *
	 * @return a set of all found entities
	 */
	@Override
	public Set<T> set() {
		return new LinkedHashSet<>(list());
	}

	/**
	 * Returns some generic result using previously defined {@link CriteriaFunction}
	 * and after applying the specified {@code resultFunction}
	 *
	 * @param resultFunction a function to apply on result
	 * @param <R>            type of generic result
	 * @return some generic result
	 */
	@Override
	public <R> R some(ResultFunction<T, R> resultFunction) {
		return manager.find(function, resultFunction);
	}

	/**
	 * Returns a stream of all found entities using previously defined {@link CriteriaFunction}
	 *
	 * @return a stream of all found entities
	 */
	@Override
	public Stream<T> stream() {
		return manager.stream(function);
	}

	/**
	 * Returns a stream of all found entities using previously defined {@link CriteriaFunction}
	 * and the specified {@code chunkSize}
	 *
	 * @param chunkSize size of a chunk
	 * @return a stream of all found entities
	 */
	@Override
	public Stream<T> stream(int chunkSize) {
		return manager.stream(function, chunkSize);
	}

	/**
	 * Returns the first found entity using previously defined {@link CriteriaFunction}
	 *
	 * @return the first found entity
	 */
	@Override
	public Optional<T> first() {
		return list().stream().findFirst();
	}

}
