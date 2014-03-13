package com.etapa.etl.logic.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
			
			TypedQuery<Archivo> query = em.createQuery("Select a from Archivo a", Archivo.class);
			List<Archivo> resultList = query.getResultList();
			
			if (resultList != null){
				System.out.println("Resultado: ");
				for (Archivo archivo : resultList) {
					System.out.println(archivo);
				}
			}
			
			// Insertar
			System.out.println("Insertando...");
			
			int next = 0;
			if (resultList != null && resultList.size() > 0){
				next = Integer.parseInt(resultList.get(resultList.size()-1).getArcPath()) + 1;
			}
			
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
				em.persist(archivo);
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
