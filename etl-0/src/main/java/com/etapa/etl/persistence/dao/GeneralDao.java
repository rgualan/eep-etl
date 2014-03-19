package com.etapa.etl.persistence.dao;

import java.util.List;

import com.etapa.etl.persistence.manager.Persistence;
import com.etapa.etl.util.Log;

public class GeneralDao {

	public GeneralDao() {
		Persistence.createEntityManager();
	}

	public static void insert(Object obj) throws Exception {
		Log.getInstance().info("Insert entity: " + obj);

		Persistence.beginTransaction();
		Persistence.persist(obj);
		Persistence.commitTransaction();

	}

	public static void update(Object obj) throws Exception {
		Log.getInstance().info("Update entity: " + obj);

		Persistence.beginTransaction();
		Persistence.update(obj);
		Persistence.commitTransaction();
	}

	public static void delete(Object obj) throws Exception {
		Log.getInstance().info("Delete entity: " + obj);

		Persistence.beginTransaction();
		Persistence.remove(obj);
		Persistence.commitTransaction();
	}

	public static <T> T find(Class<T> type, Object pk) throws Exception {
		return Persistence.find(type, pk);
	}

	public static <T> List<T> queryAll(Class<T> entityClass) {
		return Persistence.queryAll(entityClass);
	}
}
