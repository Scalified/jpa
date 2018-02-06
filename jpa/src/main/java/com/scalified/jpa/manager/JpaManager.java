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

package com.scalified.jpa.manager;

import com.scalified.jpa.function.CriteriaFunction;
import com.scalified.jpa.function.ExpressionFunction;
import com.scalified.jpa.function.ResultFunction;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JpaManager {

	<T, K> T find(Class<T> entityClass, K primaryKey);

	<T, R> R find(CriteriaFunction<T> criteriaFunction, ResultFunction<T, R> resultFunction);

	<T> long count(Class<T> entityClass);

	<T> long count(Class<T> entityClass, ExpressionFunction<T> function);

	<T> T insert(T entity);

	<T> Collection<T> insert(Collection<T> entities);

	<T> T update(T entity);

	<T> Collection<T> update(Collection<T> entities);

	<T> void delete(T entity);

	<T> void delete(Collection<T> entities);

	<T> void refresh(T entity);

	<T> void refresh(Collection<T> entities);

	<T> void detach(T entity);

	<T> void detach(Collection<T> entities);

	EntityManager em();

}
