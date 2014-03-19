package com.etapa.etl.persistence.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.etapa.etl.util.Log;

public class Persistence {

	private static Persistence INSTANCE = null;

	private static EntityManagerFactory emf;
	
	private final ThreadLocal<EntityManager> em = new ThreadLocal<EntityManager>();

	private final static Logger log = Log.getInstance();

	private Persistence() {
	}

	// Entity manager

	public static void createEntityManager() {
		emf = javax.persistence.Persistence.createEntityManagerFactory("etl");
		
		//getInstance().em.set(JpaManagerFactory.createEntityManager());
		getInstance().em.set(emf.createEntityManager());
	}

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Persistence();
		}
	}

	public static Persistence getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	public static EntityManager getEntityManager() {
		return getInstance().em.get();
	}

	public static void close() {
		if (getEntityManager() != null) {
			log.info("Close entity manager");
			getEntityManager().close();
		}
		
		if(emf != null){
			emf.close();
		}
	}

	// Transactions

	public static void beginTransaction() {
		log.info("Begin transaction");
		getEntityManager().getTransaction().begin();
	}

	public static void commitTransaction() {
		log.info("Commit transaction");
		getEntityManager().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		if ((getEntityManager().getTransaction() != null)
				&& (getEntityManager().getTransaction().isActive())) {
			log.info("Rollback transaction");
			getEntityManager().getTransaction().rollback();
		}
	}

	// Basic operations

	public static void persist(Object entity) throws Exception {
		log.info("Persist");
		getEntityManager().persist(entity);
	}

	public static void update(Object entity) throws Exception {
		getEntityManager().merge(entity);
	}

	public static void remove(Object entity) throws Exception {
		log.info("Delete");

		entity = getEntityManager().merge(entity);
		getEntityManager().remove(entity);
	}

	public static <T> T find(Class<T> type, Object pk) throws Exception {
		T entity = getEntityManager().find(type, pk);

		getEntityManager().detach(entity);

		return entity;
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