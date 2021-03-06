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
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

/**
 * Describes specifications based on specification pattern
 *
 * @author shell
 * @since 2018-02-17
 */
@FunctionalInterface
public interface Specification<T> {

	/**
	 * Returns {@code true} if the specified object matches the current {@link Specification},
	 * otherwise returns {@code false}
	 *
	 * @param what an object to check {@link Specification} matching
	 * @return {@code true} if the specified object matches the current {@link Specification},
	 * {@code false} otherwise
	 */
	default boolean isSatisfiedBy(T what) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Constructs the {@link Predicate} from the current {@link Specification}
	 *
	 * @param builder criteria builder
	 * @param root    root
	 * @return a {@link Predicate} constructed from the current specification
	 */
	Predicate toPredicate(CriteriaBuilder builder, Root<T> root);

	/**
	 * Returns the current {@link Specification} type
	 *
	 * @return current {@link Specification} type
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	default Class<T> getType() {
		return Arrays.stream(getClass().getGenericInterfaces())
				.filter(ParameterizedType.class::isInstance)
				.map(type -> (ParameterizedType) type)
				.filter(type -> type.getRawType() == Specification.class)
				.map(type -> type.getActualTypeArguments()[0])
				.filter(Class.class::isInstance)
				.map(type -> (Class) type)
				.findFirst()
				.orElseThrow(UnsupportedOperationException::new);
	}

}
