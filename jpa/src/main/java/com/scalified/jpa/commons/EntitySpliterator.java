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

package com.scalified.jpa.commons;

import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A {@link Spliterator} used to create {@link Stream} for entity chunk loading
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class EntitySpliterator<T> implements Spliterator<T> {

	/**
	 * Default size of a chunk
	 */
	private static final int DEFAULT_CHUNK_SIZE = 50;

	/**
	 * Entity typed query
	 */
	private final TypedQuery<T> query;

	/**
	 * Start index position of each query
	 */
	private final AtomicInteger startIdx = new AtomicInteger(0);

	/**
	 * Entity chunk queue, populated by queries
	 */
	private final Queue<T> chunk;

	/**
	 * Size of a chunk
	 */
	private final int chunkSize;

	/**
	 * Creates {@link EntitySpliterator} instance
	 *
	 * @param query a {@link TypedQuery} instance
	 */
	public EntitySpliterator(TypedQuery<T> query) {
		this.query = query;
		this.chunkSize = DEFAULT_CHUNK_SIZE;
		this.chunk = new LinkedList<>();
	}

	/**
	 * Creates {@link EntitySpliterator} instance
	 *
	 * @param query     a {@link TypedQuery} instance
	 * @param chunkSize size of a chunk
	 */
	public EntitySpliterator(TypedQuery<T> query, int chunkSize) {
		this.query = query;
		this.chunkSize = chunkSize;
		this.chunk = new LinkedList<>();
	}

	/**
	 * If a remaining element exists, performs the given action on it,
	 * returning {@code true}; else returns {@code false}.
	 * The action is performed on the next element in encounter order.
	 * Exceptions thrown by the action are relayed to the caller.
	 *
	 * @param action The action
	 * @return {@code false} if no remaining elements existed
	 * upon entry to this method, else {@code true}.
	 * @throws NullPointerException if the specified action is {@code null}
	 */
	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		if (chunk.isEmpty()) {
			List<T> chunkResult = query.setFirstResult(startIdx.getAndAdd(chunkSize))
					.setMaxResults(chunkSize)
					.getResultList();

			if (chunkResult.isEmpty()) return false;

			chunk.addAll(chunkResult);
		}
		action.accept(chunk.poll());
		return true;
	}

	/**
	 * Returns {@code null} since cannot be split
	 *
	 * @return {@code null}
	 */
	@Override
	public Spliterator<T> trySplit() {
		return null;
	}

	/**
	 * Returns {@link Long#MAX_VALUE} since infinite
	 *
	 * @return {@link Long#MAX_VALUE}
	 */
	@Override
	public long estimateSize() {
		return Long.MAX_VALUE;
	}

	/**
	 * Returns {@link Spliterator#ORDERED} since preserves order
	 *
	 * @return {@link Spliterator#ORDERED}
	 */
	@Override
	public int characteristics() {
		return Spliterator.ORDERED;
	}

}
