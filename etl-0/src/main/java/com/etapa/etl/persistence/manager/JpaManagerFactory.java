package com.etapa.etl.persistence.manager;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.etapa.etl.util.FormatDates;
import com.etapa.etl.util.Log;

public final class JpaManagerFactory {

	private static JpaManagerFactory INSTANCE = null;

	private EntityManagerFactory emf = null;

	private final static String PERSISTENCE_UNIT = "etl";

	private final static Logger log = Log.getInstance();

	private JpaManagerFactory() {
		// Avoid creating the entity manager here, because the close method
		// problem
	}

	public static void createEntityManagerFactory() {
		createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	public static void createEntityManagerFactory(final String persistenceUnit) {
		Log.getInstance().info("Initialize persistence");
		Long start = System.currentTimeMillis(); // Start
		getInstance().emf = Persistence
				.createEntityManagerFactory(persistenceUnit);
		Log.getInstance().info("Load time for persistence: "
				+ FormatDates.getMinuteFormat().format(
						new Date(System.currentTimeMillis() - start)));

	}

	// Creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciación múltiple
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