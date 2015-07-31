/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.aerospike.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.aerospike.repository.query.Query;
import org.springframework.data.domain.Sort;
import org.springframework.data.keyvalue.core.KeyValueCallback;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.mapping.context.MappingContext;

import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;

/**
 * Aerospike specific data access operations.
 * 
 * @author Oliver Gierke
 * @author Peter Milne
 */
public interface AerospikeOperations {//extends KeyValueOperations {

	/**
	 * The Set name used for the specified class by this template.
	 * 
	 * @param entityClass must not be {@literal null}.
	 * @return
	 */
	String getSetName(Class<?> entityClass);
	
	/**
	 * Insert operation using the WritePolicy.recordExisits policy of CREATE_ONLY 
	 * @param objectToInsert
	 * @return
	 */
	public <T> T insert(T objectToInsert);
	/**
	 * Insert operation using the WritePolicy.recordExisits policy of CREATE_ONLY 
	 * @param id
	 * @param objectToInsert
	 */
	void insert(Serializable id, Object objectToInsert);
	
	/**
	 * @return mapping context in use.
	 */
	MappingContext<?, ?> getMappingContext();
	
	void save(Serializable id, Object objectToInsert);
	<T> void save(Serializable id, Object objectToInsert, Class<T> domainType);
	void update(Object objectToUpdate);
	void update(Serializable id, Object objectToUpdate);
	
	void delete(Class<?> type);
	
	<T> T delete(Serializable id, Class<T> type);
	<T> T delete(T objectToDelete);
	
	<T> Iterable<T> find(Query<?> query, Class<T> type);
	<T> List<T> findAll(Class<T> type);

	<T> T findById(Serializable id, Class<T> type);
	<T> T findById(Serializable id, Class<T> type, Class<T> domainType);

	<T> T add(T objectToAddTo, Map<String, Long> values);
	<T> T add(T objectToAddTo, String binName, int value);

	<T> T append(T objectToAppenTo, Map<String, String> values);
	<T> T append(T objectToAppenTo, String binName, String value);
	
	<T> Iterable<T> aggregate(Filter filter, Class<?> type, Class<T> outputType, String module, String function, List<?> arguments);
	

	/**
	 * @param query
	 * @param javaType
	 * @return
	 */
	int count(Query<?> query, Class<?> javaType);

	/**
	 * Execute operation against underlying store.
	 * 
	 * @param action must not be {@literal null}.
	 * @return
	 */
	<T> T execute(KeyValueCallback<T> action);

	/**
	 * @param sort
	 * @param type
	 * @return
	 */
	<T> Iterable<T> findAll(Sort sort, Class<T> type);

	/**
	 * @param offset
	 * @param rows
	 * @param sort
	 * @param type
	 * @return
	 */
	<T> Iterable<T> findInRange(int offset, int rows, Sort sort, Class<T> type);

	/**
	 * @param type
	 * @return
	 */
	long count(Class<?> type,String setName);

	/**
	 * @param <T>
	 * @param domainType
	 * @param indexName
	 * @param binName
	 * @param indexType
	 */
	<T> void createIndex(Class<T> domainType, String indexName, String binName,
			IndexType indexType);

	/**
	 * @param <T>
	 * @param id
	 * @param type
	 * @return
	 */
	<T> T findOne(Serializable id, Class<T> type);

	/**
	 * @param type
	 * @return
	 */
	long count(Class<?> type);



	
}
