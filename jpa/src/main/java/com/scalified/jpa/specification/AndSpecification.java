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

package com.scalified.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collection;

/**
 * A {@link Specification} <b>and</b> clause combiner
 *
 * @author shell
 * @since 2018-02-17
 */
public class AndSpecification<T> implements Specification<T> {

	/**
	 * Specifications to combine
	 */
	private final Collection<? extends Specification<T>> specifications;

	/**
	 * Creates {@link AndSpecification} instance
	 *
	 * @param specifications specifications to combine
	 */
	private AndSpecification(Collection<? extends Specification<T>> specifications) {
		this.specifications = specifications;
	}

	/**
	 * Creates {@link AndSpecification} instance
	 *
	 * @param specifications specifications to combine
	 * @param <T>            entity type
	 * @param <S>            specification type
	 * @return {@link AndSpecification} instance
	 */
	@SafeVarargs
	public static <T, S extends Specification<T>> AndSpecification<T> of(S... specifications) {
		return new AndSpecification<>(Arrays.asList(specifications));
	}

	/**
	 * Returns {@code true} if the specified object matches all of the specified specifications,
	 * otherwise returns {@code false}
	 *
	 * @param what an object to check specifications matching
	 * @return {@code true} if the specified object matches all of the specified specifications,
	 * {@code false} otherwise
	 */
	@Override
	public boolean isSatisfiedBy(T what) {
		return specifications.stream()
				.allMatch(specification -> specification.isSatisfiedBy(what));
	}

	/**
	 * Constructs the {@link Predicate} from the specified specifications
	 *
	 * @param builder criteria builder
	 * @param root    root
	 * @return a {@link Predicate} constructed from the specified specifications
	 */
	@Override
	public Predicate toPredicate(CriteriaBuilder builder, Root<T> root) {
		Predicate[] predicates = specifications.stream()
				.map(specification -> specification.toPredicate(builder, root))
				.toArray(Predicate[]::new);
		return builder.and(predicates);
	}

}
