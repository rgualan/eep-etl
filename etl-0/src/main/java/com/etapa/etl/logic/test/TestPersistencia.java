package com.etapa.etl.logic.test;

import com.etapa.etl.persistence.manager.JpaManagerFactory;
import com.etapa.etl.util.Log;

public class TestPersistencia {

	public static void main(String[] args) throws Exception {

		Log.getInstance().info("Prueba de las operaciones básicas de la persistencia...");

//		Persistence.createEntityManager();
//		Persistence.close();
		
		JpaManagerFactory.createEntityManagerFactory();
		JpaManagerFactory.close();
		
		Log.getInstance().info("Fin");
	}

}
