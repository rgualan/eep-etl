package com.etapa.etl.logic.test;

import com.etapa.etl.persistence.manager.JpaManager;
import com.etapa.etl.persistence.manager.JpaManagerFactory;
import com.etapa.etl.util.Log;

public class Cleanner {
	public static void main(String[] args) {
		try {
			JpaManagerFactory.createEntityManagerFactory();

			cleanTables();

		} catch (RuntimeException re) {
			Log.getInstance().error(re);
		} catch (Exception e) {
			Log.getInstance().error(e);
			JpaManager.rollbackTransaction();
		} finally {
			JpaManagerFactory.close();
		}
	}

	private static void cleanTables() {
		Log.getInstance().info("Eliminando tablas para la prueba...");
		JpaManager.beginTransaction();
		JpaManager.getEntityManager()
				.createNativeQuery("DELETE FROM OBSERVACION").executeUpdate();
		JpaManager.getEntityManager()
				.createNativeQuery("DELETE FROM FENOMENO_UNIDADES")
				.executeUpdate();
		JpaManager.getEntityManager().createNativeQuery("DELETE FROM FENOMENO")
				.executeUpdate();
		JpaManager.getEntityManager().createNativeQuery("DELETE FROM UNIDADES")
				.executeUpdate();
		JpaManager.getEntityManager().createNativeQuery("DELETE FROM ARCHIVO")
				.executeUpdate();
		JpaManager.getEntityManager().createNativeQuery("DELETE FROM ESTACION")
				.executeUpdate();
		JpaManager.getEntityManager()
				.createNativeQuery("DELETE FROM TIPO_ESTACION").executeUpdate();
		JpaManager.commitTransaction();
	}

}
