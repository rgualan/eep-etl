package com.etapa.etl.persistence.dao;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.TipoEstacion;

public class TipoEstacionDao extends GeneralDao {

	public TipoEstacionDao(EntityManager em) {
		super(em);
	}
	
	public TipoEstacion queryById(String tipoestacionId) {
		TypedQuery<TipoEstacion> query = em.createQuery("Select t from TipoEstacion t where t.tipId = :tipId",
				TipoEstacion.class);
		query.setParameter("tipId", tipoestacionId);
		TipoEstacion TipoEstacion = null;
		try{
		 TipoEstacion = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return TipoEstacion;
	}
	
	
	
	public TipoEstacion queryByNombre(String tipNombre) {
		TypedQuery<TipoEstacion> query = em.createQuery("Select t from TipoEstacion t where t.tipNombre = :tipNombre",
				TipoEstacion.class);
		query.setParameter("tipNombre", tipNombre);
		TipoEstacion TipoEstacion = null;
		try{
		 TipoEstacion = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return TipoEstacion;
	}
}
