package com.etapa.etl.persistence.dao;

import java.util.List;

import com.etapa.etl.persistence.manager.JpaManager;
import com.etapa.etl.util.Log;

public class GeneralDao {

	public GeneralDao() {
		JpaManager.createEntityManager();
	}

	public static void insert(Object obj) throws Exception {
	//	Log.getInstance().info("Insert entity: " + obj);
try
{
		JpaManager.beginTransaction();
		JpaManager.persist(obj);
		JpaManager.commitTransaction();
}
catch(Exception e)
{}
	}

	public static void update(Object obj) throws Exception {
		Log.getInstance().info("Update entity: " + obj);

		JpaManager.beginTransaction();
		JpaManager.update(obj);
		JpaManager.commitTransaction();
	}

	public static void delete(Object obj) throws Exception {
		Log.getInstance().info("Delete entity: " + obj);

		JpaManager.beginTransaction();
		JpaManager.delete(obj);
		JpaManager.commitTransaction();
	}

	public static <T> T find(Class<T> type, Object pk) throws Exception {
		return JpaManager.find(type, pk);
	}

	public static <T> List<T> queryAll(Class<T> entityClass) {
		return JpaManager.queryAll(entityClass);
	}
}
