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
import com.scalified.jpa.specification.Specification;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * A {@link JpaManager} decorator, which adds synchronization support
 * for entity write operations
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaSynchronizedManager implements JpaManager {

	/**
	 * An underlying {@link JpaManager}
	 */
	protected final JpaManager manager;

	/**
	 * Creates {@link JpaSynchronizedManager} instance
	 *
	 * @param manager an {@link JpaManager} to decorate
	 */
	public JpaSynchronizedManager(JpaManager manager) {
		this.manager = manager;
	}

	/**
	 * Returns an entity found by its <b>primaryKey</b>
	 *
	 * @param entityClass a class of a searched entity
	 * @param primaryKey  a primary key of a searched entity
	 * @param <T>         type of a searched entity
	 * @param <K>         type of a primary key of a searched entity
	 * @return entity object
	 */
	@Override
	public <T, K> T find(Class<T> entityClass, K primaryKey) {
		return manager.find(entityClass, primaryKey);
	}

	/**
	 * Returns the generic result found by the specified <b>criteriaFunction</b> and
	 * derived from applying the specified <b>resultFunction</b>
	 *
	 * @param criteriaFunction a function to find result
	 * @param resultFunction   a function, which maps {@link javax.persistence.criteria.CriteriaBuilder}
	 *                         to a generic result
	 * @param <T>              type of an entity
	 * @param <R>              type of the result
	 * @return generic result object
	 */
	@Override
	public <T, R> R find(CriteriaFunction<T> criteriaFunction, ResultFunction<T, R> resultFunction) {
		return manager.find(criteriaFunction, resultFunction);
	}

	/**
	 * Returns the <b>Stream</b> of generic results found by the specified <b>criteriaFunction</b>
	 *
	 * @param criteriaFunction a function to find result
	 * @param <T>              type of an entity
	 * @return <b>Stream</b> of generic results
	 */
	@Override
	public <T> Stream<T> find(CriteriaFunction<T> criteriaFunction) {
		return manager.find(criteriaFunction);
	}

	/**
	 * Returns the <b>Stream</b> of generic results found by the specified <b>criteriaFunction</b>
	 * which has the specified <b>chunkSize</b>
	 *
	 * @param criteriaFunction a function to find result
	 * @param chunkSize        size of chunk
	 * @param <T>              type of an entity
	 * @return <b>Stream</b> of generic results
	 */
	@Override
	public <T> Stream<T> find(CriteriaFunction<T> criteriaFunction, int chunkSize) {
		return manager.find(criteriaFunction, chunkSize);
	}

	/**
	 * Returns the generic result found by the specified <b>specification</b> and
	 * derived from applying the specified <b>resultFunction</b>
	 *
	 * @param specification  a specification to find result
	 * @param resultFunction a function, which maps {@link javax.persistence.criteria.CriteriaBuilder}
	 *                       to a generic result
	 * @param <T>            type of an entity
	 * @param <R>            type of the result
	 * @return generic result object
	 */
	@Override
	public <T, R> R find(Specification<T> specification, ResultFunction<T, R> resultFunction) {
		return manager.find(specification, resultFunction);
	}

	/**
	 * Returns the count of all entities with the specified <b>entityClass</b>
	 *
	 * @param entityClass a class of an entity
	 * @param <T>         type of an entity
	 * @return count of all entities
	 */
	@Override
	public <T> long count(Class<T> entityClass) {
		return manager.count(entityClass);
	}

	/**
	 * Returns the count of entities with the specified <b>entityClass</b> filtered
	 * by the specified expression <b>function</b>
	 *
	 * @param entityClass a class of an entity
	 * @param function    an {@link ExpressionFunction} to apply filter
	 * @param <T>         type of an entity
	 * @return count of filtered entities
	 */
	@Override
	public <T> long count(Class<T> entityClass, ExpressionFunction<T> function) {
		return manager.count(entityClass, function);
	}

	/**
	 * Inserts an entity object
	 * <p>
	 * Returns the inserted entity object
	 *
	 * @param entity an entity object to insert
	 * @param <T>    type of an entity
	 * @return inserted entity object
	 */
	@Override
	public synchronized <T> T insert(T entity) {
		return manager.insert(entity);
	}

	/**
	 * Inserts the collection of entities
	 * <p>
	 * Returns the collection of inserted entities
	 *
	 * @param entities a collection of entities to insert
	 * @param <T>      type of an entity
	 * @return a collection of inserted entities
	 */
	@Override
	public synchronized <T> Collection<T> insert(Collection<T> entities) {
		return manager.insert(entities);
	}

	/**
	 * Updates the entity
	 * <p>
	 * Returns the updated entity
	 *
	 * @param entity an entity object to update
	 * @param <T>    type of an entity
	 * @return updated entity object
	 */
	@Override
	public synchronized <T> T update(T entity) {
		return manager.update(entity);
	}

	/**
	 * Updates the collection of entities
	 * <p>
	 * Returns the collection of updated entities
	 *
	 * @param entities the collection of updated entities
	 * @param <T>      type of an entity
	 * @return a collection of updated entities
	 */
	@Override
	public synchronized <T> Collection<T> update(Collection<T> entities) {
		return manager.update(entities);
	}

	/**
	 * Deletes the entity
	 *
	 * @param entity an entity object to delete
	 * @param <T>    type of an entity
	 */
	@Override
	public synchronized <T> void delete(T entity) {
		manager.delete(entity);
	}

	/**
	 * Deletes the collection of entities
	 *
	 * @param entities the collection of entities to delete
	 * @param <T>      type of an entity
	 */
	@Override
	public synchronized <T> void delete(Collection<T> entities) {
		manager.delete(entities);
	}

	/**
	 * Refreshes the state of an entity
	 *
	 * @param entity an entity object to refresh the state of
	 * @param <T>    type of an entity
	 */
	@Override
	public <T> void refresh(T entity) {
		manager.refresh(entity);
	}

	/**
	 * Refreshes the state of each entity in the specified collection
	 *
	 * @param entities the collection of entities to refresh states of
	 * @param <T>      type of an entity
	 */
	@Override
	public <T> void refresh(Collection<T> entities) {
		manager.refresh(entities);
	}

	/**
	 * Detaches an entity from context
	 *
	 * @param entity an entity object to detach
	 * @param <T>    type of an entity
	 */
	@Override
	public <T> void detach(T entity) {
		manager.detach(entity);
	}

	/**
	 * Detaches the each entity in the specified collection from context
	 *
	 * @param entities the collection of entities to detach
	 * @param <T>      type of an entity
	 */
	@Override
	public <T> void detach(Collection<T> entities) {
		manager.detach(entities);
	}

	/**
	 * Returns the underlying {@link EntityManager}
	 *
	 * @return underlying {@link EntityManager} instance
	 */
	@Override
	public EntityManager em() {
		return manager.em();
	}

}
