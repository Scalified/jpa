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
import com.scalified.jpa.manager.JpaManager;
import com.scalified.jpa.specification.Specification;

import javax.persistence.TypedQuery;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link JpaFindBySpecificationDsl} implementation
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaFindBySpecificationDslImpl<T> implements JpaFindBySpecificationDsl<T> {

	/**
	 * An underlying {@link JpaManager}
	 */
	private final JpaManager manager;

	/**
	 * {@link Specification} used to find result
	 */
	private final Specification<T> specification;

	/**
	 * Creates {@link JpaFindBySpecificationDslImpl} instance
	 *
	 * @param manager       an underlying {@link JpaManager}
	 * @param specification {@link Specification} used to find result
	 */
	public JpaFindBySpecificationDslImpl(JpaManager manager, Specification<T> specification) {
		this.manager = manager;
		this.specification = specification;
	}

	/**
	 * Returns a list of all found entities using {@link Specification}
	 *
	 * @return a list of all found entities
	 */
	@Override
	public List<T> list() {
		return manager.find(specification, TypedQuery::getResultList);
	}

	/**
	 * Returns a set of unique found entities using {@link Specification}
	 *
	 * @return a set of unique found entities
	 */
	@Override
	public Set<T> set() {
		return new LinkedHashSet<>(list());
	}

	/**
	 * Returns some generic result using {@link Specification}
	 * and after applying the specified <b>resultFunction</b>
	 *
	 * @param resultFunction a function to apply on result
	 * @param <R>            type of generic result
	 * @return some generic result
	 */
	@Override
	public <R> R some(ResultFunction<T, R> resultFunction) {
		return manager.find(specification, resultFunction);
	}

	/**
	 * Returns the first found entity using {@link Specification}
	 *
	 * @return the first found entity
	 */
	@Override
	public Optional<T> first() {
		return list().stream().findFirst();
	}

}
