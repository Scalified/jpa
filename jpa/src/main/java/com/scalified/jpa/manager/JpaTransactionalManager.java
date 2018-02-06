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
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaTransactionalManager implements JpaManager {

	protected final JpaManager manager;

	public JpaTransactionalManager(JpaManager manager) {
		this.manager = manager;
	}

	@Override
	public <T, K> T find(Class<T> entityClass, K primaryKey) {
		return manager.find(entityClass, primaryKey);
	}

	@Override
	public <T, R> R find(CriteriaFunction<T> criteriaFunction, ResultFunction<T, R> resultFunction) {
		return manager.find(criteriaFunction, resultFunction);
	}

	@Override
	public <T> long count(Class<T> entityClass) {
		return manager.count(entityClass);
	}

	@Override
	public <T> long count(Class<T> entityClass, ExpressionFunction<T> function) {
		return manager.count(entityClass, function);
	}

	@Override
	public <T> T insert(T entity) {
		return executeFunctionInTransaction(transaction -> manager.insert(entity));
	}

	@Override
	public <T> Collection<T> insert(Collection<T> entities) {
		return executeFunctionInTransaction(transaction -> manager.insert(entities));
	}

	@Override
	public <T> T update(T entity) {
		return executeFunctionInTransaction(transaction -> manager.update(entity));
	}

	@Override
	public <T> Collection<T> update(Collection<T> entities) {
		return executeFunctionInTransaction(transaction -> manager.update(entities));
	}

	@Override
	public <T> void delete(T entity) {
		executeConsumerInTransaction(transaction -> manager.delete(entity));
	}

	@Override
	public <T> void delete(Collection<T> entities) {
		executeConsumerInTransaction(transaction -> manager.delete(entities));
	}

	@Override
	public <T> void refresh(T entity) {
		manager.refresh(entity);
	}

	@Override
	public <T> void refresh(Collection<T> entities) {
		manager.refresh(entities);
	}

	@Override
	public <T> void detach(T entity) {
		manager.detach(entity);
	}

	@Override
	public <T> void detach(Collection<T> entities) {
		manager.detach(entities);
	}

	@Override
	public EntityManager em() {
		return manager.em();
	}

	private <R> R executeFunctionInTransaction(Function<EntityTransaction, R> function) {
		EntityTransaction transaction = manager.em().getTransaction();
		transaction.begin();
		R result = function.apply(transaction);
		transaction.commit();
		return result;
	}

	private void executeConsumerInTransaction(Consumer<EntityTransaction> consumer) {
		EntityTransaction transaction = manager.em().getTransaction();
		transaction.begin();
		consumer.accept(transaction);
		transaction.commit();
	}

}
