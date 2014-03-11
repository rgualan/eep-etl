package com.etapa.etl.logic.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
	public static final String PERSISTENCE_UNIT = "etl";
	private static EntityManagerFactory emf = null;
	private static EntityManager em = null;

	public static void main(String[] args) {
		System.out.println("Hola mundo!!!");

		cargarPersistencia();

		// Probar la persistencia
	}

	private static void cargarPersistencia() {
		System.out.println("Cargando la persistencia...");
		
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
			em = emf.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Cerrando la pesistencia...");
			em.close();
			emf.close();
		}
	}
}
