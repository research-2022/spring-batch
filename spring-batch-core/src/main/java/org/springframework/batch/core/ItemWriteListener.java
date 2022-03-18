/*
 * Copyright 2006-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.core;

import java.util.List;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemWriter;

/**
 * <p>
 * Listener interface for the writing of items.  Implementations
 * of this interface will be notified before, after, and in case
 * of any exception thrown while writing a list of items.
 * </p>
 *
 * <p>
 * <em>Note: </em> This listener is designed to work around the
 * lifecycle of an item.  This means that each method should be
 * called once within the lifecycle of an item and in fault
 * tolerant scenarios, any transactional work that is done in
 * one of these methods would be rolled back and not re-applied.
 * Because of this, it is recommended to not perform any logic
 * using this listener that participates in a transaction.
 *</p>
 *
 * @author Lucas Ward
 * @author Mahmoud Ben Hassine
 *
 */
public interface ItemWriteListener<S> extends StepListener {

	/**
	 * Called before {@link ItemWriter#write(java.util.List)}
	 *
	 * @param items to be written
	 */
	default void beforeWrite(List<? extends S> items) {
	}

	/**
	 * Called after {@link ItemWriter#write(java.util.List)} This will be
	 * called before any transaction is committed, and before
	 * {@link ChunkListener#afterChunk(ChunkContext)}
	 *
	 * @param items written items
	 */
	default void afterWrite(List<? extends S> items) {
	}

	/**
	 * Called if an error occurs while trying to write. Will be called inside a
	 * transaction, but the transaction will normally be rolled back. There is
	 * no way to identify from this callback which of the items (if any) caused
	 * the error.
	 *
	 * @param exception thrown from {@link ItemWriter}
	 * @param items attempted to be written.
	 */
	default void onWriteError(Exception exception, List<? extends S> items) {
	}
}
