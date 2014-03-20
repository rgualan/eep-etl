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

	// public synchronized static boolean isBusy() {
	// // if(getInstance().em == null || (getInstance().em != null &&
	// // !getInstance().em.isOpen())){
	// if (getInstance().em == null
	// || (getInstance().em != null && !getInstance().em.get()
	// .isOpen())) {
	// return false;
	// }
	// return true;
	// }

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

	/**
	 * @see javax.persistence.EntityManager.persist
	 * 
	 * @param entity
	 *            entity to persist
	 * @return
	 * @throws Exception
	 */
	public static Object persist(Object entity) throws Exception {
		// Persist
		getEntityManager().persist(entity);
		// getEntityManager().detach(entity);
		return entity;
	}

	/**
	 * Removes a detached entity
	 * 
	 * @param entity
	 *            entity for removing
	 * @throws Exception
	 */
	// public static void delete(Entity entity) throws Exception {
	public static void delete(Object entity) {
		// Merge
		entity = getEntityManager().merge(entity);
		// Delete
		getEntityManager().remove(entity);
	}

	/**
	 * Obtiene una referencia a una entidad dada la clase de la entidad y su Pk.
	 * Se utiliza optimistic locking si la entidad tiene el campo version
	 * 
	 * @param type
	 *            Clase de la Entidad
	 * @param pk
	 *            Clave primaria
	 * @return Una referencia a la Entidad encontrada o null si no existe
	 * @throws Exception
	 */
	public static <T> T find(Class<T> type, Object pk) throws Exception {
		return find(type, pk, true);
	}

	public static <T> T find(Class<T> type, Object pk, boolean detachEntity)
			throws Exception {
		T entity;

		// // Apply locking, if the entity implements OptimistickLocking
		// interface
		// if (implementsInterface(type, OptimisticLocking.class)) {
		// entity = getEntityManager().find(type, pk, LockModeType.OPTIMISTIC);
		// } else {
		// entity = getEntityManager().find(type, pk);
		// }

		entity = getEntityManager().find(type, pk);

		// Detach from context
		if (detachEntity && entity != null) {
			getEntityManager().detach(entity);
		}

		return entity;
	}

	/**
	 * Actualiza (merge) una entidad que fue aislada (detach) previamente del
	 * contexto actual.
	 * 
	 * @param entity
	 *            Entidad
	 */
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