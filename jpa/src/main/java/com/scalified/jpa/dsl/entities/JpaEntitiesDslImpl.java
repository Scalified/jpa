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

package com.scalified.jpa.dsl.entities;

import com.scalified.jpa.manager.JpaManager;

import java.util.Collection;

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class JpaEntitiesDslImpl<T extends Collection<T>> implements JpaEntitiesDsl<T> {

	private final JpaManager manager;

	private Collection<T> entities;

	public JpaEntitiesDslImpl(JpaManager manager, Collection<T> entities) {
		this.manager = manager;
		this.entities = entities;
	}

	@Override
	public Collection<T> insert() {
		return manager.insert(entities);
	}

	@Override
	public Collection<T> update() {
		return manager.update(entities);
	}

	@Override
	public void delete() {
		manager.delete(entities);
	}

	@Override
	public void refresh() {
		manager.refresh(entities);
	}

	@Override
	public void detach() {
		manager.detach(entities);
	}

}
