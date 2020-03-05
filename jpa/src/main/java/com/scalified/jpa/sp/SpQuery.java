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

package com.scalified.jpa.sp;

import com.scalified.jpa.commons.StringUtils;

import javax.persistence.ParameterMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A stored procedure configuration object
 *
 * @author shell
 * @since 2018-08-18
 */
public class SpQuery<T> {

	/**
	 * Stored procedure name
	 */
	private final String name;

	/**
	 * Collection of stored procedure parameters
	 */
	private final List<SpParam> params;

	/**
	 * Stored procedure result classes
	 */
	private Class<T>[] resultClasses;

	/**
	 * Stored procedure result mappings
	 */
	private String[] resultMappings;

	/**
	 * Creates {@link SpQuery} instance
	 *
	 * @param name stored procedure name
	 */
	public SpQuery(String name) {
		this.name = name;
		this.params = new LinkedList<>();
	}

	/**
	 * Creates stored procedure configuration object builder
	 *
	 * @param name stored procedure name
	 * @param <T>  type of result
	 * @return stored procedure configuration object builder
	 */
	public static <T> Builder<T> builder(String name) {
		return new Builder<>(name);
	}

	/**
	 * Returns stored procedure name
	 *
	 * @return stored procedure name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns stored procedure parameters
	 *
	 * @return stored procedure parameters
	 */
	public List<SpParam> getParams() {
		return params;
	}

	/**
	 * Returns stored procedure result classes
	 *
	 * @return stored procedure result classes
	 */
	public Class<T>[] getResultClasses() {
		return resultClasses;
	}

	/**
	 * Returns stored procedure result mappings
	 *
	 * @return stored procedure result mappings
	 */
	public String[] getResultMappings() {
		return resultMappings;
	}

	/**
	 * Stored procedure configuration object builder
	 */
	public static class Builder<T> {

		/**
		 * An underlying stored procedure configuration object
		 */
		private final SpQuery<T> spQuery;

		/**
		 * Creates {@link Builder} instance
		 *
		 * @param name stored procedure name
		 */
		public Builder(String name) {
			this.spQuery = new SpQuery<>(name);
		}

		/**
		 * Adds stored procedure parameter with the specified {@code name},
		 * parameter {@code mode} and {@code value}
		 *
		 * @param name  stored procedure parameter name
		 * @param mode  stored procedure parameter mode
		 * @param value stored procedure parameter value
		 * @return this object
		 */
		public Builder<T> withParam(String name, ParameterMode mode, Object value) {
			if (Objects.nonNull(value)) {
				this.spQuery.params.add(new SpParam(name, mode, value));
			}
			return this;
		}

		/**
		 * Adds stored procedure parameter with the specified {@code name},
		 * {@code value} and {@link ParameterMode#IN} mode
		 *
		 * @param name  stored procedure parameter name
		 * @param value stored procedure parameter value
		 * @return this object
		 */
		public Builder<T> withInParam(String name, Object value) {
			if (Objects.nonNull(value)) {
				this.spQuery.params.add(new SpParam(name, ParameterMode.IN, value));
			}
			return this;
		}

		/**
		 * Adds stored procedure parameter with the specified {@code name},
		 * {@code values} and {@link ParameterMode#IN} mode
		 *
		 * <p>
		 * The specified collection of values is passed as a single string
		 * stored procedure parameter containing comma-separated values,
		 * thus stored procedure must implement the logic of extracting
		 * each parameter
		 *
		 * @param name   stored procedure parameter name
		 * @param values stored procedure parameter values
		 * @return this object
		 */
		public Builder<T> withInParam(String name, List<String> values) {
			if (Objects.nonNull(values) && !values.isEmpty()) {
				String value = StringUtils.join(values, ',');
				this.spQuery.params.add(new SpParam(name, ParameterMode.IN, value));
			}
			return this;
		}

		/**
		 * Adds stored procedure parameter with the specified {@code name}
		 * and {@link ParameterMode#REF_CURSOR} mode
		 *
		 * @param name stored procedure parameter name
		 * @return this object
		 */
		public Builder<T> withRefCursorParam(String name) {
			SpParam param = new SpParam(name, ParameterMode.REF_CURSOR);
			this.spQuery.params.add(param);
			return this;
		}

		/**
		 * Adds stored procedure result classes
		 *
		 * @param resultClasses stored procedure result classes
		 * @return this object
		 */
		@SafeVarargs
		public final Builder<T> withResultClasses(Class<T>... resultClasses) {
			this.spQuery.resultClasses = resultClasses;
			return this;
		}

		/**
		 * Add stored procedure result mappings
		 *
		 * @param resultMappings stored procedure result mappings
		 * @return this object
		 */
		public Builder<T> withResultMappings(String... resultMappings) {
			this.spQuery.resultMappings = resultMappings;
			return this;
		}

		/**
		 * Builds stored procedure configuration object
		 *
		 * @return stored procedure configuration object
		 */
		public SpQuery<T> build() {
			return this.spQuery;
		}

	}

	/**
	 * Stored procedure configuration object parameter
	 */
	public static class SpParam {

		/**
		 * Stored procedure parameter name
		 */
		private final String name;

		/**
		 * Stored procedure parameter mode
		 */
		private final ParameterMode mode;

		/**
		 * Stored procedure parameter value
		 */
		private Object value;

		/**
		 * Creates {@link SpParam} instance
		 *
		 * @param name stored procedure parameter name
		 * @param mode stored procedure parameter mode
		 */
		SpParam(String name, ParameterMode mode) {
			this.name = name;
			this.mode = mode;
		}

		/**
		 * Creates {@link SpParam} instance
		 *
		 * @param name  stored procedure parameter name
		 * @param mode  stored procedure parameter mode
		 * @param value stored procedure parameter value
		 */
		SpParam(String name, ParameterMode mode, Object value) {
			this.name = name;
			this.mode = mode;
			this.value = value;
		}

		/**
		 * Returns stored procedure parameter name
		 *
		 * @return stored procedure parameter name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns stored procedure parameter mode
		 *
		 * @return stored procedure parameter mode
		 */
		public ParameterMode getMode() {
			return mode;
		}

		/**
		 * Returns stored procedure parameter value
		 *
		 * @return stored procedure parameter value
		 */
		public Object getValue() {
			return value;
		}

	}

}
