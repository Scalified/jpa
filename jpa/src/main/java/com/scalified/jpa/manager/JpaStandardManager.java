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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaStandardManager implements JpaManager {

	protected final EntityManager em;

	public JpaStandardManager(EntityManager em) {
		this.em = em;
	}

	public <T, K> T find(Class<T> entityClass, K primaryKey) {
		return em.find(entityClass, primaryKey);
	}

	@Override
	public <T, R> R find(CriteriaFunction<T> criteriaFunction, ResultFunction<T, R> resultFunction) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaFunction.apply(builder);
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		return resultFunction.apply(query);
	}

	@Override
	public <T> long count(Class<T> entityClass) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		criteriaQuery.select(builder.count(criteriaQuery.from(entityClass)));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	@Override
	public <T> long count(Class<T> entityClass, ExpressionFunction<T> function) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(builder.count(criteriaQuery.from(entityClass))).where(function.apply(builder, root));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	@Override
	public <T> T insert(T entity) {
		em.persist(entity);
		em.flush();
		return entity;
	}

	@Override
	public <T> Collection<T> insert(Collection<T> entities) {
		entities.forEach(em::persist);
		em.flush();
		return entities;
	}

	@Override
	public <T> T update(T entity) {
		T merged = em.merge(entity);
		em.flush();
		return merged;
	}

	@Override
	public <T> Collection<T> update(Collection<T> entities) {
		Collection<T> merged = entities.stream()
				.map(em::merge)
				.collect(Collectors.toCollection(LinkedHashSet::new));
		em.flush();
		return merged;
	}

	@Override
	public <T> void delete(T entity) {
		em.remove(entity);
		em.flush();
	}

	@Override
	public <T> void delete(Collection<T> entities) {
		entities.forEach(em::remove);
		em.flush();
	}

	@Override
	public <T> void refresh(T entity) {
		em.refresh(entity);
	}

	@Override
	public <T> void refresh(Collection<T> entities) {
		entities.forEach(em::refresh);
	}

	@Override
	public <T> void detach(T entity) {
		em.detach(entity);
	}

	@Override
	public <T> void detach(Collection<T> entities) {
		entities.forEach(em::detach);
	}

	@Override
	public EntityManager em() {
		return em;
	}

}
