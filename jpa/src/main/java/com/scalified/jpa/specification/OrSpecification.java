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
 * A {@link Specification} <b>or</b> clause combiner
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrSpecification<T> implements Specification<T> {

	/**
	 * Specifications to combine
	 */
	private final Collection<? extends Specification<T>> specifications;

	/**
	 * Creates {@link OrSpecification} instance
	 *
	 * @param specifications specifications to combine
	 */
	private OrSpecification(Collection<? extends Specification<T>> specifications) {
		this.specifications = specifications;
	}

	/**
	 * Returns <b>true</b> if the specified object matches any of the specified specifications,
	 * otherwise returns <b>false</b>
	 *
	 * @param what an object to check specifications matching
	 * @return <b>true</b> if the specified object matches any of the specified specifications,
	 * <b>false</b> otherwise
	 */
	@Override
	public boolean isSatisfiedBy(T what) {
		return specifications.stream()
				.anyMatch(specification -> specification.isSatisfiedBy(what));
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
		return builder.or(predicates);
	}

	/**
	 * Creates {@link OrSpecification} instance
	 *
	 * @param specifications specifications to combine
	 * @param <T>            entity type
	 * @param <S>            specification type
	 * @return {@link OrSpecification} instance
	 */
	@SafeVarargs
	public static <T, S extends Specification<T>> OrSpecification<T> of(S... specifications) {
		return new OrSpecification<>(Arrays.asList(specifications));
	}

}
