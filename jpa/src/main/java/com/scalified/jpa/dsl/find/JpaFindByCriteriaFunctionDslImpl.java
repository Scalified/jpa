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

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaFindByCriteriaFunctionDslImpl<T> implements JpaFindByCriteriaFunctionDsl<T> {

	private final JpaManager manager;

	private final CriteriaFunction<T> function;

	public JpaFindByCriteriaFunctionDslImpl(JpaManager manager, CriteriaFunction<T> function) {
		this.manager = manager;
		this.function = function;
	}

	@Override
	public List<T> all() {
		return manager.find(function, TypedQuery::getResultList);
	}

	@Override
	public Set<T> unique() {
		return new LinkedHashSet<>(all());
	}

	@Override
	public <R> R some(ResultFunction<T, R> resultFunction) {
		return manager.find(function, resultFunction);
	}

	@Override
	public Optional<T> first() {
		return all().stream().findFirst();
	}

}
