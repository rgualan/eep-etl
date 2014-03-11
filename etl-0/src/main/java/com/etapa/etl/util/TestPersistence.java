package com.etapa.etl.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestPersistence {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("etl");

		EntityManager em = emf.createEntityManager();

		// List<Center> list = em.createNamedQuery("Dataset.findAll",
		// Dataset.class).getResultList();
		//
		// for (Dataset dataset : list) {
		// Log.getInstance().debug(dataset);
		// }

		em.close();
		emf.close();

		Log.getInstance().debug("Test completed!");
	}
}
