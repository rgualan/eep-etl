package com.etapa.etl.persistence.dao;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.Estacion;
import com.etapa.etl.persistence.entity.EstacionPK;
public class EstacionDao extends GeneralDao {

	public EstacionDao(EntityManager em) {
		super(em);
	}
	/*
	 * 
	 *  String s = "SELECT o FROM orden o"
                    + " JOIN o.Historia h "
                    + " JOIN o.organizacion org "
                    + " WHERE h.id  = :idHistoria ";
	 */
	public Estacion queryById(String estacionId, int tipoestacionId) {
		EstacionPK id = new EstacionPK();
		id.setEstId(estacionId);
		id.setTipId(tipoestacionId);
		TypedQuery<Estacion> query = em.createQuery("Select e from Estacion e where e.id = :id",
				Estacion.class);
		query.setParameter("id", id);
		Estacion Estacion = null;
		try{
		 Estacion = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return Estacion;
	}
	
	public Estacion queryjoinById(String estacionId, int tipoestacionId) {
		EstacionPK id = new EstacionPK();
		id.setEstId(estacionId);
		id.setTipId(tipoestacionId);
		TypedQuery<Estacion> query = em.createQuery("Select e from Estacion e JOIN TipoEstacion t where e.id = :id",
				Estacion.class);
		query.setParameter("id", id);
		Estacion Estacion = null;
		try{
		 Estacion = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return Estacion;
	}
	
	public Estacion queryBytipoestacion(int tipoestacionId) {
		TypedQuery<Estacion> query = em.createQuery("Select e from Estacion e JOIN TipoEstacion t where t.tipId = :tipoestacionId",
				Estacion.class);
		query.setParameter("tipoestacionId", tipoestacionId);
		Estacion Estacion = null;
		try{
		 Estacion = query.getSingleResult();
		}
		catch(Exception e)
		{}
		return Estacion;
	}
}
