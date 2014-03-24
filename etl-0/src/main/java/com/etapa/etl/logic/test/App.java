package com.etapa.etl.logic.test;

import java.util.Scanner;

import com.etapa.etl.logic.core.LoggerNetScanner;
import com.etapa.etl.persistence.dao.GeneralDao;
import com.etapa.etl.persistence.entity.Unidade;
import com.etapa.etl.persistence.manager.JpaManager;
import com.etapa.etl.persistence.manager.JpaManagerFactory;
import com.etapa.etl.util.Log;

public class App {

	public static void main(String[] args) throws Exception {

		try {
			JpaManagerFactory.createEntityManagerFactory();

			// Vaciar datos
			//cleanTables();

			// Insertar parametros generales
			parameterize();

			// Procesar/Escanear archivos
			LoggerNetScanner.scan();

		} catch (RuntimeException re) {
			Log.getInstance().error(re);
			
		} catch (Exception e) {
			Log.getInstance().error(e);
			JpaManager.rollbackTransaction();
		} finally {
			JpaManagerFactory.close();
		}
	}

	private static void parameterize() throws Exception {
		Unidade uni = new Unidade();
		uni.setUniId("NA");
		uni.setUniNombre("NA");
		uni.setUniDescripcion("NA");
		uni.setUniTipo("NA");
if (GeneralDao.find(Unidade.class, uni.getUniId())==null)
		GeneralDao.insert(uni);
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
