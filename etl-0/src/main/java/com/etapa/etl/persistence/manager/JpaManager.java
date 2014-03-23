package com.etapa.etl.persistence.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.etapa.etl.util.Log;

public class JpaManager {

	private static JpaManager INSTANCE = null;

	private final ThreadLocal<EntityManager> em = new ThreadLocal<EntityManager>();

 	private final static Logger log = Log.getInstance();

	private JpaManager() {
	}

	public static void createEntityManager() {
		Log.getInstance().info("Create entity manager");
		getInstance().em.set(JpaManagerFactory.createEntityManager());
	}

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JpaManager();
		}
	}

	public static JpaManager getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	public static void beginTransaction() {
	//	Log.getInstance().info("Begin transaction");
		getEntityManager().getTransaction().begin();
	}

	public static void commitTransaction() {
		//Log.getInstance().info("Commit transaction");
		getEntityManager().getTransaction().commit();
		getEntityManager().clear();
	}

	public static void rollbackTransaction() {
		if (getEntityManager().getTransaction() != null
				&& getEntityManager().getTransaction().isActive()) {
			Log.getInstance().info("Rollback transaction");
			getEntityManager().getTransaction().rollback();
		}
	}

	public static EntityManager getEntityManager() {
		if (getInstance().em.get() == null) {
			createEntityManager();
		}
		return getInstance().em.get();

	}

	public static void close() {
		if (getInstance().em.get() != null) {
			Log.getInstance().info("Close entity manager");
			getEntityManager().close();
		}
	}

	public static Object persist(Object entity) throws Exception {
		// Persist
		getEntityManager().persist(entity);
		// getEntityManager().detach(entity);
		return entity;
	}

	public static void delete(Object entity) {
		// Merge
		entity = getEntityManager().merge(entity);
		// Delete
		getEntityManager().remove(entity);
	}

	public static <T> T find(Class<T> type, Object pk) throws Exception {
		return find(type, pk, true);
	}

	public static <T> T find(Class<T> type, Object pk, boolean detachEntity)
			throws Exception {
		T entity = getEntityManager().find(type, pk);

		if (detachEntity && entity != null) {
			getEntityManager().detach(entity);
		}

		return entity;
	}

	public static Object update(Object entity) throws Exception {
		return getEntityManager().merge(entity);
	}

	public static void detachList(List<? extends Object> lEntities) {
		for (Object entity : lEntities) {
			getEntityManager().detach(entity);
		}
	}
	
	public static <T> List<T> queryAll(Class<T> entityClass) {
		Log.getInstance().info("Query all: " + entityClass.getSimpleName());
		
		CriteriaBuilder cb = getEntityManager().getEntityManagerFactory()
				.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(entityClass);
		Root<T> entityRoot = query.from(entityClass);
		query.select(entityRoot);

		List<T> list = getEntityManager().createQuery(query).getResultList();

		return list;
	}

}