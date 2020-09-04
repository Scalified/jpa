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
import com.scalified.jpa.dsl.find.JpaFindBySpecificationDsl;
import com.scalified.jpa.dsl.from.JpaFromDsl;
import com.scalified.jpa.dsl.query.JpaQueryDsl;
import com.scalified.jpa.function.CriteriaFunction;
import com.scalified.jpa.sp.SpQuery;
import com.scalified.jpa.specification.Specification;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * The entry point to start working with <b>JPA DSL</b>
 *
 * <p>
 * Declares root <b>DSL</b> methods within <b>DSL</b> call chain
 *
 * @author shell
 * @since 2018-02-06
 */
public interface Jpa {

	/**
	 * Returns {@link JpaFindByEntityClassDsl} object, which provides the next
	 * <b>DSL</b> methods within <b>DSL</b> call chain to find entities by their class
	 *
	 * @param entityClass class of an entity
	 * @param <T>         type of an entity
	 * @return {@link JpaFindByEntityClassDsl} object
	 */
	<T> JpaFindByEntityClassDsl<T> find(Class<T> entityClass);

	/**
	 * Returns {@link JpaFindByCriteriaFunctionDsl} object, which provides the next
	 * <b>DSL</b> methods within <b>DSL</b> call chain to find entities using
	 * {@link CriteriaFunction}
	 *
	 * @param function {@link CriteriaFunction} object
	 * @param <T>      type of an entity
	 * @return {@link JpaFindByCriteriaFunctionDsl} object
	 */
	<T> JpaFindByCriteriaFunctionDsl<T> find(CriteriaFunction<T> function);

	/**
	 * Returns {@link JpaFindBySpecificationDsl} object, which provides the next
	 * <b>DSL</b> methods within <b>DSL</b> call chain to find entities using
	 * {@link Specification}
	 *
	 * @param specification {@link Specification} object
	 * @param <T>           type of an entity
	 * @return {@link JpaFindBySpecificationDsl} object
	 */
	<T> JpaFindBySpecificationDsl<T> find(Specification<T> specification);

	/**
	 * Returns {@link JpaQueryDsl} object, which provides the next <b>DSL</b>
	 * methods within <b>DSL</b> call chain to execute raw SQL query
	 *
	 * @param sql         raw SQL query
	 * @param entityClass class of the result entities
	 * @param <T>         type of the result entities
	 * @return {@link JpaQueryDsl} object
	 */
	<T> JpaQueryDsl<T> query(String sql, Class<T> entityClass);

	/**
	 * Returns {@link JpaQueryDsl} object, which provides the next <b>DSL</b>
	 * methods within <b>DSL</b> call chain to execute stored procedure
	 *
	 * @param spQuery stored procedure configuration object
	 * @param <T>     type of the result
	 * @return {@link JpaQueryDsl} object
	 */
	<T> JpaQueryDsl<T> query(SpQuery<T> spQuery);

	/**
	 * Returns {@link JpaFromDsl} object, which provides the next <b>DSL</b> methods
	 * within <b>DSL</b> call chain derived from entity class
	 *
	 * @param entityClass class of an entity
	 * @param <T>         type of an entity
	 * @return {@link JpaFromDsl} object
	 */
	<T> JpaFromDsl<T> from(Class<T> entityClass);

	/**
	 * Returns {@link JpaEntityDsl} object, which provides the next <b>DSL</b> methods
	 * within <b>DSL</b> call chain derived from entity object
	 *
	 * @param entity an entity instance
	 * @param <T>    type of an entity
	 * @return {@link JpaEntityDsl} object
	 */
	<T> JpaEntityDsl<T> entity(T entity);

	/**
	 * Returns {@link JpaEntitiesDsl} object, which provides the next <b>DSL</b> methods
	 * within <b>DSL</b> call chain derived from collection of entity objects
	 *
	 * @param entities a collection of entity instances
	 * @param <T>      type of an entity
	 * @param <K>      type of the entities collection
	 * @return {@link JpaEntitiesDsl} object
	 */
	<T, K extends Collection<T>> JpaEntitiesDsl<T> entities(K entities);

	/**
	 * Returns the underlying {@link EntityManager}
	 *
	 * @return underlying {@link EntityManager}
	 */
	EntityManager em();

}
