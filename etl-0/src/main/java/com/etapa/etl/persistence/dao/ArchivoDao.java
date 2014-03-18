package com.etapa.etl.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.Archivo;

public class ArchivoDao extends GeneralDao {

	public ArchivoDao(EntityManager em) {
		super(em);
	}

	public Archivo queryById(String archivoId) {
		TypedQuery<Archivo> query = em.createQuery("Select a from Archivo a where a.arcPath = :arcPath",
				Archivo.class);
		query.setParameter("arcPath", archivoId);
		Archivo archivo = null;
		try{
		 archivo = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return archivo;
	}
}
