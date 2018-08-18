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

import com.scalified.jpa.commons.EntitySpliterator;
import com.scalified.jpa.function.CriteriaFunction;
import com.scalified.jpa.function.ExpressionFunction;
import com.scalified.jpa.function.ResultFunction;
import com.scalified.jpa.sp.SpQuery;
import com.scalified.jpa.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A standard implementation of the {@link JpaManager}
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaStandardManager implements JpaManager {

	/**
	 * An underlying {@link EntityManager}
	 */
	protected final EntityManager em;

	/**
	 * Creates {@link JpaStandardManager} instance
	 *
	 * @param em an {@link EntityManager} instance
	 */
	public JpaStandardManager(EntityManager em) {
		this.em = em;
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
	public <T, K> T find(Class<T> entityClass, K primaryKey) {
		return em.find(entityClass, primaryKey);
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
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaFunction.apply(builder);
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		return resultFunction.apply(query);
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
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaFunction.apply(builder);
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		return StreamSupport.stream(new EntitySpliterator<>(query), false);
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
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaFunction.apply(builder);
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		return StreamSupport.stream(new EntitySpliterator<>(query, chunkSize), false);
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
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(specification.getType());
		Root<T> root = criteriaQuery.from(specification.getType());
		Predicate predicate = specification.toPredicate(builder, root);
		TypedQuery<T> query = em.createQuery(criteriaQuery.where(predicate));
		return resultFunction.apply(query);
	}

	/**
	 * Returns the raw result as a list containing column values in
	 * array of objects produced by stored procedure execution built
	 * from the specified <b>spQuery</b>
	 *
	 * @param spQuery stored procedure configuration object
	 * @param <T>     type of result
	 * @return the raw result as a list containing column values in
	 * array of objects
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<Object[]> query(SpQuery<T> spQuery) {
		StoredProcedureQuery query;

		if (Objects.nonNull(spQuery.getResultClasses())) {
			query = em.createStoredProcedureQuery(spQuery.getName(), spQuery.getResultClasses());
		} else if (Objects.nonNull(spQuery.getResultMappings())) {
			query = em.createStoredProcedureQuery(spQuery.getName(), spQuery.getResultMappings());
		} else {
			query = em.createStoredProcedureQuery(spQuery.getName());
		}

		spQuery.getParams()
				.forEach(param -> {
					if (param.getMode() == ParameterMode.REF_CURSOR) {
						query.registerStoredProcedureParameter(param.getName(), void.class, ParameterMode.REF_CURSOR);
					} else if (Objects.nonNull(param.getValue())) {
						query.registerStoredProcedureParameter(
								param.getName(), param.getValue().getClass(), param.getMode()
						);
						if (param.getMode() == ParameterMode.IN) {
							query.setParameter(param.getName(), param.getValue());
						}
					}
				});

		return query.execute() ? query.getResultList() : Collections.emptyList();
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
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		criteriaQuery.select(builder.count(criteriaQuery.from(entityClass)));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
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
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(builder.count(criteriaQuery.from(entityClass))).where(function.apply(builder, root));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
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
	public <T> T insert(T entity) {
		em.persist(entity);
		em.flush();
		return entity;
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
	public <T> Collection<T> insert(Collection<T> entities) {
		entities.forEach(em::persist);
		em.flush();
		return entities;
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
	public <T> T update(T entity) {
		T merged = em.merge(entity);
		em.flush();
		return merged;
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
	public <T> Collection<T> update(Collection<T> entities) {
		Collection<T> merged = entities.stream()
				.map(em::merge)
				.collect(Collectors.toCollection(LinkedHashSet::new));
		em.flush();
		return merged;
	}

	/**
	 * Deletes the entity
	 *
	 * @param entity an entity object to delete
	 * @param <T>    type of an entity
	 */
	@Override
	public <T> void delete(T entity) {
		em.remove(entity);
		em.flush();
	}

	/**
	 * Deletes the collection of entities
	 *
	 * @param entities the collection of entities to delete
	 * @param <T>      type of an entity
	 */
	@Override
	public <T> void delete(Collection<T> entities) {
		entities.forEach(em::remove);
		em.flush();
	}

	/**
	 * Refreshes the state of an entity
	 *
	 * @param entity an entity object to refresh the state of
	 * @param <T>    type of an entity
	 */
	@Override
	public <T> void refresh(T entity) {
		em.refresh(entity);
	}

	/**
	 * Refreshes the state of each entity in the specified collection
	 *
	 * @param entities the collection of entities to refresh states of
	 * @param <T>      type of an entity
	 */
	@Override
	public <T> void refresh(Collection<T> entities) {
		entities.forEach(em::refresh);
	}

	/**
	 * Detaches an entity from context
	 *
	 * @param entity an entity object to detach
	 * @param <T>    type of an entity
	 */
	@Override
	public <T> void detach(T entity) {
		em.detach(entity);
	}

	/**
	 * Detaches the each entity in the specified collection from context
	 *
	 * @param entities the collection of entities to detach
	 * @param <T>      type of an entity
	 */
	@Override
	public <T> void detach(Collection<T> entities) {
		entities.forEach(em::detach);
	}

	/**
	 * Returns the underlying {@link EntityManager}
	 *
	 * @return underlying {@link EntityManager} instance
	 */
	@Override
	public EntityManager em() {
		return em;
	}

}
