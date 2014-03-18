package com.etapa.etl.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.FenomenoUnidade;
import com.etapa.etl.persistence.entity.FenomenoUnidadePK;

public class FenomenoUnidadeDao extends GeneralDao {

	public FenomenoUnidadeDao(EntityManager em) {
		super(em);
	}

	public FenomenoUnidade queryById(String unidadeId, String fenomenoId) {
		TypedQuery<FenomenoUnidade> query = em.createQuery("Select f from FenomenoUnidade f where f.id = :id",
				FenomenoUnidade.class);
		FenomenoUnidadePK id= new FenomenoUnidadePK(); 
		id.setUniId(unidadeId);
		id.setFenId(fenomenoId);
		query.setParameter("id", id);
		FenomenoUnidade FenomenoUnidade = null;
		try{
		 FenomenoUnidade = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return FenomenoUnidade;
	}
}
