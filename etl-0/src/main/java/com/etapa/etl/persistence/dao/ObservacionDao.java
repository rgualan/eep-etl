package com.etapa.etl.persistence.dao;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.Observacion;
import com.etapa.etl.persistence.entity.ObservacionPK;

public class ObservacionDao extends GeneralDao {

	public ObservacionDao(EntityManager em) {
		super(em);
	}

	public Observacion queryById(String obsFecha, String estId, String tipId, String uniId, String fenId) {
		ObservacionPK id = new ObservacionPK();	
		id.setObsFecha(Date.valueOf(obsFecha));
		id.setEstId(estId);
	id.setTipId(Integer.valueOf(tipId));
	id.setUniId(uniId);
	id.setFenId(fenId);
		TypedQuery<Observacion> query = em.createQuery("Select o from Observacion o where o.id = :id",
				Observacion.class);
		query.setParameter("id", id);
		Observacion Observacion = null;
		try{
		 Observacion = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return Observacion;
	}
}


