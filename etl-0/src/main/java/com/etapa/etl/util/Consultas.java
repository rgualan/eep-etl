package com.etapa.etl.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.etapa.etl.persistence.dao.ArchivoDao;
import com.etapa.etl.persistence.entity.Archivo;

public class Consultas<T> {
	T obj;
	Persistencia p;
	Object dao;
	String item;

	public Consultas(T o, Persistencia p) {
		obj = o;
		this.p = p;
		this.item = obj.getClass().getSimpleName();
		if (item.equals("Archivo"))
			dao = new ArchivoDao(p.em);

	}

	public List<Object> consultas() {
		List<Object> resultList = null;
		System.out.println("Consultando...");
		if (item.equals("Archivo")) {
			resultList = (List<Object>) ((ArchivoDao) dao).queryAll(obj
					.getClass());
		}
		return resultList;
	}

	public List<Object> consultasporid(String id) {
		System.out.println("Consultando...");
		List<Object> resultList = null;
		System.out.println("Consultando...");
		if (item.equals("Archivo")) {
			Archivo tmpArc = (Archivo) ((ArchivoDao) dao).queryById(id);
			resultList = (List<Object>) ((ArchivoDao) dao).queryAll(obj
					.getClass());
		}
		return resultList;
	}

	public void ingresos(String datos[]) {/*
										 * revisar id int next = 0; if
										 * (resultList != null &&
										 * resultList.size() > 0){ next =
										 * Integer
										 * .parseInt(resultList.get(resultList
										 * .size()-1).getArcPath()) + 1; }
										 */

		Object newEntity = null;
		newEntity = obj;
		// newEntity.setArcPath("" + next++);
		try {
			try {
				System.out.println("ESTOOOOO"
						+ obj.getClass().getMethods()[0].invoke(""));
				this.obj.getClass().getMethods()[1].invoke("prueba");
				this.obj.getClass().getMethods()[3].invoke(123);
				System.err.println(obj.getClass().getDeclaredFields()[1]);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// obj.getClass().getDeclaredFields()[1].set(obj, "test");
			// obj.getClass().getDeclaredFields()[2].set(obj, Long.MIN_VALUE);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// .setArcNbytes(Long.MIN_VALUE);
			// newEntitiesList.add(newEntity);
		// Iniciar transaccion
		p.em.getTransaction().begin();
		p.em.persist(newEntity);
		p.em.getTransaction().commit();
	}
	/*
	 * 
	 * 
	 * // Consultar
	 * 
	 * 
	 * TypedQuery<Archivo> query = em.createQuery("Select a from Archivo a",
	 * Archivo.class); List<Archivo> resultList = query.getResultList();
	 * 
	 * if (resultList != null){ System.out.println("Resultado: "); for (Archivo
	 * archivo : resultList) { System.out.println(archivo); } }
	 * 
	 * // Insertar System.out.println("Insertando...");
	 * 
	 * int next = 0; if (resultList != null && resultList.size() > 0){ next =
	 * Integer.parseInt(resultList.get(resultList.size()-1).getArcPath()) + 1; }
	 * 
	 * List<Archivo> newEntitiesList = new ArrayList<Archivo>(); Archivo
	 * newEntity = null; for (int i = 0; i < 10; i++) { newEntity = new
	 * Archivo(); newEntity.setArcPath("" + next++);
	 * newEntity.setArcNbytes(Long.MIN_VALUE); newEntitiesList.add(newEntity); }
	 * 
	 * // Iniciar transaccion em.getTransaction().begin();
	 * 
	 * for (Archivo archivo : newEntitiesList) { em.persist(archivo); }
	 * 
	 * em.getTransaction().commit();
	 */
}
