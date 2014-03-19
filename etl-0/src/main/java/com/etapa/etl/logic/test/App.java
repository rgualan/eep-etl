package com.etapa.etl.logic.test;

import com.etapa.etl.logic.core.RecopilarDatos;
import com.etapa.etl.persistence.manager.JpaManager;
import com.etapa.etl.persistence.manager.JpaManagerFactory;
import com.etapa.etl.util.Log;

public class App {

	public static void main(String[] args) throws Exception {

		Log.getInstance().info("Hola mundo!!!");
		
		try {
			// Persistence.createEntityManager();
			JpaManagerFactory.createEntityManagerFactory();

			// Vaciar datos
			cleanTables();

			// Procesar
			RecopilarDatos rd = new RecopilarDatos();
			rd.leerdatos();

		} catch (Exception e) {
			Log.getInstance().error(e);
			JpaManager.rollbackTransaction();
		} finally {
			// Persistence.close();
			JpaManagerFactory.close();
		}

	}

	private static void cleanTables() {
		Log.getInstance().info("Eliminando tablas para la prueba...");
		JpaManager.beginTransaction();
		JpaManager.getEntityManager().createNativeQuery("DELETE FROM ARCHIVO")
				.executeUpdate();
		JpaManager.commitTransaction();
	}

}
