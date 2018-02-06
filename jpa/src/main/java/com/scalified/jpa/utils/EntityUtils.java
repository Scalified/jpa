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

package com.scalified.jpa.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static java.util.Objects.nonNull;

/**
 * An utility class for working with entities
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
public class EntityUtils {

	/**
	 * Updates the {@code non-static} field values of the first specified entity
	 * with the correspondent {@code not null} field values of the second specified entity
	 *
	 * @param what an entity to update
	 * @param with an entity used to take field values from
	 * @param <T>  type of entities
	 */
	public static <T> void update(T what, T with) {
		Field[] whatFields = what.getClass().getDeclaredFields();
		Field[] withFields = with.getClass().getDeclaredFields();
		for (int i = 0; i < withFields.length; i++) {
			if (nonNull(withFields[i])
					&& !Modifier.isStatic(whatFields[i].getModifiers())) {
				Field withField = withFields[i];
				withField.setAccessible(true);
				Object value;
				try {
					value = withField.get(with);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				withField.setAccessible(false);
				Field whatField = whatFields[i];
				whatField.setAccessible(true);
				try {
					whatField.set(what, value);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				whatField.setAccessible(false);
			}
		}
	}

}
