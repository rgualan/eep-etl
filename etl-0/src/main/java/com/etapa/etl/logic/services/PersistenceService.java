package com.etapa.etl.logic.services;

import java.util.List;

public interface PersistenceService {

	public void insert(Object entity) throws Exception;

	public void update(Object entity) throws Exception;

	public void delete(Object entity) throws Exception;

	public <T> List<T> queryAll(Class<T> entityClass);

	public void insertBatch(List<? extends Object> objects) throws Exception;

}
