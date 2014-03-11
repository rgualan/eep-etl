package com.etapa.etl.logic.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.etapa.etl.logic.services.PersistenceService;
import com.etapa.etl.persistence.dao.GeneralDao;

@Service("persistenceService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersistenceServiceImpl implements PersistenceService {

	@Autowired
	GeneralDao generalDao;

	/* Services */
	public void insert(Object entity) throws Exception {
		generalDao.insert(entity);
	}

	public void update(Object entity) throws Exception {
		generalDao.update(entity);
	}

	public void delete(Object entity) throws Exception {
		generalDao.delete(entity);
	}

	public <T> List<T> queryAll(Class<T> entityClass) {
		return generalDao.queryAll(entityClass);
	}

	@Override
	public void insertBatch(List<? extends Object> objects) throws Exception {
		generalDao.insertBatch(objects);
	}
}
