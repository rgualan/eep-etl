package com.etapa.etl.persistence.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.etapa.etl.util.Chronometer;
import com.etapa.etl.util.Log;

public final class JpaManagerFactory {

	private static JpaManagerFactory INSTANCE = null;

	private EntityManagerFactory emf = null;

	private final static String PERSISTENCE_UNIT = "etl";

	// private final static Logger log = Log.getInstance();

	private JpaManagerFactory() {
	}

	public static void createEntityManagerFactory() {
		createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	public static void createEntityManagerFactory(final String persistenceUnit) {
		Log.getInstance().info("Initialize persistence");

		Chronometer.startTimer("persistence");

		getInstance().emf = Persistence
				.createEntityManagerFactory(persistenceUnit);

		Chronometer.stopTimer("persistence", "Load time for persistence: ");

	}

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JpaManagerFactory();
		}
	}

	private static JpaManagerFactory getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	public static EntityManager createEntityManager() {
		return getInstance().emf.createEntityManager();
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return getInstance().emf;
	}

	public static void close() {
		Log.getInstance().info("Close entity manager factory");
		if (getInstance().emf != null) {
			getInstance().emf.close();
		}
	}
}