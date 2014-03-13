package com.etapa.etl.logic.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.etapa.etl.persistence.dao.ArchivoDao;
import com.etapa.etl.persistence.entity.Archivo;

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
			// Cargar persistencia
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
			em = emf.createEntityManager();

			// Consultar
			System.out.println("Consultando...");

			ArchivoDao dao = new ArchivoDao(em);

			List<Archivo> resultList = dao.queryAll(Archivo.class);

			if (resultList != null) {
				System.out.println("Resultado: ");
				for (Archivo archivo : resultList) {
					System.out.println(archivo);
				}
			}
			
			System.out.println("Consulta especializada...");
			Archivo tmpArc = dao.queryById("1");
			System.out.println(tmpArc);
			

			// Insertar
			System.out.println("Insertando...");

			int next = 0;
			if (resultList != null && resultList.size() > 0) {
				for (Archivo archivo : resultList) {
					int id = Integer.parseInt(archivo.getArcPath());
					if (id > next) {
						next = id ;
					}
				}
				next++;

			}
			System.out.println("Next: " + next);

			List<Archivo> newEntitiesList = new ArrayList<Archivo>();
			Archivo newEntity = null;
			for (int i = 0; i < 10; i++) {
				newEntity = new Archivo();
				newEntity.setArcPath("" + next++);
				newEntity.setArcNbytes(Long.MIN_VALUE);
				newEntitiesList.add(newEntity);
			}

			// Iniciar transaccion
			em.getTransaction().begin();

			for (Archivo archivo : newEntitiesList) {
				dao.insert(archivo);
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Cerrando la pesistencia...");
			em.close();
			emf.close();
		}
	}
}
