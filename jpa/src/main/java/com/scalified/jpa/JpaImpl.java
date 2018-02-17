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
import com.scalified.jpa.dsl.entities.JpaEntitiesDslImpl;
import com.scalified.jpa.dsl.entity.JpaEntityDsl;
import com.scalified.jpa.dsl.entity.JpaEntityDslImpl;
import com.scalified.jpa.dsl.find.*;
import com.scalified.jpa.dsl.from.JpaFromDsl;
import com.scalified.jpa.dsl.from.JpaFromDslImpl;
import com.scalified.jpa.function.CriteriaFunction;
import com.scalified.jpa.manager.JpaManager;
import com.scalified.jpa.manager.JpaStandardManager;
import com.scalified.jpa.specification.Specification;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * A {@link Jpa} implementation
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaImpl implements Jpa {

	/**
	 * An underlying {@link JpaManager}
	 */
	protected final JpaManager manager;

	/**
	 * Creates {@link JpaImpl} instance
	 *
	 * @param em an {@link EntityManager} to construct the underlying {@link JpaManager} from
	 */
	public JpaImpl(EntityManager em) {
		this.manager = new JpaStandardManager(em);
	}

	/**
	 * Creates {@link JpaImpl} instance
	 *
	 * @param manager an underlying {@link JpaManager}
	 */
	public JpaImpl(JpaManager manager) {
		this.manager = manager;
	}

	/**
	 * Returns {@link JpaFindByEntityClassDsl} object, which provides the next
	 * <b>DSL</b> methods within <b>DSL</b> call chain to find entities by their class
	 *
	 * @param entityClass class of an entity
	 * @param <T>         type of an entity
	 * @return {@link JpaFindByEntityClassDsl} object
	 */
	@Override
	public <T> JpaFindByEntityClassDsl<T> find(Class<T> entityClass) {
		return new JpaFindByEntityClassDslImpl<>(manager, entityClass);
	}

	/**
	 * Returns {@link JpaFindByCriteriaFunctionDsl} object, which provides the next
	 * <b>DSL</b> methods within <b>DSL</b> call chain to find entities using
	 * {@link CriteriaFunction}
	 *
	 * @param function {@link CriteriaFunction} object
	 * @param <T>      type of an entity
	 * @return {@link JpaFindByCriteriaFunctionDsl} object
	 */
	@Override
	public <T> JpaFindByCriteriaFunctionDsl<T> find(CriteriaFunction<T> function) {
		return new JpaFindByCriteriaFunctionDslImpl<>(manager, function);
	}

	/**
	 * Returns {@link JpaFindBySpecificationDsl} object, which provides the next
	 * <b>DSL</b> methods within <b>DSL</b> call chain to find entities using
	 * {@link Specification}
	 *
	 * @param specification {@link Specification} object
	 * @param <T>           type of an entity
	 * @return {@link JpaFindBySpecificationDsl} object
	 */
	@Override
	public <T> JpaFindBySpecificationDsl<T> find(Specification<T> specification) {
		return new JpaFindBySpecificationDslImpl<>(manager, specification);
	}

	/**
	 * Returns {@link JpaFromDsl} object, which provides the next <b>DSL</b> methods
	 * within <b>DSL</b> call chain derived from entity class
	 *
	 * @param entityClass class of an entity
	 * @param <T>         type of an entity
	 * @return {@link JpaFromDsl} object
	 */
	@Override
	public <T> JpaFromDsl<T> from(Class<T> entityClass) {
		return new JpaFromDslImpl<>(manager, entityClass);
	}

	/**
	 * Returns {@link JpaEntityDsl} object, which provides the next <b>DSL</b> methods
	 * within <b>DSL</b> call chain derived from entity object
	 *
	 * @param entity an entity instance
	 * @param <T>    type of an entity
	 * @return {@link JpaEntityDsl} object
	 */
	@Override
	public <T> JpaEntityDsl<T> entity(T entity) {
		return new JpaEntityDslImpl<>(manager, entity);
	}

	/**
	 * Returns {@link JpaEntitiesDsl} object, which provides the next <b>DSL</b> methods
	 * within <b>DSL</b> call chain derived from collection of entity objects
	 *
	 * @param entities a collection of entity instances
	 * @param <T>      type of an entity
	 * @return {@link JpaEntitiesDsl} object
	 */
	@Override
	public <T extends Collection<T>> JpaEntitiesDsl<T> entities(Collection<T> entities) {
		return new JpaEntitiesDslImpl<>(manager, entities);
	}

	/**
	 * Returns the underlying {@link EntityManager}
	 *
	 * @return underlying {@link EntityManager}
	 */
	@Override
	public EntityManager em() {
		return manager.em();
	}

}
