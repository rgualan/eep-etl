package com.etapa.etl.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.etapa.etl.util.Log;

public class GeneralDao {

	protected EntityManager em;

	public GeneralDao(EntityManager em) {
		this.em = em;
	}

	public void insert(Object obj) throws Exception {
		Log.getInstance().info("Insert entity: " + obj);
		em.persist(obj);

	}

	public void update(Object obj) throws Exception {
		Log.getInstance().info("Update entity: " + obj);
		obj = em.merge(obj);
	}

	public void delete(Object obj) throws Exception {
		Log.getInstance().info("Delete entity: " + obj);
		em.remove(em.merge(obj));
	}

	public <T> List<T> queryAll(Class<T> entityClass) {
		Log.getInstance().info("Query all: " + entityClass.getSimpleName());
		// CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaBuilder cb = em.getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(entityClass);
		Root<T> entityRoot = query.from(entityClass);
		query.select(entityRoot);

		List<T> list = em.createQuery(query).getResultList();
		// Log.getInstance().debug("Results:");
		// for (T t : list) {
		// Log.getInstance().debug(t);
		// }

		return list;

		// return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryAll(String className) throws Exception {
		Log.getInstance().info("Query all: " + className);
		CriteriaBuilder qb = em.getCriteriaBuilder();
		Class<T> cls = (Class<T>) Class.forName(className);
		CriteriaQuery<T> query = qb.createQuery(cls);
		return em.createQuery(query).getResultList();
	}

	public void insertBatch(List<? extends Object> objects) throws Exception {
		if (objects == null || objects.size() <= 0) {
			return;
		}

		Log.getInstance().warn(
				"Insert batch registers. Total " + objects.size() + ". Type: "
						+ objects.get(0).getClass().getSimpleName());

		for (Object object : objects) {
			em.persist(object);
		}

	}
}
