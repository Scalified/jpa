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
class EntitySpliterator<T> implements Spliterator<T> {

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
	EntitySpliterator(TypedQuery<T> query) {
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
	EntitySpliterator(TypedQuery<T> query, int chunkSize) {
		this.query = query;
		this.chunkSize = chunkSize;
		this.chunk = new LinkedList<>();
	}

	/**
	 * If a remaining element exists, performs the given action on it,
	 * returning {@code true}; else returns {@code false}.  If this
	 * Spliterator is {@link #ORDERED} the action is performed on the
	 * next element in encounter order.  Exceptions thrown by the
	 * action are relayed to the caller.
	 *
	 * @param action The action
	 * @return {@code false} if no remaining elements existed
	 * upon entry to this method, else {@code true}.
	 * @throws NullPointerException if the specified action is null
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
	 * If this spliterator can be partitioned, returns a Spliterator
	 * covering elements, that will, upon return from this method, not
	 * be covered by this Spliterator.
	 *
	 * <p>If this Spliterator is {@link #ORDERED}, the returned Spliterator
	 * must cover a strict prefix of the elements.
	 *
	 * <p>Unless this Spliterator covers an infinite number of elements,
	 * repeated calls to {@code trySplit()} must eventually return {@code null}.
	 * Upon non-null return:
	 * <ul>
	 * <li>the value reported for {@code estimateSize()} before splitting,
	 * must, after splitting, be greater than or equal to {@code estimateSize()}
	 * for this and the returned Spliterator; and</li>
	 * <li>if this Spliterator is {@code SUBSIZED}, then {@code estimateSize()}
	 * for this spliterator before splitting must be equal to the sum of
	 * {@code estimateSize()} for this and the returned Spliterator after
	 * splitting.</li>
	 * </ul>
	 *
	 * <p>This method may return {@code null} for any reason,
	 * including emptiness, inability to split after traversal has
	 * commenced, data structure constraints, and efficiency
	 * considerations.
	 *
	 * @apiNote
	 * An ideal {@code trySplit} method efficiently (without
	 * traversal) divides its elements exactly in half, allowing
	 * balanced parallel computation.  Many departures from this ideal
	 * remain highly effective; for example, only approximately
	 * splitting an approximately balanced tree, or for a tree in
	 * which leaf nodes may contain either one or two elements,
	 * failing to further split these nodes.  However, large
	 * deviations in balance and/or overly inefficient {@code
	 * trySplit} mechanics typically result in poor parallel
	 * performance.
	 *
	 * @return a {@code Spliterator} covering some portion of the
	 * elements, or {@code null} if this spliterator cannot be split
	 */
	@Override
	public Spliterator<T> trySplit() {
		return null;
	}

	/**
	 * Returns an estimate of the number of elements that would be
	 * encountered by a {@link #forEachRemaining} traversal, or returns {@link
	 * Long#MAX_VALUE} if infinite, unknown, or too expensive to compute.
	 *
	 * <p>If this Spliterator is {@link #SIZED} and has not yet been partially
	 * traversed or split, or this Spliterator is {@link #SUBSIZED} and has
	 * not yet been partially traversed, this estimate must be an accurate
	 * count of elements that would be encountered by a complete traversal.
	 * Otherwise, this estimate may be arbitrarily inaccurate, but must decrease
	 * as specified across invocations of {@link #trySplit}.
	 *
	 * @apiNote
	 * Even an inexact estimate is often useful and inexpensive to compute.
	 * For example, a sub-spliterator of an approximately balanced binary tree
	 * may return a value that estimates the number of elements to be half of
	 * that of its parent; if the root Spliterator does not maintain an
	 * accurate count, it could estimate size to be the power of two
	 * corresponding to its maximum depth.
	 *
	 * @return the estimated size, or {@code Long.MAX_VALUE} if infinite,
	 *         unknown, or too expensive to compute.
	 */
	@Override
	public long estimateSize() {
		return Long.MAX_VALUE;
	}

	/**
	 * Returns a set of characteristics of this Spliterator and its
	 * elements. The result is represented as ORed values from {@link
	 * #ORDERED}, {@link #DISTINCT}, {@link #SORTED}, {@link #SIZED},
	 * {@link #NONNULL}, {@link #IMMUTABLE}, {@link #CONCURRENT},
	 * {@link #SUBSIZED}.  Repeated calls to {@code characteristics()} on
	 * a given spliterator, prior to or in-between calls to {@code trySplit},
	 * should always return the same result.
	 *
	 * <p>If a Spliterator reports an inconsistent set of
	 * characteristics (either those returned from a single invocation
	 * or across multiple invocations), no guarantees can be made
	 * about any computation using this Spliterator.
	 *
	 * @apiNote The characteristics of a given spliterator before splitting
	 * may differ from the characteristics after splitting.  For specific
	 * examples see the characteristic values {@link #SIZED}, {@link #SUBSIZED}
	 * and {@link #CONCURRENT}.
	 *
	 * @return a representation of characteristics
	 */
	@Override
	public int characteristics() {
		return Spliterator.ORDERED;
	}

}
