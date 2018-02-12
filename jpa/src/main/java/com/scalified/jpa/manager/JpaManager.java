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
 * Declares available operations on entity used by <b>DSL</b>
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JpaManager {

	/**
	 * Returns an entity found by its <code>primaryKey</code>
	 *
	 * @param entityClass a class of a searched entity
	 * @param primaryKey  a primary key of a searched entity
	 * @param <T>         type of a searched entity
	 * @param <K>         type of a primary key of a searched entity
	 * @return entity object
	 */
	<T, K> T find(Class<T> entityClass, K primaryKey);

	/**
	 * Returns the generic result derived from applying <code>resultFunction</code>
	 *
	 * @param criteriaFunction a function to
	 * @param resultFunction   a function, which maps {@link javax.persistence.criteria.CriteriaBuilder}
	 *                         to a generic result
	 * @param <T>              type of an entity
	 * @param <R>              type of the result
	 * @return generic result object
	 */
	<T, R> R find(CriteriaFunction<T> criteriaFunction, ResultFunction<T, R> resultFunction);

	/**
	 * Returns the count of all entities with the specified <code>entityClass</code>
	 *
	 * @param entityClass a class of an entity
	 * @param <T>         type of an entity
	 * @return count of all entities
	 */
	<T> long count(Class<T> entityClass);

	/**
	 * Returns the count of entities with the specified <code>entityClass</code> filtered
	 * by the specified expression <code>function</code>
	 *
	 * @param entityClass a class of an entity
	 * @param function    an {@link ExpressionFunction} to apply filter
	 * @param <T>         type of an entity
	 * @return count of filtered entities
	 */
	<T> long count(Class<T> entityClass, ExpressionFunction<T> function);

	/**
	 * Inserts an entity object
	 * <p>
	 * Returns the inserted entity object
	 *
	 * @param entity an entity object to insert
	 * @param <T>    type of an entity
	 * @return inserted entity object
	 */
	<T> T insert(T entity);

	/**
	 * Inserts the collection of entities
	 * <p>
	 * Returns the collection of inserted entities
	 *
	 * @param entities a collection of entities to insert
	 * @param <T>      type of an entity
	 * @return a collection of inserted entities
	 */
	<T> Collection<T> insert(Collection<T> entities);

	/**
	 * Updates the entity
	 * <p>
	 * Returns the updated entity
	 *
	 * @param entity an entity object to update
	 * @param <T>    type of an entity
	 * @return updated entity object
	 */
	<T> T update(T entity);

	/**
	 * Updates the collection of entities
	 * <p>
	 * Returns the collection of updated entities
	 *
	 * @param entities the collection of updated entities
	 * @param <T>      type of an entity
	 * @return a collection of updated entities
	 */
	<T> Collection<T> update(Collection<T> entities);

	/**
	 * Deletes the entity
	 *
	 * @param entity an entity object to delete
	 * @param <T>    type of an entity
	 */
	<T> void delete(T entity);

	/**
	 * Deletes the collection of entities
	 *
	 * @param entities the collection of entities to delete
	 * @param <T>      type of an entity
	 */
	<T> void delete(Collection<T> entities);

	/**
	 * Refreshes the state of an entity
	 *
	 * @param entity an entity object to refresh the state of
	 * @param <T>    type of an entity
	 */
	<T> void refresh(T entity);

	/**
	 * Refreshes the state of each entity in the specified collection
	 *
	 * @param entities the collection of entities to refresh states of
	 * @param <T>      type of an entity
	 */
	<T> void refresh(Collection<T> entities);

	/**
	 * Detaches an entity from context
	 *
	 * @param entity an entity object to detach
	 * @param <T>    type of an entity
	 */
	<T> void detach(T entity);

	/**
	 * Detaches the each entity in the specified collection from context
	 *
	 * @param entities the collection of entities to detach
	 * @param <T>      type of an entity
	 */
	<T> void detach(Collection<T> entities);

	/**
	 * Returns the underlying {@link EntityManager}
	 *
	 * @return underlying {@link EntityManager} instance
	 */
	EntityManager em();

}
