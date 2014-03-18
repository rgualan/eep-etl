package com.etapa.etl.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.Fenomeno;

public class FenomenoDao extends GeneralDao {

	public FenomenoDao(EntityManager em) {
		super(em);
	}

	public Fenomeno queryById(String fenId) {
		TypedQuery<Fenomeno> query = em.createQuery("Select f from Fenomeno f where f.fenId = :fenId",
				Fenomeno.class);
		query.setParameter("fenId", fenId);
		Fenomeno Fenomeno = null;
		try{
		 Fenomeno = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return Fenomeno;
	}
}
