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

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <b>DSL</b> for finding entities using previously defined {@link CriteriaFunction}
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JpaFindByCriteriaFunctionDsl<T> {

	/**
	 * Returns a list of all found entities using previously defined {@link CriteriaFunction}
	 *
	 * @return a list of all found entities
	 */
	List<T> all();

	/**
	 * Returns a set of unique found entities using previously defined {@link CriteriaFunction}
	 *
	 * @return a set of unique found entities
	 */
	Set<T> unique();

	/**
	 * Returns some generic result using previously defined {@link CriteriaFunction}
	 * and after applying the specified <code>resultFunction</code>
	 *
	 * @param resultFunction a function to apply on result
	 * @param <R>            type of generic result
	 * @return some generic result
	 */
	<R> R some(ResultFunction<T, R> resultFunction);

	/**
	 * Returns the first found entity using previously defined {@link CriteriaFunction}
	 *
	 * @return the first found entity
	 */
	Optional<T> first();

}
