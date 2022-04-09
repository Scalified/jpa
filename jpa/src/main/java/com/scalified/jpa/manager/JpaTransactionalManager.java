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
import com.scalified.jpa.sp.SpQuery;
import com.scalified.jpa.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A {@link JpaManager} decorator, which adds transaction support
 * for entity write operations
 *
 * @author shell
 * @since 2018-02-06
 */
public class JpaTransactionalManager implements JpaManager {

	/**
	 * An underlying {@link JpaManager}
	 */
	protected final JpaManager manager;

	/**
	 * Creates {@link JpaTransactionalManager} instance
	 *
	 * @param manager an {@link JpaManager} to decorate
	 */
	public JpaTransactionalManager(JpaManager manager) {
		this.manager = manager;
	}

	/**
	 * Returns an entity found by its {@code primaryKey}
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
	 * Returns the {@link List} of all generic results found by the specified
	 * {@code entityClass}
	 *
	 * @param entityClass a class of a searched entity
	 * @param <T>         type of the searched entity
	 * @return {@link List} of all generic results
	 */
	@Override
	public <T> List<T> find(Class<T> entityClass) {
		return manager.find(entityClass);
	}

	/**
	 * Returns the generic result found by the specified {@code entityClass}
	 * and derived from applying the specified {@code resultFunction}
	 *
	 * @param entityClass    a class of a searched entity
	 * @param resultFunction a function, which maps {@link CriteriaBuilder}
	 *                       to a generic result
	 * @param <T>            type of an entity
	 * @param <R>            type of the result
	 * @return generic result object
	 */
	@Override
	public <T, R> R find(Class<T> entityClass, ResultFunction<T, R> resultFunction) {
		return manager.find(entityClass, resultFunction);
	}

	/**
	 * Returns the {@link Stream} of generic results found by the specified {@code entityClass}
	 *
	 * @param entityClass a class of a searched entity
	 * @param <T>         type of the searched entity
	 * @return {@link Stream} of generic results
	 */
	@Override
	public <T> Stream<T> stream(Class<T> entityClass) {
		return manager.stream(entityClass);
	}

	/**
	 * Returns the {@link Stream} of generic results found by the specified {@code entityClass},
	 * which has the specified {@code chunkSize}
	 *
	 * @param entityClass a class of a searched entity
	 * @param chunkSize   size of chunk
	 * @param <T>         type of the searched entity
	 * @return {@link Stream} of generic results
	 */
	@Override
	public <T> Stream<T> stream(Class<T> entityClass, int chunkSize) {
		return manager.stream(entityClass, chunkSize);
	}

	/**
	 * Returns the generic result found by the specified {@code criteriaFunction} and
	 * derived from applying the specified {@code resultFunction}
	 *
	 * @param criteriaFunction a function to find result
	 * @param resultFunction   a function, which maps {@link CriteriaBuilder}
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
	 * Returns the {@link Stream} of generic results found by the specified {@code criteriaFunction}
	 *
	 * @param criteriaFunction a function to find result
	 * @param <T>              type of an entity
	 * @return {@link Stream} of generic results
	 */
	@Override
	public <T> Stream<T> stream(CriteriaFunction<T> criteriaFunction) {
		return manager.stream(criteriaFunction);
	}

	/**
	 * Returns the {@link Stream} of generic results found by the specified {@code criteriaFunction},
	 * which has the specified {@code chunkSize}
	 *
	 * @param criteriaFunction a function to find result
	 * @param chunkSize        size of chunk
	 * @param <T>              type of an entity
	 * @return {@link Stream} of generic results
	 */
	@Override
	public <T> Stream<T> stream(CriteriaFunction<T> criteriaFunction, int chunkSize) {
		return manager.stream(criteriaFunction, chunkSize);
	}

	/**
	 * Returns the generic result found by the specified {@code specification} and
	 * derived from applying the specified {@code resultFunction}
	 *
	 * @param specification  a specification to find result
	 * @param resultFunction a function, which maps {@link CriteriaBuilder}
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
	 * Executes query and returns the number of entities updated or deleted
	 *
	 * @param sql raw SQL query
	 * @return number of entities updated or deleted
	 */
	@Override
	public int query(String sql) {
		return manager.query(sql);
	}

	/**
	 * Returns the list of entities as a result of raw {@code sql} query execution
	 *
	 * @param sql         raw SQL query
	 * @param entityClass type of the result entities
	 * @param <T>         type of the result
	 * @return the list of entities
	 */
	@Override
	public <T> List<T> query(String sql, Class<T> entityClass) {
		return manager.query(sql, entityClass);
	}

	/**
	 * Returns the list of entities as a result of stored procedure execution
	 * built from the specified {@code spQuery}
	 *
	 * @param spQuery stored procedure configuration object
	 * @param <T>     type of the result
	 * @return the list of entities
	 */
	@Override
	public <T> List<T> query(SpQuery<T> spQuery) {
		return manager.query(spQuery);
	}

	/**
	 * Returns the count of all entities with the specified {@code entityClass}
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
	 * Returns the count of entities with the specified {@code entityClass} filtered
	 * by the specified expression {@code function}
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
	 *
	 * <p>
	 * Returns the inserted entity object
	 *
	 * @param entity an entity object to insert
	 * @param <T>    type of an entity
	 * @return inserted entity object
	 */
	@Override
	public <T> T insert(T entity) {
		return applyInTransaction(transaction -> manager.insert(entity));
	}

	/**
	 * Inserts the collection of entities
	 *
	 * <p>
	 * Returns the collection of inserted entities
	 *
	 * @param entities a collection of entities to insert
	 * @param <T>      type of an entity
	 * @return a collection of inserted entities
	 */
	@Override
	public <T> Collection<T> insert(Collection<T> entities) {
		return applyInTransaction(transaction -> manager.insert(entities));
	}

	/**
	 * Updates the entity
	 *
	 * <p>
	 * Returns the updated entity
	 *
	 * @param entity an entity object to update
	 * @param <T>    type of an entity
	 * @return updated entity object
	 */
	@Override
	public <T> T update(T entity) {
		return applyInTransaction(transaction -> manager.update(entity));
	}

	/**
	 * Updates the collection of entities
	 *
	 * <p>
	 * Returns the collection of updated entities
	 *
	 * @param entities the collection of updated entities
	 * @param <T>      type of an entity
	 * @return a collection of updated entities
	 */
	@Override
	public <T> Collection<T> update(Collection<T> entities) {
		return applyInTransaction(transaction -> manager.update(entities));
	}

	/**
	 * Deletes the entity
	 *
	 * @param entity an entity object to delete
	 * @param <T>    type of an entity
	 */
	@Override
	public <T> void delete(T entity) {
		acceptInTransaction(transaction -> manager.delete(entity));
	}

	/**
	 * Deletes the collection of entities
	 *
	 * @param entities the collection of entities to delete
	 * @param <T>      type of an entity
	 */
	@Override
	public <T> void delete(Collection<T> entities) {
		acceptInTransaction(transaction -> manager.delete(entities));
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

	/**
	 * Applies the specified <b>function</b> in a new transaction
	 *
	 * <p>
	 * Returns generic result
	 *
	 * @param function a function to execute
	 * @param <R>      type of the result
	 * @return result of function execution
	 */
	private <R> R applyInTransaction(Function<EntityTransaction, R> function) {
		EntityTransaction transaction = manager.em().getTransaction();
		transaction.begin();
		R result = function.apply(transaction);
		transaction.commit();
		return result;
	}

	/**
	 * Accepts the specified <b>consumer</b> in a new transaction
	 *
	 * @param consumer a consumer to accept
	 */
	private void acceptInTransaction(Consumer<EntityTransaction> consumer) {
		EntityTransaction transaction = manager.em().getTransaction();
		transaction.begin();
		consumer.accept(transaction);
		transaction.commit();
	}

}
