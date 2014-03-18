package com.etapa.etl.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.Unidade;

public class UnidadeDao extends GeneralDao {

	public UnidadeDao(EntityManager em) {
		super(em);
	}

	public Unidade queryById(String uniId) {
		TypedQuery<Unidade> query = em.createQuery("Select u from Unidade u where u.uniId = :uniId",
				Unidade.class);
		query.setParameter("uniId", uniId);
		Unidade Unidade = null;
		try{
		 Unidade = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return Unidade;
	}
}
