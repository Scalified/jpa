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

package com.scalified.jpa;

import com.scalified.jpa.dsl.entities.JpaEntitiesDsl;
import com.scalified.jpa.dsl.entity.JpaEntityDsl;
import com.scalified.jpa.dsl.find.JpaFindByCriteriaFunctionDsl;
import com.scalified.jpa.dsl.find.JpaFindByEntityClassDsl;
import com.scalified.jpa.dsl.from.JpaFromDsl;
import com.scalified.jpa.function.CriteriaFunction;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Jpa {

	<T> JpaFindByEntityClassDsl<T> find(Class<T> entityClass);

	<T> JpaFindByCriteriaFunctionDsl<T> find(CriteriaFunction<T> function);

	<T> JpaFromDsl from(Class<T> entityClass);

	<T> JpaEntityDsl<T> entity(T entity);

	<T extends Collection<T>> JpaEntitiesDsl<T> entities(Collection<T> entities);

	EntityManager em();

}
